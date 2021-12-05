package com.hl.xinguanservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.hl.xinguanservice.config.OssPropertiesConfig;
import com.hl.xinguanservice.service.OssService;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @Author huangliang
 * @Date 2021/11/30 19:10
 * @Version 1.0
 * @Description
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = OssPropertiesConfig.END_POINT;
        String accessKeyId = OssPropertiesConfig.ACCESS_KEY_ID;
        String accessKeySecret = OssPropertiesConfig.ACCESS_KEY_SECRET;
        String bucketName = OssPropertiesConfig.BUCKET_NAME;

        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //文件名：uuid.扩展名
            String original = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString().replace("-","");
            String fileType = original.substring(original.lastIndexOf("."));
            String newName = fileName + fileType;
            String fileUrl = "avatar" + "/" + newName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileUrl, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址
            String url = "http://" + bucketName + "." + endPoint + "/" + fileUrl;

            return url;
        } catch (IOException e) {

        }
        return null;
    }


    @Test
    public void delete(){
        //获取阿里云存储相关常量
        String endPoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAI5tCZ5Es824uipNQVoPUb";
        String accessKeySecret = "uCM5xeJDpaOt7o9v42MtzNt6yaoynB";
        String bucketName = "zhanggoudang";
        String objectName = "avatar/fbd9829c9f7941408c7755239aa9e083.jfif";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

// 删除文件或目录。如果要删除目录，目录必须为空。
        System.out.println(bucketName);
        System.out.println(objectName);
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
