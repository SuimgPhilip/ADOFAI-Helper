package cn.suimg.adofai;

import cn.suimg.adofai.config.AngleConfig;
import cn.suimg.adofai.enmus.Angle;
import cn.suimg.adofai.util.ParseDataUtil;
import cn.suimg.adofai.util.ReBuildUtil;
import cn.suimg.adofai.util.StatisticsUtil;
import cn.suimg.adofai.vo.Track;
import com.melloware.jintellitype.JIntellitype;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainTest {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {


        System.out.println("Welcome use ADOFAI(A Dance Of Fire And Ice) Helper");
        System.out.println("this tool is open-source and free.");
        System.out.println("web-site address https://www.adofai.cn");
        System.out.println("git address https://gitee.com/Suimg/adofai-helper (forChina)");
        System.out.println("git address https://github.com/Suimgcn/adofai-helper (forGlobal)");
        System.out.println("The first step is to open the level file");
        System.out.println("Please drop the level file to this window or press enter to select manually");
        System.out.print(":");

        File levelFile;
        try {
            levelFile = new File(args[0]);
            if(!levelFile.exists())
                throw new FileNotFoundException();
        }catch (ArrayIndexOutOfBoundsException|FileNotFoundException ignored){
            Scanner scanner = new Scanner(System.in);
            String fileAddress = scanner.nextLine();
            levelFile = new File(fileAddress);
        }

        if (!levelFile.isFile() || !levelFile.exists())
            levelFile = openFile();
        ParseDataUtil instance = ParseDataUtil.getInstance(levelFile);
        printBasicInfo(instance);
        printStatisticsInfo(instance);



        //把谱子转换为一条直线
//        File directory = new File("D:/parse/");
//        for (String fileName : directory.list()) {
//            ParseDataUtil parseDataUtil = ParseDataUtil.getInstance(new File(directory,fileName));
//            Map<String, Object> settings = parseDataUtil.getSettings();
//            List<Track> trackList = parseDataUtil.getTrackList();
//            ReBuildUtil reBuildUtil = ReBuildUtil.getInstance(trackList, settings);
//            String json = reBuildUtil.buildWithALine();
//            OutputStream outputStream = new FileOutputStream(new File(directory,"[rebuild]" + fileName));
//            FileCopyUtils.copy(json.getBytes("UTF-8"),outputStream);
//        }

        //把谱子转换为中旋
//        File directory = new File("D:/parse/");
//        for (String fileName : directory.list()) {
//            ParseDataUtil parseDataUtil = ParseDataUtil.getInstance(new File(directory,fileName));
//            Map<String, Object> settings = parseDataUtil.getSettings();
//            List<Track> trackList = parseDataUtil.getTrackList();
//            ReBuildUtil reBuildUtil = ReBuildUtil.getInstance(trackList, settings);
//            String json = reBuildUtil.buildWithMidSplit();
//            OutputStream outputStream = new FileOutputStream(new File(directory,"[rebuild]" + fileName));
//            FileCopyUtils.copy(json.getBytes("UTF-8"),outputStream);
//        }
//



//        Map<Integer, List<Angle[]>> angleMapping = AngleConfig.angleMapping;
//        System.out.println(String.format("游戏中一共包含了%d中角度(大于180度的直接用360取反)\n角度分布列表如下",angleMapping.size()));
//        Set<Map.Entry<Integer, List<Angle[]>>> entries = angleMapping.entrySet();
//        for (Map.Entry<Integer, List<Angle[]>> entry : entries) {
//            System.out.println(String.format("%d°:%d",entry.getKey(),entry.getValue().size()));
//        }


//        System.out.println(Locale.getDefault().getLanguage());
//        System.out.println(Locale.getDefault().getDisplayLanguage());
//        System.out.println(Angle.U.toString());
    }


    public static File openFile(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFileFilter(new FileFilter(){
            @Override
            public boolean accept(File f) {
                if (f.isDirectory())
                    return true;
                String name = f.getName();
                try {
                    return name.substring(name.lastIndexOf(".")).equals(".adofai");
                }catch (StringIndexOutOfBoundsException ignored){
                    return true;
                }
            }

            @Override
            public String getDescription() {
                return "ADOFAI level File";
            }
        });
        chooser.showDialog(new JLabel(), "选择");

        return chooser.getSelectedFile();

    }

    private static void printBasicInfo(ParseDataUtil instance){
        Map<String, Object> settings = instance.getSettings();
        System.out.println("level info:");
        System.out.println("song name: " + settings.get("song"));
        System.out.println("song artist: " + settings.get("artist"));
        System.out.println("level author: " + settings.get("author"));
        System.out.println("level desc: " + settings.get("levelDesc"));
        System.out.println("basic bpm: "+ settings.get("bpm"));
    }

    private static void printStatisticsInfo(ParseDataUtil instance){
        StatisticsUtil statisticsUtil = StatisticsUtil.getInstance(instance.getTrackList());
        System.out.println("max bpm: "+statisticsUtil.maxBpm());
        System.out.println("min bpm: "+statisticsUtil.minBpm());
        System.out.println("max angle: "+statisticsUtil.maxAngle());
        System.out.println("min angle: "+statisticsUtil.minAngle());
        System.out.println("max delay: "+statisticsUtil.maxDelay());
        System.out.println("min delay: "+statisticsUtil.minDelay());
        System.out.println("max delay map to bpm: "+statisticsUtil.maxDelay2Bpm());
        System.out.println("min delay map to bpm: "+statisticsUtil.minDelay2Bpm());
    }


    private static void autoPlay(ParseDataUtil instance){
        instance.parseSetSpeedAction();
        try{
            Robot robot = new Robot();
            AtomicBoolean change = new AtomicBoolean(false);
            AtomicBoolean stop = new AtomicBoolean(false);
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
            robot.delay(instance.getReadyDelay());
            long startTime;
            for(Integer ping : instance.getDelayList()){
                startTime = System.currentTimeMillis();
                if(stop.get())
                    break;
                change.set(!change.get());
                int key = change.get() ? KeyEvent.VK_F : KeyEvent.VK_J;
                robot.keyPress(key);
                robot.keyRelease(key);
                System.out.println(System.currentTimeMillis() - startTime);
                robot.delay(ping);
            }
            while (true){
                Thread.sleep(1000);
                if(stop.get())
                    break;
            }
        } catch (AWTException|InterruptedException e) {
            e.printStackTrace();
        }
    }
}
