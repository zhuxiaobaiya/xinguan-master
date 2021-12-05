package com.hl.xinguanservice.controller;

import com.hl.common.response.ResponseBean;
import com.hl.xinguanservice.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author huangliang
 * @Date 2021/11/30 19:14
 * @Version 1.0
 * @Description
 */
@Api(tags = "系统模块-文件上传相关接口")
@RestController
@RequestMapping("/system/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @RequiresPermissions({"upload:image"})
    @PostMapping("upload")
    public ResponseBean uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        //返回r对象
        return ResponseBean.success(url);
    }

    @GetMapping("delete")
    public void a(){
        ossService.delete();
    }


}
