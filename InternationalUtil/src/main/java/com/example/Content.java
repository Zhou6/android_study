package com.example;

/**
 * @author zqm
 * @since 2017/8/18
 */

public class Content {

    public int x_int, y;
    public String text;
    String[] english = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public Content(String content) {
        int first = content.indexOf("\"");
        String loc = content.substring(0, first);
        String x = loc.substring(0, 1);
        for (int i = 0; i < english.length; i++) {
            if (english[i].equals(x)) {
                x_int = i;
                break;
            }
        }
        try {
            y = Integer.parseInt(loc.substring(1));
        } catch (Exception e) {

        }
        if (content.contains("[[") && content.contains("]]")) {
            String ss = content.substring(content.indexOf("[[") + 2, content.lastIndexOf("]]"));
            if (ss.contains("\"")) {
                text = ss.substring(ss.indexOf("\"") + 1, ss.lastIndexOf("\""));
            }
        }
    }
}
