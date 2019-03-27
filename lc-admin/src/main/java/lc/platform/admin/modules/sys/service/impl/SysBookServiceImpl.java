package lc.platform.admin.modules.sys.service.impl;

import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Query;
import lc.platform.admin.common.utils.UtilValidate;
import lc.platform.admin.modules.sys.dao.SysBookDao;
import lc.platform.admin.modules.sys.entity.SysBookEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysBookService;
import lc.platform.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysBookService")
public class SysBookServiceImpl extends ServiceImpl<SysBookDao, SysBookEntity> implements SysBookService {

    @Autowired
    private SysUserService userService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<SysBookEntity> ew =new EntityWrapper<SysBookEntity>();
        String userId=(String)params.get("userId");
        if(UtilValidate.isNotEmpty(userId)){
            ew.eq("book_person_id",userId);
        }
        Page<SysBookEntity> page = this.selectPage(
                new Query<SysBookEntity>(params).getPage(),ew
        );
        List<SysBookEntity> list = page.getRecords();
        list.stream().forEach(e->{
            SysUserEntity userEntity = userService.selectById(e.getBookPersonId());
            if(UtilValidate.isNotEmpty(userEntity)){
                e.setBookPerson(userEntity.getNickName());
            }


        });

        return new PageUtils(page);
    }

}
