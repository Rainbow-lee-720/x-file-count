package com.springbootpract.xfilecount.controller;

import com.springbootpract.xfilecount.utils.FileParseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Date 2021-7-7 09：00AM
 * @Author lee
 * @Describe 文件解析计算
 */
@RestController
@RequestMapping(value = "/base")
@Slf4j
public class FileCountController {

    private static Logger logger = LoggerFactory.getLogger(FileCountController.class);

//    private static final String LOCAL_FILE_WRITE_PATH = "/Users/bruceli/Desktop/count.txt";
//
//    private static final String LOCAL_FILE_READ_PATH = "/Users/bruceli/Desktop/用户中心/制造memnerId模板(压测导卡池自己造数据用).txt";

    private static  String LOCAL_FILE_READ_PATH = "/Users/bruceli/Desktop/用户中心/";

    private static  String LOCAL_FILE_WRITE_PATH_C = "./file/";

    @RequestMapping(value = "/fileCount")
    @SneakyThrows
    public ResponseEntity fileCount() {
        Long start = System.currentTimeMillis();
        logger.error("###requestUrl: {}, param: {}", "/base/fileCount", "null");
        Map<String, Object> resMap = FileParseUtil.localFileParseCount(LOCAL_FILE_READ_PATH, LOCAL_FILE_WRITE_PATH_C);
        Long end = System.currentTimeMillis();
        logger.error("###response: {}, url: {}, spendTime: {}", resMap.toString(), "/base/fileCount", end - start + "ms");
        return new ResponseEntity(resMap, HttpStatus.OK);
    }

}
