package cn.suimg.adofai;

import cn.suimg.adofai.util.ParseDataUtil;
import cn.suimg.adofai.util.ReBuildUtil;
import cn.suimg.adofai.vo.Track;
import com.melloware.jintellitype.JIntellitype;
import org.springframework.util.FileCopyUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainTest {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {

        //把谱子转换为一条直线
        File directory = new File("D:/parse/");
        for (String fileName : directory.list()) {
            ParseDataUtil parseDataUtil = ParseDataUtil.getInstance(new File(directory,fileName));
            Map<String, Object> settings = parseDataUtil.getSettings();
            List<Track> trackList = parseDataUtil.getTrackList();
            ReBuildUtil reBuildUtil = ReBuildUtil.getInstance(trackList, settings);
            String json = reBuildUtil.buildWithALine();
            OutputStream outputStream = new FileOutputStream(new File(directory,"[rebuild]" + fileName));
            FileCopyUtils.copy(json.getBytes("UTF-8"),outputStream);
        }



//        Robot robot = new Robot();
//        AtomicBoolean change = new AtomicBoolean(false);
//        AtomicBoolean stop = new AtomicBoolean(false);
//        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, 'P');
//        JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT, 'O');
//        JIntellitype.getInstance().addHotKeyListener(keycode -> {
//            if(keycode == 1){
//                robot.keyPress(KeyEvent.VK_SPACE);
//                robot.keyRelease(KeyEvent.VK_SPACE);
//                robot.delay(parseDataUtil.getReadyDelay());
//                for(Integer ping : parseDataUtil.getDelayList()){
//                    if(stop.get())
//                        break;
//                    change.set(!change.get());
//                    int key = change.get() ? KeyEvent.VK_F : KeyEvent.VK_J;
//                    robot.keyPress(key);
//                    robot.keyRelease(key);
//                    robot.delay(ping);
//                }
//            }else if (keycode == 2){
//                stop.set(true);
//            }fjfjfjf
//        });
//        robot.delay(5000);
//        robot.keyPress(KeyEvent.VK_SPACE);
//        robot.keyRelease(KeyEvent.VK_SPACE);
//        robot.delay(parseDataUtil.getReadyDelay());
//        long startTime;
//        for(Integer ping : parseDataUtil.getDelayList()){
//            startTime = System.currentTimeMillis();
//            if(stop.get())
//                break;
//            change.set(!change.get());
//            int key = change.get() ? KeyEvent.VK_F : KeyEvent.VK_J;
//            robot.keyPress(key);
//            robot.keyRelease(key);
//            System.out.println(System.currentTimeMillis() - startTime);
//            robot.delay(ping);
//        }
//       while (true){
//           Thread.sleep(1000);
//           if(stop.get())
//               break;
//       }

    }
}
