package com.springbootpract.xfilecount.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @Date 2021-7-5 09：00AM
 * @Author lee
 * @Describe 文件解析计算
 */
@Slf4j
public class FileParseUtil {

    private static Logger logger = LoggerFactory.getLogger(FileParseUtil.class);

    private static  String IN_FILE_NAME = "制造memnerId模板(压测导卡池自己造数据用).txt";

    private static  String OUT_FILE_NAME = "count.txt";

    /**
     * 解析本地文件
     * /Users/bruceli/Desktop/用户中心/制造memnerId模板(压测导卡池自己造数据用).txt
     */
    @SneakyThrows
    public static Map<String, Object> localFileParseCount(String fileReadPath, String fileWritePath) throws FileNotFoundException {
        logger.info("###Class: {}, fileReadPath: {}, fileWritePath", FileParseUtil.class.getName(), fileReadPath + IN_FILE_NAME, fileWritePath + OUT_FILE_NAME);

        //读取
        File inFile = new File(fileReadPath);
        if (!inFile.exists()) {
            inFile.mkdirs();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(fileReadPath)
                .append(IN_FILE_NAME);
        File inFileTran = new File(sb.toString());

        InputStreamReader fileInputStreamReader = new InputStreamReader(new FileInputStream(inFileTran));
        BufferedReader bufferedReader = new BufferedReader(fileInputStreamReader);

        //写出
        File outFile = new File(fileWritePath);
        if (!outFile.exists()) {
            outFile.mkdirs();
        }
        StringBuffer sb2 = new StringBuffer();
        sb2.append(fileWritePath)
                .append(OUT_FILE_NAME);
        File outFileTran = new File(sb2.toString());

        FileWriter fileWriter = new FileWriter(outFileTran);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        //存储
        List<String> list = new ArrayList<>();
        List<String> resList = new ArrayList<>();
        Integer count = 0;
        String line;
        String flag;
        try {
            while ((flag = bufferedReader.readLine()) != null) {
                /**
                 * 行读取（必须将readLine()的返回值作为存储对象，否则会出现隔行读取或者是数据丢失）
                 */
                line = new String(flag.getBytes(),"utf-8");

                //写入
                bufferedWriter.write(line);
                bufferedWriter.newLine();

                //存储
                list.add(line);
//                list = bufferedReader.lines().collect(Collectors.toList());

                //计数
                count ++;
            }
            //刷新
            bufferedWriter.flush();

            //过滤
            if (list != null && list.size() > 0) {
                for (int k = 0; k < list.size(); k ++) {
                    resList.add(list.get(k));
                    if (k > 29) break;
                    logger.info("###item: {}, ###count: {}", list.get(k), k + 1);
                }
            }
            List<String> newResList = resList.stream().map(item -> {
                String newStr = "CSV::" + item;
                return newStr;
            }).filter(item ->
                item.contains("H9")
            ).collect(Collectors.toList());

            //返回
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("data", newResList);
            resMap.put("count", count);
            logger.info("###Class: {}, result: {}, ", FileParseUtil.class.getName(), resMap.toString());
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("###e: {}", e.getMessage());
        } finally {
            //关闭资源
            bufferedWriter.close();
            fileWriter.close();
            bufferedReader.close();
            fileInputStreamReader.close();
        }
        return null;
    }

    private static  String LOCAL_FILE_READ_PATH = "/Users/bruceli/Desktop/用户中心/";

    private static  String LOCAL_FILE_WRITE_PATH_C = "./file/";

    public static void main(String[] args) {
        try {
            Map<String, Object> map = FileParseUtil.localFileParseCount(LOCAL_FILE_READ_PATH, LOCAL_FILE_WRITE_PATH_C);
            System.out.println(map.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
