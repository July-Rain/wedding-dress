package lc.platform.admin.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysConfigEntity;

import java.util.Map;


public interface SysConfigService extends IService<SysConfigEntity>  {

	PageUtils queryPage(Map<String, Object> params);
	

	public void save(SysConfigEntity config);
	

	public void update(SysConfigEntity config);
	

	public void updateValueByKey(String key, String value);
	

	public void deleteBatch(Long[] ids);
	

	public String getValue(String key);
	

	public <T> T getConfigObject(String key, Class<T> clazz);
	
}
