package cn.suimg.adofai.util;

import cn.suimg.adofai.config.AngleConfig;
import cn.suimg.adofai.enmus.Angle;
import cn.suimg.adofai.exception.ParseException;
import cn.suimg.adofai.vo.Action;
import cn.suimg.adofai.vo.action.SetSpeed;
import cn.suimg.adofai.vo.action.Twirl;
import org.springframework.util.FileCopyUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static cn.suimg.adofai.enmus.Angle.*;
import static cn.suimg.adofai.enmus.Angle.parseAngle;

/**
 * 随机轨道生成器
 * @author Suimg
 */
public class TrackGeneratorUtil {




    private final List<Angle> angleList = getAllAngles();


    private Map<String,Object> settings = new HashMap<String, Object>(){{
        put("version", 3);
        put("artist", "音乐作者");
        put("specialArtistType", "None");
        put("artistPermission", "");
        put("song", "歌曲");
        put("author", "关卡作者");
        put("separateCountdownTime", "Enabled");
        put("previewImage", "");
        put("previewIcon", "");
        put("previewIconColor", "003f52");
        put("previewSongStart", 0);
        put("previewSongDuration", 10);
        put("seizureWarning", "Disabled");
        put("levelDesc", "说说看你的关卡是什么样的吧！");
        put("levelTags", "");
        put("artistLinks", "");
        put("difficulty", 1);
        put("songFilename", "");
        put("bpm", 200);
        put("volume", 100);
        put("offset", 0);
        put("pitch", 100);
        put("hitsound", "Kick");
        put("hitsoundVolume", 100);
        put("countdownTicks", 4);
        put("trackColorType", "Single");
        put("trackColor", "debb7b");
        put("secondaryTrackColor", "ffffff");
        put("trackColorAnimDuration", 2);
        put("trackColorPulse", "None");
        put("trackPulseLength", 10);
        put("trackStyle", "Standard");
        put("trackAnimation", "Grow");
        put("beatsAhead", 4);
        put("trackDisappearAnimation", "Retract");
        put("beatsBehind", 0);
        put("backgroundColor", "000000");
        put("showDefaultBGIfNoImage", "Enabled");
        put("bgImage", "");
        put("bgImageColor", "ffffff");
        put("parallax", new int[]{100, 100});
        put("bgDisplayMode", "FitToScreen");
        put("lockRot", "Disabled");
        put("loopBG", "Disabled");
        put("unscaledSize", 100);
        put("relativeTo", "Player");
        put("position", new int[]{0, 0});
        put("rotation", 0);
        put("zoom", 100);
        put("bgVideo", "");
        put("loopVideo", "Disabled");
        put("vidOffset", 0);
        put("floorIconOutlines", "Disabled");
        put("stickToFloors", "Disabled");
        put("planetEase", "Linear");
        put("planetEaseParts", 1);
        put("legacyFlash", false);
    }};



    private  boolean inverse = false;


    private Double lastBpm = 0D;



    private List<Angle> pathBuilder = new ArrayList<>();


    private List<Action> actionList = new ArrayList<>();

    public enum Rule {

        /**
         * 随机生成
         */
        RANDOM,

        /**
         * 对称生成
         */
        SYMMETRY;
    }


    public static TrackGeneratorUtil getInstance(Double bpm,Integer length) throws IOException {
        return new TrackGeneratorUtil(bpm,length);
    }

    private TrackGeneratorUtil(Double bpm,Integer length) throws IOException {




        settings.put("bpm",bpm);
        pathBuilder.add(R);
        Angle lastAngle = R;
        for (int i = 0; i < length; i++) {

            Angle tmpAngle;
            int angle;
            do{
                tmpAngle = angleList.get(randomRange(0,angleList.size()));
                angle = parseAngle(lastAngle,tmpAngle);
            }while (AngleConfig.mirrorMapping.get(lastAngle) == tmpAngle || (angle >= 45 && angle <= 165));
            lastAngle = tmpAngle;
            pathBuilder.add(tmpAngle);
        }

        for (int i = 0; i < pathBuilder.size(); i++) {
            try {
                Angle sourceAngle = pathBuilder.get(i);
                Angle targetAngle = pathBuilder.get(i + 1);
                Integer angle = parseAngle(sourceAngle,targetAngle);


                //如果角度大于180 旋转得到一个较小的角度。
                int tmpAngel = inverse ? 360 - angle : angle;
                if(tmpAngel > 180 && tmpAngel != 360){
                    actionList.add(new Twirl(i + 1));
                    inverse = !inverse;
                }
                if(angle == 0 )
                    angle = 360;
                angle = Math.abs(inverse ? 360 - angle : angle);
                double newBpm = 60 / bpm / 180 * angle * 1000;
                if(newBpm != 0 && lastBpm != newBpm)
                    actionList.add(new SetSpeed(i + 1,"Bpm", newBpm,1D));
                lastBpm = newBpm;
            }catch (IndexOutOfBoundsException ignored){}
        }

    }


    public void saveToLevel(){
        StringBuilder result = new StringBuilder();
        pathBuilder.forEach(result::append);
        byte[] bytes = JSONUtil.toString(new HashMap<String, Object>(){{
            put("pathData",result.toString());
            put("settings",settings);
            put("actions",actionList.stream().filter(action -> {
                if (action instanceof SetSpeed){
                    SetSpeed setSpeed = (SetSpeed) action;
                    return setSpeed.getBeatsPerMinute() != 0;
                }
                return true;
            }).collect(Collectors.toList()));
        }}).getBytes(StandardCharsets.UTF_8);
        try{
            FileCopyUtils.copy(bytes,new FileOutputStream("C:\\Users\\Suimg\\Desktop\\buildTest.adofai"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws IOException {
        TrackGeneratorUtil.getInstance(60D,5000).saveToLevel();

    }


    private int randomRange(int min,int max){
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * Parse angle
     * @param sourceAngle
     * @param targetAngle
     * @return
     */
    public Integer parseAngle(Angle sourceAngle,Angle targetAngle){



        Angle mirrorAngle = AngleConfig.mirrorMapping.get(sourceAngle);

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
            throw new ParseException("parse failed : not found angle from [" + mirrorAngle + "] to [" + targetAngle + "]");
        return angle;
    }
}
