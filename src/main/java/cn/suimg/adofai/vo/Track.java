package cn.suimg.adofai.vo;

import lombok.Data;

import java.util.List;

/**
 * 解析之后的对象
 */
@Data
public class Track {

    /**
     * 轨道ID[从1开始]
     */
    private Integer floor;

    /**
     * 当前轨道的BPM
     */
    private Double bpm;

    /**
     * 轨道代码
     */
    private String path;

    /**
     * 延迟
     */
    private Integer delay;

    /**
     * 特效列表
     */
    private List<Action> actions;
}
