package com.example;

import com.tinify.Source;
import com.tinify.Tinify;

import java.io.File;
import java.io.IOException;

/**
 * 利用熊猫压缩批量压缩图片
 */

public class Compress {
    public static String apiKey1 = "7qq8sA6WBdp6VCiT3_FZ9YlqcvmAoeL7";
    public static String apiKey2 = "RlBBezjkDfJDiPOWN7u247YvBFHUYT0D";
    public static String apiKey3 = "opatDW9tpoZN5J030v_pfYYWQcpYTvgw";

    static int begin = 0;
    public static void main(String[] args) {
        Tinify.setKey(apiKey3);
        File[] files = null;
//            Tinify.setProxy("http://user:pass@192.168.0.1:8080");
        final int dur = 20;
        String path = Compress.class.getResource("").toString();
        path = "/" + path.substring(path.indexOf("/") + 1);
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        path = path.substring(0, path.lastIndexOf("/"));
        String old_path = path + "/old_image";
        final String new_path = path + "/new_image/";
        System.out.println("old_path:" + old_path);
        System.out.println("new_path:" + new_path);
        try {
            File file = new File(old_path);
            files = file.listFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int length = files.length;
        while (begin < length) {
            final int finalBegin = begin;
            final File[] finalFiles = files;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i = finalBegin; i < finalBegin + dur && i < finalFiles.length; i++) {
                            Source source = Tinify.fromFile(finalFiles[i].getPath());
                            source.toFile(new_path + finalFiles[i].getName());
                            System.out.println(i);
                        }
//                        System.out.println("老文件" + file.length());
//                        System.out.println("新文件" + new File(new_path).length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            begin += dur;
        }
    }
}
