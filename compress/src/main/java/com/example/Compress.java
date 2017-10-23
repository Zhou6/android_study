package com.example;

import com.tinify.Source;
import com.tinify.Tinify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 利用熊猫压缩批量压缩图片
 * 把需要压缩的图片放到old_image文件中
 */

public class Compress {
    //每个key每个月可压缩500张
    private static String[] apiKey = {"7qq8sA6WBdp6VCiT3_FZ9YlqcvmAoeL7", "RlBBezjkDfJDiPOWN7u247YvBFHUYT0D", "opatDW9tpoZN5J030v_pfYYWQcpYTvgw"};
    private static String ModuleName = "compress";
    private static final int dur = 5; //每dur张图片开一个线程
    private static int begin = 0;
    private static int threadNum = 0;
    private static long all_old_size;
    private static long all_new_size;

    public static void main(String[] args) {
        try {
            Tinify.setKey(apiKey[0]);
//            Tinify.validate(); //这句话太浪费时间了！！！
//            int compressionsThisMonth = Tinify.compressionCount();
//            System.out.println("此key本月已压缩:" + compressionsThisMonth);
        } catch (java.lang.Exception e) {
            System.out.println(e);
            return;
        }
        File[] files = null;
//            Tinify.setProxy("http://user:pass@192.168.0.1:8080");
        String path = Compress.class.getResource("").toString();
        path = "/" + path.substring(path.indexOf("/") + 1);
        path = path.substring(0, path.lastIndexOf(ModuleName) + ModuleName.length());
        String old_path = path + "/old_image";
        final String new_path = path + "/new_image/";
        System.out.println("old_path:" + old_path);
        System.out.println("new_path:" + new_path);
        try {
            File file = new File(old_path);
            if (file.mkdir()) {
                file.createNewFile();
            }
            files = file.listFiles();
            File file2 = new File(new_path);
            if (file2.mkdir()) {
                file2.createNewFile();
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        int length = files != null ? files.length : 0;
        System.out.println("共需压缩:" + length + "张图片");
        threadNum = length / dur + (length % dur == 0 ? 0 : 1);
        while (begin < length) {
            final int finalBegin = begin;
            final File[] finalFiles = files;
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = finalBegin; i < finalBegin + dur && i < finalFiles.length; i++) {
                            long oldSize = getSize(finalFiles[i]);
                            all_old_size += oldSize;
                            Source source = Tinify.fromFile(finalFiles[i].getPath());
                            source.toFile(new_path + finalFiles[i].getName());
                            long newSize = getSize(new File(new_path + finalFiles[i].getName()));
                            System.out.println(i + "：" + finalFiles[i].getName() + "     " + oldSize / 1000.0 + "kb ==> " + newSize / 1000.0 + "kb");
                            all_new_size += newSize;
                        }
                        threadNum--;
                        if (threadNum == 0) {
                            System.out.println("压缩完成, 共节省：" + (all_old_size - all_new_size) / 1000.0 + "kb");
                        }
                    } catch (IOException e) {
                        System.out.print(e);
                    }
                }
            });
            thread.start();
            begin += dur;
        }
    }

    private static long getSize(File f) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            return fis.available();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }
}
