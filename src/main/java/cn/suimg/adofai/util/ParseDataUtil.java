package cn.suimg.adofai.util;


import cn.suimg.adofai.config.AngleConfig;
import cn.suimg.adofai.enmus.Angle;
import static cn.suimg.adofai.enmus.Angle.*;
import cn.suimg.adofai.exception.ParseException;
import cn.suimg.adofai.vo.Action;
import cn.suimg.adofai.vo.Track;
import cn.suimg.adofai.vo.action.SetSpeed;
import cn.suimg.adofai.vo.action.Twirl;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 解析工具类
 */
public class ParseDataUtil {

    /**
     * 特效列表
     */
    private final Map<Integer, List<Action>> actionMap = new HashMap<>();

    /**
     * 解析列表
     */
    private final List<Track> trackList = new ArrayList<>();

    /**
     * 延迟列表
     */
    private final List<Integer> delayList = new ArrayList<>();

    /**
     * 轨道列表
     */
    private final List<String> paths;

    /**
     * 当前BPM
     */
    private Double bpm;


    private int readyBeat = 0;

    /**
     * 是否逆向，默认是顺时针。旋转一次取反
     */
    private boolean inverse = false;



    /**
     * 获取当前工具类的实例
     * @param file
     * @return
     */
    public static ParseDataUtil getInstance(File file){
        return new ParseDataUtil(file);
    }

    /**
     * 私有构造方法
     * @param file
     */
    private ParseDataUtil(File file){
        try{
            //读取存档文件
            byte[] bytes = FileCopyUtils.copyToByteArray(file);

            JSONObject jsonObject;
            try{
                jsonObject = JSONObject.parseObject(new String(bytes));
            }catch (JSONException e){
                //有的文件前部分会有三个字节的BOM信息 跳过
                jsonObject = JSONObject.parseObject(new String(bytes,3,bytes.length - 3));
            }

            //读取基础的BPM
            bpm = jsonObject.getJSONObject("settings").getDouble("bpm");
            readyBeat = jsonObject.getJSONObject("settings").getInteger("countdownTicks");

            //解析路径信息
            String pathData = jsonObject.getString("pathData").replaceAll("(.)", "$1\n");
            paths = Arrays.asList(pathData.split("\n"));

            //解析特效
            jsonObject.getJSONArray("actions").forEach(object0 -> {
                JSONObject object = (JSONObject) object0;
                int floor = object.getInteger("floor");
                List<Action> actions = actionMap.get(floor) != null ? actionMap.get(floor) : new ArrayList<>();
                Action action = null;
                //影响到延迟的特效只有两个，旋转和重新设置速度
                switch (object.getString("eventType")){
                    case "Twirl":
                        action = new Twirl(floor);
                        break;
                    case "SetSpeed":
                        //这种解析方法适用于最新版本。旧版本的存档需要在新版本的游戏中打开再保存 自动转换版本
                        action = new SetSpeed(floor,object.getString("speedType"),object.getDouble("beatsPerMinute"),object.getDouble("bpmMultiplier"));
                        break;
                    default:
                        break;
                }
                if(action != null){
                    actions.add(action);
                    actionMap.put(floor,actions);
                }
            });

            //第一遍循环：把轨道和特效整合为一个新的对象
            for(int i = 0;i < paths.size();i++){
                int floor = i + 1;
                Track track = new Track();
                List<Action> action = actionMap.get(floor) != null ? actionMap.get(floor) : new ArrayList<>();
                action.forEach(item -> item.setFloor(floor));
                track.setFloor(floor);
                track.setBpm(bpm);
                track.setPath(paths.get(i));
                track.setActions(action);
                trackList.add(track);
            }
            for(int i = 0; i < trackList.size() - 1; i++){
                Track sourceAction = trackList.get(i);
                Track targetAction = trackList.get(i + 1);
                parseDelay(sourceAction,targetAction);
            }
        }catch (IOException e){
            throw new ParseException("parse failed",e);
        }

    }

    public void parseDelay(Track source, Track target){
        bpm = getBPM(source);
        setSpeed(source.getFloor());
        isTwirl(source);
        int angle = getAngle(source, target);
        int delay =  (int) (60 / bpm / 180 * angle * 1000);
        delayList.add(delay);
        source.setDelay(delay);
        System.out.println(String.format("轨道ID:%d,从:%s,到:%s,旋转:%b,角度:%d,BPM:%f,延迟:%dms",source.getFloor(),source.getPath(),target.getPath(),inverse,angle,bpm,delay));
    }

    /**
     * 计算出当前BPM
     * @param source
     * @return
     */
    public double getBPM(Track source){
        for(Action action : source.getActions()){
            if(action instanceof SetSpeed){
                SetSpeed setSpeed = (SetSpeed) action;
                Double newBPM = 0D;
                switch (setSpeed.getSpeedType()){
                    case "Bpm":
                        newBPM = setSpeed.getBeatsPerMinute();
                        source.setBpm(newBPM);
                        break;
                    case "Multiplier":
                        Double basedBPM = source.getBpm();
                        newBPM = basedBPM * setSpeed.getBpmMultiplier();
                        break;
                }
                return newBPM;
            }
        }
        return source.getBpm();
    }

    /**
     * 判断是否旋转
     * @param sourceItem
     * @return
     */
    public void isTwirl(Track sourceItem){
        for(Action action : sourceItem.getActions()) {
            if (action instanceof Twirl) {
                inverse = !inverse;
            }
        }
    }

    /**
     * 获取角度
     * @param sourceItem
     * @param targetItem
     * @return
     */
    public int getAngle(Track sourceItem, Track targetItem){
        Angle currentAngle = parseAngle(sourceItem.getPath());
        Angle targetAngle = parseAngle(targetItem.getPath());
        Angle mirrorAngle = AngleConfig.mirrorMapping.get(currentAngle);
        Integer angle = null;
        Set<Map.Entry<Integer, List<Angle[]>>> entries = AngleConfig.angleMapping.entrySet();
        for (Map.Entry<Integer, List<Angle[]>> entry : entries) {
            List<Angle[]> value = entry.getValue();
            for (Angle[] angles : value) {
                Angle angle1 = angles[0];
                Angle angle2 = angles[1];

                //正向查询
                if(angle1 == mirrorAngle && angle2 == targetAngle){
                    angle = entry.getKey();
                    break;
                }

                //反向查询
                if(angle1 == targetAngle && angle2 == mirrorAngle){
                    angle = 360 - entry.getKey();
                    break;
                }
            }
        }
        if(angle == null)
            throw new ParseException("parse failed:not found angle from [" + mirrorAngle + "] to [" + targetAngle + "]");

        return Math.abs(inverse ? 360 - angle : angle);
    }

    /**
     * 设置后面的BPM
     * @param skip
     */
    private void setSpeed(int skip){
        for(int i = 0; i < trackList.size(); i++){
            if(i >= skip)
                trackList.get(i).setBpm(bpm);
        }
    }

    /**
     * 返回延迟列表
     * @return
     */
    public List<Integer> getDelayList() {
        return delayList;
    }

    /**
     * 获取准备节拍延迟
     * @return
     */
    public int getReadyDelay(){
        return (int) (60 / trackList.get(0).getBpm() * readyBeat * 2 * 1000);
    }

    /**
     * 返回解析列表
     * @return
     */
    public String getDumpJSON(){
        return JSONUtil.toString(trackList);
    }

    /**
     * 返回延迟列表
     * @return
     */
    public String getDelayJSON(){
        return JSONUtil.toString(delayList);
    }
}
