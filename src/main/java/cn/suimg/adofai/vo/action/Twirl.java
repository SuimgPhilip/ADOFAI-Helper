package cn.suimg.adofai.vo.action;

import cn.suimg.adofai.vo.Action;
import lombok.Data;

/**
 * 旋转特效
 */
@Data
public class Twirl extends Action {

    public Twirl(Integer floor) {
        super.setFloor(floor);
        super.eventType = "Twirl";
    }

}
