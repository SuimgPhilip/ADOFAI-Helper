package cn.suimg.adofai;

import cn.suimg.adofai.util.ParseDataUtil;
import com.melloware.jintellitype.JIntellitype;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainTest {
    public static void main(String[] args) throws AWTException, InterruptedException {
        ParseDataUtil parseDataUtil = ParseDataUtil.getInstance(new File("C:\\Users\\Suimg\\AppData\\LocalLow\\7th Beat Games\\A Dance of Fire and Ice\\【极难】Adofai Custom Level - 佐藤直之,L.E.D. - Chrono Diver -PENDULUMs- by 阿峦\\Chrono Diver -PENDULUMs-.adofai"));
        System.out.println(parseDataUtil.getDumpJSON());
        System.out.println(parseDataUtil.getDelayJSON());
        System.out.println(parseDataUtil.getReadyDelay());
        Robot robot = new Robot();
        AtomicBoolean change = new AtomicBoolean(false);
        AtomicBoolean stop = new AtomicBoolean(false);
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
        robot.delay(5000);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.keyRelease(KeyEvent.VK_SPACE);
        robot.delay(parseDataUtil.getReadyDelay());
        long startTime;
        for(Integer ping : parseDataUtil.getDelayList()){
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
//       while (true){
//           Thread.sleep(1000);
//           if(stop.get())
//               break;
//       }

    }
}
