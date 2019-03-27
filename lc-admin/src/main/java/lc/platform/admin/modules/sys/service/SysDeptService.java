package lc.platform.admin.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;


public interface SysDeptService extends IService<SysDeptEntity> {

	List<SysDeptEntity> queryList(Map<String, Object> map);


	List<Long> queryDetpIdList(Long parentId);


	List<Long> getSubDeptIdList(Long deptId);

}
