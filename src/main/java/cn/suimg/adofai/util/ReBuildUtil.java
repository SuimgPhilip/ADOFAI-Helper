package cn.suimg.adofai.util;

import cn.suimg.adofai.vo.Action;
import cn.suimg.adofai.vo.Track;
import cn.suimg.adofai.vo.action.SetSpeed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        this.trackList = trackList;
        this.settings = settings;
        //修改作者信息
        this.settings.put("author",this.settings.get("author") + " (ADOFAI-Helper Rebuild)");
    }


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
