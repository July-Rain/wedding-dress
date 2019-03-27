package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysBookEntity;

import java.util.Map;

/**
 * 角色
 *
 * @author sinorock.net
 * @email ${email}
 * @date 2018-12-29 14:18:35
 */
public interface SysBookService extends IService<SysBookEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

