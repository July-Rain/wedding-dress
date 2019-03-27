package lc.platform.admin.modules.sys.service.impl;

import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Query;
import lc.platform.admin.modules.sys.dao.SysAreaDao;
import lc.platform.admin.modules.sys.entity.SysAreaEntity;
import lc.platform.admin.modules.sys.service.SysAreaService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;



@Service("sysAreaService")
public class SysAreaServiceImpl extends ServiceImpl<SysAreaDao, SysAreaEntity> implements SysAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysAreaEntity> page = this.selectPage(
                new Query<SysAreaEntity>(params).getPage(),
                new EntityWrapper<SysAreaEntity>()
        );

        return new PageUtils(page);
    }

}
