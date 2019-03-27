package lc.platform.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import lc.platform.admin.modules.sys.entity.SysDeptEntity;

import java.util.List;


public interface SysDeptDao extends BaseMapper<SysDeptEntity> {


    List<Long> queryDetpIdList(Long parentId);

}
