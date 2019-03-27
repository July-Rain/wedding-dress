package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;


public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
	

	List<SysMenuEntity> queryListParentId(Long parentId);
	

	List<SysMenuEntity> queryNotButtonList();

	List<Map<String,Object>> queryForZtree();

}
