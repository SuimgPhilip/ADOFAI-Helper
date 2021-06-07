package cn.suimg.adofai.util;

import cn.suimg.adofai.vo.Track;

import java.util.List;

/**
 * 统计工具类
 * @author Suimg
 */
public class StatisticsUtil {

    /**
     * 轨道列表
     */
    private final List<Track> trackList;

    /**
     * 获取实例方法
     * @param trackList
     * @return
     */
    public static StatisticsUtil getInstance(List<Track> trackList){
        return new StatisticsUtil(trackList);
    }

    /**
     * 私有构造方法
     * @param trackList
     */
    private StatisticsUtil(List<Track> trackList){
        this.trackList = trackList;
    }

    /**
     * 获取最大BPM
     * @return
     */
    public double maxBpm(){
        return trackList.stream().mapToDouble(Track::getBpm).max().getAsDouble();
    }

    /**
     * 获取最小BPM
     * @return
     */
    public double minBpm(){
        return trackList.stream().mapToDouble(Track::getBpm).min().getAsDouble();
    }

    /**
     * 获取最大角度
     * @return
     */
    public int maxAngle(){
        return trackList.stream().mapToInt(Track::getAngle).max().getAsInt();
    }

    /**
     * 获取最小角度
     * @return
     */
    public int minAngle(){
        return trackList.stream().mapToInt(Track::getAngle).min().getAsInt();
    }

    /**
     * 最大延迟
     * @return
     */
    public int maxDelay(){
        return trackList.stream().mapToInt(Track::getDelay).max().getAsInt();
    }

    /**
     * 最小延迟
     * @return
     */
    public int minDelay(){
        return trackList.stream().mapToInt(Track::getDelay).min().getAsInt();
    }

    /**
     * 最大延迟对应BPM
     * @return
     */
    public double maxDelay2Bpm(){
        return 60D / maxDelay();
    }

    /**
     * 最小延迟
     * @return
     */
    public double minDelay2Bpm(){
        return 60D / minDelay();
    }


}
