package lc.platform.admin.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.modules.sys.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lc.platform.admin.modules.sys.entity.SysAreaEntity;



/**
 * 区域信息
 *
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-04-17 23:54:04
 */
@RestController
@RequestMapping("mgt/sysarea")
public class SysAreaController extends AbstractController{

    @Autowired
    private SysAreaService sysAreaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("mgt:sysarea:list")
    public Result list(@RequestParam Map<String, Object> params){

        PageUtils page = sysAreaService.queryPage(params);

        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{areaId}")
    //@RequiresPermissions("mgt:sysarea:info")
    public Result info(@PathVariable("areaId") String areaId){

        SysAreaEntity sysArea = sysAreaService.selectById(areaId);

        return Result.ok().put("sysArea", sysArea);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("mgt:sysarea:save")
    public Result save(@RequestBody SysAreaEntity sysArea){

        //you should set the SysAreaEntity's 'id' value
		sysAreaService.insert(sysArea);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("mgt:sysarea:update")
    public Result update(@RequestBody SysAreaEntity sysArea){

        sysAreaService.updateById(sysArea);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("mgt:sysarea:delete")
    public Result delete(@RequestBody String[] areaIds){

		sysAreaService.deleteBatchIds(Arrays.asList(areaIds));

        return Result.ok();
    }

}
