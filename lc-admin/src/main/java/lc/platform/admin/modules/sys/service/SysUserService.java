package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;



public interface SysUserService extends IService<SysUserEntity> {

	PageUtils queryPage(Map<String, Object> params);
	

	List<Long> queryAllMenuId(Long userId);
	

	void save(SysUserEntity user);
	

	void update(SysUserEntity user);


	boolean updatePassword(Long userId, String password, String newPassword);
}
