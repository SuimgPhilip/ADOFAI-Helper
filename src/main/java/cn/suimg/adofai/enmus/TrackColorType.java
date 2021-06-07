package cn.suimg.adofai.enmus;

/**
 * 轨道颜色类别
 * @author Suimg
 */
public enum TrackColorType {

    /**
     * 单独
     */
    Single,

    /**
     * 条纹
     */
    Stripes,

    /**
     * 发光
     */
    Glow,

    /**
     * 闪烁
     */
    Blink,

    /**
     * 切换
     */
    Switch,

    /**
     * 彩虹
     */
    Rainbow,


    ;


    @Override
    public String toString() {
        return this.name();
    }
}
