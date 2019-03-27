package lc.platform.admin.modules.sys.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;


public interface SysConfigDao extends BaseMapper<SysConfigEntity> {


	SysConfigEntity queryByKey(String paramKey);


	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
