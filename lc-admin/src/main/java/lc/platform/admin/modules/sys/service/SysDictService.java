package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysDictEntity;

import java.util.Map;


public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

