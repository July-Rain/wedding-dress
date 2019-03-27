package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysAreaEntity;

import java.util.Map;

/**
 * 区域信息
 *
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-04-17 23:54:04
 */
public interface SysAreaService extends IService<SysAreaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

