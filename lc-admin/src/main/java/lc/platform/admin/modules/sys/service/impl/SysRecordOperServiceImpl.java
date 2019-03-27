package lc.platform.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Query;
import lc.platform.admin.modules.sys.dao.SysRecordOperDao;
import lc.platform.admin.modules.sys.entity.SysRecordOperEntity;
import lc.platform.admin.modules.sys.service.SysRecordOperService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRecordOperService")
public class SysRecordOperServiceImpl extends ServiceImpl<SysRecordOperDao, SysRecordOperEntity> implements SysRecordOperService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysRecordOperEntity> page = this.selectPage(
                new Query<SysRecordOperEntity>(params).getPage(),
                new EntityWrapper<SysRecordOperEntity>()
        );

        return new PageUtils(page);
    }

}
