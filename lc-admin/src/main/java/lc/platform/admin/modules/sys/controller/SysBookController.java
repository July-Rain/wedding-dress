package lc.platform.admin.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.utils.Constant;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.modules.sys.entity.SysBookEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 角色
 *
 * @author sinorock.net
 * @email ${email}
 * @date 2018-12-29 14:18:35
 */
@RestController
@RequestMapping("mgt/sysbook")
public class SysBookController extends AbstractController{

    @Autowired
    private SysBookService sysBookService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public Result list(@RequestParam Map<String, Object> params){
        //获取当前登陆人信息
        SysUserEntity userEntity=getUser();
        if(userEntity.getUserId()!=Constant.SUPER_ADMIN){
            params.put("userId",userEntity.getUserId());
        }
        PageUtils page = sysBookService.queryPage(params);

        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") String id){

        SysBookEntity sysBook = sysBookService.selectById(id);

        return Result.ok().put("sysBook", sysBook);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public Result save(@RequestBody SysBookEntity sysBook){

        sysBook.setId(IdWorker.getIdStr());
        sysBook.setCreateTime(new Date());
		sysBookService.insert(sysBook);

        return Result.ok().put("id",sysBook.getId());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public Result update(@RequestBody SysBookEntity sysBook){

        sysBookService.updateById(sysBook);

        return Result.ok().put("id",sysBook.getId());
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public Result delete(@RequestBody String[] ids){

		sysBookService.deleteBatchIds(Arrays.asList(ids));

        return Result.ok();
    }

}
