package lc.platform.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.modules.sys.entity.SysRoleDeptEntity;

import java.util.List;



public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> deptIdList);
	

	List<Long> queryDeptIdList(Long[] roleIds) ;


	int deleteBatch(Long[] roleIds);
}
