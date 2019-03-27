package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;


public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
	

	List<Long> queryMenuIdList(Long roleId);


	int deleteBatch(Long[] roleIds);
}
