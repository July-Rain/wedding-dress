package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysRoleDeptEntity;

import java.util.List;


public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {
	

	List<Long> queryDeptIdList(Long[] roleIds);


	int deleteBatch(Long[] roleIds);
}
