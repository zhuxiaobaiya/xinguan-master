package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.utils.AddressUtil;
import com.hl.common.utils.IPUtil;
import com.hl.common.vo.system.LoginLogVO;
import com.hl.common.vo.system.PageVO;
import com.hl.common.vo.system.UserVO;
import com.hl.xinguanservice.entity.respBean.ActiveUser;
import com.hl.xinguanservice.entity.system.LoginLog;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.LoginLogMapper;
import com.hl.xinguanservice.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-22
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {


    /**
     * 登入日志列表
     * @param pageNum
     * @param pageSize
     * @param loginLogVO
     * @return
     */
    @Override
    public PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO) {
        //创建page对象
        Page<LoginLog> loginLogPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("login_time");
        if(loginLogVO.getIp()!=null&&!"".equals(loginLogVO.getIp())){
            wrapper.like("ip",loginLogVO.getIp());
        }
        if(loginLogVO.getLocation()!=null&&!"".equals(loginLogVO.getLocation())){
            wrapper.like("location",loginLogVO.getLocation());
        }
        if(loginLogVO.getUsername()!=null&&!"".equals(loginLogVO.getUsername())){
            wrapper.like("username",loginLogVO.getUsername());
        }
        baseMapper.selectPage(loginLogPage,wrapper);
        List<LoginLog> loginLogs = loginLogPage.getRecords();
        List<LoginLogVO> loginLogVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(loginLogs)){
            for (LoginLog loginLog : loginLogs) {
                LoginLogVO logVO = new LoginLogVO();
                BeanUtils.copyProperties(loginLog,logVO);
                loginLogVOS.add(logVO);
            }
        }

        return new PageVO<>(loginLogPage.getTotal(),loginLogVOS);
    }



    /**
     * 插入登入日志
     * @param request
     */
    @Transactional
    @Override
    public void add(HttpServletRequest request) {
        baseMapper.insert(createLoginLog(request));
    }

    /**
     * 创建登入日志
     * @param
     * @return
     */
    public static LoginLog createLoginLog(HttpServletRequest request) {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(activeUser.getUser().getUsername());
        loginLog.setIp(IPUtil.getIpAddress(request));
        loginLog.setLocation(AddressUtil.getCityInfo(IPUtil.getIpAddress(request)));
        // 获取客户端操作系统
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();
        loginLog.setUserSystem(os.getName());
        loginLog.setUserBrowser(browser.getName());
        loginLog.setLoginTime(new Date());
        return loginLog;
    }


    /**
     * 删除登入日志
     * @param id
     */
    @Transactional
    @Override
    public void delete(Long id) throws SystemException {
        LoginLog loginLog = baseMapper.selectById(id);
        if(loginLog==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"登入日志不存在");
        }
        baseMapper.deleteById(id);
    }

    /**
     * 批量删除日志
     * @param list
     */
    @Override
    public void batchDelete(List<Long> list) throws SystemException {
        for (Long id : list) {
            LoginLog loginLog = baseMapper.selectById(id);
            if(loginLog==null){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"id="+id+"登入日志不存在");
            }
            delete(id);
        }
    }


    @Override
    public List<Map<String, Object>> loginReport(UserVO userVO) {
        return baseMapper.userLoginReport(userVO);
    }
}
