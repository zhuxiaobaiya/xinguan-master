package com.hl.xinguanservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author huangliang
 * @Date 2021/11/30 19:10
 * @Version 1.0
 * @Description
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
    void delete();
}
