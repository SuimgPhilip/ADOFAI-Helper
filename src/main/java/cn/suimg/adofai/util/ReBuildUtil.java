package cn.suimg.adofai.util;

import cn.suimg.adofai.config.AngleConfig;
import cn.suimg.adofai.enmus.Angle;
import cn.suimg.adofai.exception.ParseException;
import cn.suimg.adofai.vo.Action;
import cn.suimg.adofai.vo.Track;
import cn.suimg.adofai.vo.action.SetSpeed;
import cn.suimg.adofai.vo.action.Twirl;

import java.util.*;
import java.util.stream.Collectors;

import static cn.suimg.adofai.enmus.Angle.parseAngle;

/**
 * 重新构建工具类
 */
public class ReBuildUtil {

    /**
     * 解析列表
     */
    private final List<Track> trackList;

    /**
     * 读取原来的配置
     */
    private final Map<String,Object> settings;

    /**
     * 获取实例的方法
     * @param trackList
     * @return
     */
    public static ReBuildUtil getInstance(List<Track> trackList,Map<String,Object> settings){
        return new ReBuildUtil(trackList,settings);
    }

    /**
     * 私有构造方法
     * @param trackList
     */
    private ReBuildUtil(List<Track> trackList,Map<String,Object> settings){
        this.trackList = trackList
                .stream()
                .filter(track -> ! "!".equals(track.getPath()))
                .collect(Collectors.toList());
        this.settings = settings;
        //修改作者信息
        this.settings.put("author",this.settings.get("author") + " (ADOFAI-Helper Rebuild)");
    }

    /**
     * 构造为一条直线
     * @return
     */
    public String buildWithALine(){
        int currentDelay = 0;
        StringBuilder sb = new StringBuilder();
        List<Action> actionList = new ArrayList<>();
        for (int i = 0; i < trackList.size(); i++) {
            sb.append("R");
            Track track = trackList.get(i);
            //个别情况不会计算延迟
            if(null == track.getDelay())
                continue;
            //只有延迟不相等才回重新设置BPM
            if(currentDelay != track.getDelay()){
                currentDelay = track.getDelay();
                actionList.add(new SetSpeed(i + 1,"Bpm",(double) 60 / currentDelay * 1000,1D));
            }
        }
        return builder(sb.toString(),actionList);
    }

    /**
     * 构造为中旋
     * @return
     */
    public String buildWithMidSplit(){
        StringBuilder sb = new StringBuilder();
        List<Action> actionList = new ArrayList<>();
        Angle currentAngle = Angle.L;
        for (int i = 0; i < trackList.size(); i++) {
            Track currentTrack = trackList.get(i);
            Angle targetAngle = null;
            int angle;

            try{
                angle = currentTrack.getAngle();
            }catch (NullPointerException ignored){
                continue;
            }

            boolean needTwirl = false;

            //大于180度的角在配置类中重复了,直接拿360取反 加旋转
//            if(currentTrack.getAngle() != 180 && currentTrack.getAngle() != 360 && angle > 180){
//                angle = 360 - angle;
//                needTwirl = true;
//            }

            Set<Map.Entry<Integer, List<Angle[]>>> entries = AngleConfig.angleMapping.entrySet();
            for (Map.Entry<Integer, List<Angle[]>> entry : entries) {
                if(angle == entry.getKey()){
                    List<Angle[]> value = entry.getValue();
                    for (Angle[] angles : value) {
                        if(angles[0] == currentAngle){
                            targetAngle = angles[1];
                            break;
                        }
                    }
                    break;
                }else if(angle == 360 - entry.getKey()){
                    List<Angle[]> value = entry.getValue();
                    for (Angle[] angles : value) {
                        if(angles[1] == currentAngle){
                            targetAngle = angles[0];
                            break;
                        }
                    }
                }
            }
            if(targetAngle == null)
                throw new ParseException("Build map failed,can't find the from [" + currentAngle + "]" + angle + "'s Angle");


            System.out.println("path [" + currentAngle + "] to [" + targetAngle + "] angle is " +angle+"");
            sb.append(targetAngle).append("!");
            currentAngle = targetAngle;
            //添加设置速度
            for (Action action : currentTrack.getActions()) {
                if(action instanceof SetSpeed){
                    SetSpeed setSpeed = (SetSpeed) action;
                    actionList.add(new SetSpeed(i + 1,setSpeed.getSpeedType(),setSpeed.getBeatsPerMinute(),setSpeed.getBpmMultiplier()));
                    break;
                }
            }

            //旋转角度
//            if(needTwirl)
//                actionList.add(new Twirl(i + 1));
        }
        return builder(sb.toString(),actionList);
    }

    /**
     * Builder
     * @param pathData
     * @param actionList
     * @return
     */
    private String builder(String pathData,List<Action> actionList){
        return JSONUtil.toString(new HashMap<String, Object>(){{
            put("pathData",pathData);
            put("settings",settings);
            put("actions",actionList);
        }});
    }

}
