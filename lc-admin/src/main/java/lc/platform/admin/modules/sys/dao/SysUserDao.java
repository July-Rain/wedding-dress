package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysUserEntity;

import java.util.List;


public interface SysUserDao extends BaseMapper<SysUserEntity> {
	

	List<String> queryAllPerms(Long userId);
	

	List<Long> queryAllMenuId(Long userId);

}
