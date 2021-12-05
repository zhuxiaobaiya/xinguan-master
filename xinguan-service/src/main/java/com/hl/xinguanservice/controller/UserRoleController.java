package com.hl.xinguanservice.controller;


import com.hl.xinguanservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/xinguanservice/user-role")
@CrossOrigin
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;
}

