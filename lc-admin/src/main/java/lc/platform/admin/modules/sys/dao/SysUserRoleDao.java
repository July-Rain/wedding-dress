package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysUserRoleEntity;

import java.util.List;


public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {
	

	List<Long> queryRoleIdList(Long userId);


	int deleteBatch(Long[] roleIds);
}
