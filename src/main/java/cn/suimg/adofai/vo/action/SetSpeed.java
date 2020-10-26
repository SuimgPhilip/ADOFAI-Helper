package cn.suimg.adofai.vo.action;

import cn.suimg.adofai.vo.Action;
import lombok.Data;

/**
 * 重新设置BPM特效
 */
@Data
public class SetSpeed extends Action {

    /**
     * 设置速度类型[Bpm/Multiplier]
     */
    private String speedType;

    /**
     * 设置新的BPM
     */
    private Double beatsPerMinute;

    /**
     * 设置倍速
     */
    private Double bpmMultiplier;

    public SetSpeed() {
    }

    public SetSpeed(Integer floor,String speedType, Double beatsPerMinute, Double bpmMultiplier) {
        super.setFloor(floor);
        super.setEventType("SetSpeed");
        this.speedType = speedType;
        this.beatsPerMinute = beatsPerMinute;
        this.bpmMultiplier = bpmMultiplier;
    }
}
