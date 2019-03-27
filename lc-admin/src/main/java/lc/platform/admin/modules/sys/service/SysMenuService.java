package lc.platform.admin.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import lc.platform.admin.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;


public interface SysMenuService extends IService<SysMenuEntity> {


	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);


	List<SysMenuEntity> queryListParentId(Long parentId);
	

	List<SysMenuEntity> queryNotButtonList();
	

	List<SysMenuEntity> getUserMenuList(Long userId);


	void delete(Long menuId);

	List<Map<String,Object>> queryForZtree();
}
