package cn.suimg.adofai;

import java.io.File;

public class MainApplication {

    public static void main(String[] args) {
        File file = new File("F:/adofai/levels/东京喰种-unravel(1)/level.adofai");
        String name = file.getName();
        System.out.println(name.substring(name.lastIndexOf(".")).equals(".adofai"));
    }
}
