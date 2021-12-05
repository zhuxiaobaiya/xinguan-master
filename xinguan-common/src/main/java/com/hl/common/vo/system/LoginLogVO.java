package com.hl.common.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author huangliang
 * @Date 2021/11/28 21:17
 * @Version 1.0
 * @Description
 */
@Data
public class LoginLogVO {
    private Long id;

    private String username;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    private String location;

    private String ip;

    private String userSystem;

    private String userBrowser;
}

