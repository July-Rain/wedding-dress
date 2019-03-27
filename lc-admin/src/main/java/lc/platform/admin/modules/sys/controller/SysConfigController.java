package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.annotation.SysLog;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.common.validator.ValidatorUtils;
import lc.platform.admin.modules.sys.entity.SysConfigEntity;
import lc.platform.admin.modules.sys.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	@RequestMapping("/list")
	//@RequiresPermissions("sys:config:list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = sysConfigService.queryPage(params);

		return Result.ok().put("page", page);
	}

	@RequestMapping("/info/{id}")
	//@RequiresPermissions("sys:config:info")
	public Result info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.selectById(id);
		
		return Result.ok().put("config", config);
	}

	@SysLog("保存配置")
	@RequestMapping("/save")
	//@RequiresPermissions("sys:config:save")
	public Result save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		config.setId(IdWorker.getId());//
		sysConfigService.save(config);

		return Result.ok();
	}

	@SysLog("修改配置")
	@RequestMapping("/update")
	//@RequiresPermissions("sys:config:update")
	public Result update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return Result.ok();
	}

	@SysLog("删除配置")
	@RequestMapping("/delete")
	//@RequiresPermissions("sys:config:delete")
	public Result delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return Result.ok();
	}

}
