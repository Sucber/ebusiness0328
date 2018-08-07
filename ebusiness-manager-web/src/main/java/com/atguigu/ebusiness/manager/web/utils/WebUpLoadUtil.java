package com.atguigu.ebusiness.manager.web.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class WebUpLoadUtil {
    public static String uploadImage(MultipartFile file){
        String path = WebUpLoadUtil.class.getClassLoader().getResource("tracker.conf").getFile();
        try{
            ClientGlobal.init(path);
        }catch (IOException e){
            e.printStackTrace();
        }catch (MyException e){
            e.printStackTrace();
        }
        TrackerClient trackerClient = new TrackerClient();

        TrackerServer connection = null;
        try {
            connection = trackerClient.getConnection();
        }catch (Exception e){

        }

        StorageClient storageClient = new StorageClient(connection,null);

        String[] gifs = new String[0];
        try {
            // 通过最后一个。获取扩展名
            String originalFilename = file.getOriginalFilename();
            int i = originalFilename.lastIndexOf(".");
            String substring = originalFilename.substring(i+1);
            gifs = storageClient.upload_file(file.getBytes(), substring, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        String url = "http://192.168.186.123";
        for (String gif : gifs) {
            url = url+"/"+gif;
        }

        return url;

    }
}
