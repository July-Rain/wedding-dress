package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysRecordOperEntity;

import java.util.Map;

/**
 * 系统增删改日志
 *
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-03-21 17:51:17
 */
public interface SysRecordOperService extends IService<SysRecordOperEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

