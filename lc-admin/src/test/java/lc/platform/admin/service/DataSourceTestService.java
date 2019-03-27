package lc.platform.admin.service;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.datasources.DataSourceNames;
import lc.platform.admin.datasources.annotation.DataSource;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataSourceTestService {
    @Autowired
    private SysUserService sysUserService;

    public SysUserEntity queryUser(Long userId){
        return sysUserService.selectById(userId);
    }

    public void insert(){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(IdWorker.getId());
        userEntity.setUsername(IdWorker.getIdStr());
        sysUserService.insert(userEntity);
    }

    @DataSource(name = DataSourceNames.PROVINCE)
    public void insert2(){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(IdWorker.getId());
        userEntity.setUsername(IdWorker.getIdStr());
        sysUserService.insert(userEntity);
    }

    public void update(Long userId){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(userId);
        userEntity.setUsername(IdWorker.getIdStr());
        sysUserService.update(userEntity);
    }

    @DataSource(name = DataSourceNames.PROVINCE)
    public void update2(Long userId){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserId(userId);
        userEntity.setUsername(IdWorker.getIdStr());
        sysUserService.update(userEntity);
    }

    @DataSource(name = DataSourceNames.PROVINCE)
    public SysUserEntity queryUser2(Long userId){
        return sysUserService.selectById(userId);
    }
}
