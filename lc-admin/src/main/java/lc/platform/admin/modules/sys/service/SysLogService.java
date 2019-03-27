package lc.platform.admin.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysLogEntity;

import java.util.Map;



public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
