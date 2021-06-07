package cn.suimg.adofai.vo.action;

import lombok.Data;

/**
 * 设置轨道颜色
 */
@Data
public class ColorTrack {

    /**
     * 轨道颜色类别
     */
    private String trackColorType;

    /**
     * 轨道颜色
     */
    private String trackColor;

    private String secondaryTrackColor;
    private String trackColorAnimDuration;
    private String trackColorPulse;
    private String trackPulseLength;
    private String trackStyle;




}
