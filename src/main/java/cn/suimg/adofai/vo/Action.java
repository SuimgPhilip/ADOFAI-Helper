package cn.suimg.adofai.vo;

import lombok.Data;

/**
 * 基本属性类
 */
@Data
public abstract class Action {

    /**
     * 动作ID(轨道ID)
     */
    protected int floor;

    /**
     * 事件类型(特效类型)
     */
    protected String eventType;
}
