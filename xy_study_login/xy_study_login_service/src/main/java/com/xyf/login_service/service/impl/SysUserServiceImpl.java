package com.xyf.login_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.login_api.domain.SysUser;
import com.xyf.login_api.utils.ExcelUtils;
import com.xyf.login_service.mapper.SysUserMapper;
import com.xyf.login_service.service.SysUserService;
import com.xyf.result.R;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.xyf.result.CommonConstants.STATUS_NORMAL;


/**
 * userService业务层处理
 *
 * @author birancloud
 * @date 2021-04-18
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    private final SysUserMapper sysUserMapper;


    /**
     * 分页查询
     * @param page
     * @param sysUser
     * @return
     */
    @Override
    public Page fetchPage(Page page, SysUser sysUser) {
        return page(page,this.getLambdaQueryWrapper(sysUser));
    }



    /**
     * 数据导入
     * @param multipartFile
     * @return
     */
    @Override
    public R importExcel(MultipartFile multipartFile)  {

        try {
            List<SysUser> sysUserList = ExcelUtils.importExcel(multipartFile, 0, 1, SysUser.class);
            long startTime = System.currentTimeMillis(); //程序开始记录时间
            List<String> strings = insertData(sysUserList,100);
            long endTime   = System.currentTimeMillis(); //程序结束记录时间
            long TotalTime = endTime - startTime;       //总消耗时间
            return R.ok(sysUserList.size()+"_"+TotalTime);
        } catch (Exception e) {
            log.error("导入失败"+e.getMessage());
            return R.failed();
        }
    }

    private LambdaQueryWrapper<SysUser> getLambdaQueryWrapper(SysUser sysUser){
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<SysUser>();
        lqw.eq(SysUser::getIsDel,STATUS_NORMAL);

        if (StringUtils.isNotBlank(sysUser.getUserName())){
            lqw.like(SysUser::getUserName , sysUser.getUserName());
        }
        if (StringUtils.isNotBlank(sysUser.getPossWord())){
            lqw.eq(SysUser::getPossWord , sysUser.getPossWord());
        }
        if (StringUtils.isNotBlank(sysUser.getUserType())){
            lqw.eq(SysUser::getUserType , sysUser.getUserType());
        }
        if (StringUtils.isNotBlank(sysUser.getUserTel())){
            lqw.eq(SysUser::getUserTel , sysUser.getUserTel());
        }
        if (StringUtils.isNotBlank(sysUser.getUserRemark())){
            lqw.eq(SysUser::getUserRemark , sysUser.getUserRemark());
        }
        lqw.orderByDesc(SysUser::getUserId);
        return lqw;
    }
    private  List<String> insertData(List<SysUser> users,int dealSize){
        if (!users.isEmpty()){
            //数据总的大小
            int count = users.size();        //10000
            //每个线程数据集
            List<SysUser> threadUserList = null;
            int runSize = (count / dealSize) + 1;  //100
            /*threadPoolTaskExecutor.*/
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(runSize,
                    350,
                    30L, TimeUnit.SECONDS, new SynchronousQueue<>());

            for (int i = 0; i < runSize; i++) {
                int startIndex = (i * dealSize);
                if ((i+1) == runSize){
                    int endIndex = count;
                    threadUserList =  users.subList(startIndex,endIndex);
                }else {
                    int endIndex = (i +1) * dealSize;
                    threadUserList = users.subList(startIndex,endIndex);
                }
                importThread importThread = new importThread(this, threadUserList);
                threadPoolExecutor.execute(importThread);
            }
            threadPoolExecutor.shutdown();
        }
        return null;
    }
}

class importThread extends Thread{
    private SysUserServiceImpl sysUserService;
    private List<SysUser> userList;
    importThread(SysUserServiceImpl sysUserService, List<SysUser> userList) {
        this.sysUserService = sysUserService;
        this.userList = userList;
    }
    @Override
    public void run() {
       if (!userList.isEmpty()){
           sysUserService.saveBatch(userList);
       }

    }
}

