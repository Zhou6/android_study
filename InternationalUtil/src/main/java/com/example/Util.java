package com.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 把石墨文档网页的翻译批量输出到国际化的文件里
 */
public class Util {
    private static List<Content> list = new ArrayList<>();
    private static final int begin_row = 2;
    private static final int end_row = -1;
    private static final int begin_column = 1;

    private static final String shimo_id = "uKfstl7LUnMwaryZ?r=RDR4J/";//这个参数每次国际化都要改成石墨文档的url后面的参数

    private static final String ModuleName = "InternationalUtil";
    private static final String file_loaction = ModuleName + "/build";
    private static final String need_to_output_file = "/app/src/main/res/";

    private static final String url = "https://api.shimo.im/files/";
    private static final String[] values_paths = {"values-zh-rCN", "values-zh", "values"};
    private static String shimo_file_path = "";

    public static void main(String args[]) {
        initPath();
        initResponse();
        readExcel();
        System.out.println("国际化已完成");
    }

    private static void initResponse() {
        String json = doRequest(url + shimo_id);
        Gson gson = new Gson();
        Model model = gson.fromJson(json, Model.class);
        String[] strings = model.getContent().substring(1).split("\"],\"");
        for (String s : strings) {
            Content c = new Content(s);
            if (c.text == null || c.text.equals("")) {
                continue;
            }
            list.add(c);
        }
        updateExcel(list);
    }

    private static void initPath() {
        String path = Util.class.getResource("").toString();
        System.out.println(path);
        path = "/" + path.substring(path.indexOf("/") + 1);
        shimo_file_path = path.substring(0, path.lastIndexOf(file_loaction) + file_loaction.length());
        String gen_path = path.substring(0, path.lastIndexOf(ModuleName) - 1) + need_to_output_file;
        for (int a = 0; a < values_paths.length; a++) {
            values_paths[a] = gen_path + values_paths[a] + "/strings.xml";
        }
    }

    private static String doRequest(String url) {
        HttpURLConnection httpConn = null;
        StringBuffer contentBuffer = new StringBuffer();
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");// IE代理进行下载
            httpConn.setConnectTimeout(5000);
            httpConn.setReadTimeout(10000);
            int responseCode = httpConn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return "fail";
            }
            InputStream inStr = httpConn.getInputStream();
            InputStreamReader istreamReader = new InputStreamReader(inStr, "UTF-8");
            BufferedReader buffStr = new BufferedReader(istreamReader);

            String str = null;
            while ((str = buffStr.readLine()) != null)
                contentBuffer.append(str);
            inStr.close();
        } catch (IOException e) {
            e.printStackTrace();
            contentBuffer = null;
            System.out.println("error: " + url);
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return contentBuffer != null ? contentBuffer.toString() : null;
    }

    private static void updateExcel(List<Content> list) {
        try {
            File f = new File(shimo_file_path);
            File f1 = new File(f, "国际化.xls");
            shimo_file_path = f1.getAbsolutePath();
            System.out.println("文档path:" + shimo_file_path);
            WritableWorkbook book = Workbook.createWorkbook(f1);
            // 添加一个工作表
            WritableSheet sheet = book.createSheet("国际化", 0);
            for (Content c : list) {
                sheet.addCell(new Label(c.x_int, c.y, c.text));
            }
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println("updateExcel:" + e);
        }
    }

    private static void readExcel() {
        String s[] = new String[4];
        try {
            Workbook book = Workbook.getWorkbook(new File(shimo_file_path));
            Sheet sheet = book.getSheet(0);
            int column = sheet.getColumns();
            int row = sheet.getRows();
            if (end_row > 0 && end_row > begin_row && end_row < row) {
                row = end_row + 1;
            }
            for (int j = begin_column + 1; j < column; j++) {
                s[j - begin_column - 1] = "\n";
                for (int i = begin_row; i < row; i++) {
                    Cell cell = sheet.getCell(begin_column, i);
                    Cell c = sheet.getCell(j, i);
                    if (cell.getContents() != null && !cell.getContents().equals("") && c.getContents() != null && !c.getContents().equals("")) {
                        s[j - begin_column - 1] += "\t" + "<string name=\"" + cell.getContents() + "\">" + c.getContents() + "</string>" + "\n";
                    }
                }
                s[j - begin_column - 1] += "</resources>";
            }
            book.close();
            for (int a = 0; a < s.length && a < values_paths.length; a++) {
                writeEndLine(s[a], values_paths[a]);
            }
        } catch (Exception e) {
            System.out.println("readExcel:" + e);
        }
    }

    private static void writeEndLine(String filename, String filePath) {
        try {
            RandomAccessFile file0 = new RandomAccessFile(filePath, "rw");
            long start = file0.getFilePointer();
            long len = file0.length();
            long nextend = start + len - 1;

            file0.seek(nextend);
            byte buf[] = new byte[1];
            while (nextend > start) {
                file0.read(buf, 0, 1);
                if (buf[0] == '\n' && (file0.readLine()) != null) {
                    file0.seek(nextend);
                    break;
                }
                nextend--;
                file0.seek(nextend);
            }
            file0.write(filename.getBytes("utf-8"));
            file0.close();
        } catch (Exception e) {
            System.out.println("writeEndLine:" + e);
        }
    }
}