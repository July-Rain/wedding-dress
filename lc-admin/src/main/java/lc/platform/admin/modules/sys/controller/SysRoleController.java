package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.annotation.SysLog;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.common.validator.ValidatorUtils;
import lc.platform.admin.modules.sys.entity.SysRoleEntity;
import lc.platform.admin.modules.sys.service.SysRoleDeptService;
import lc.platform.admin.modules.sys.service.SysRoleMenuService;
import lc.platform.admin.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	@RequestMapping("/list")
	//@RequiresPermissions("sys:role:list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);

		return Result.ok().put("page", page);
	}
	
	@RequestMapping("/select")
	//@RequiresPermissions("sys:role:select")
	public Result select(){
		List<SysRoleEntity> list = sysRoleService.selectList(null);
		
		return Result.ok().put("list", list);
	}
	
	@RequestMapping("/info/{roleId}")
	//@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.selectById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);
		
		return Result.ok().put("role", role);
	}

	@SysLog("保存角色")
	@RequestMapping("/save")
	//@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		role.setRoleId(IdWorker.getId());//
		sysRoleService.save(role);
		
		return Result.ok();
	}
	
	@SysLog("修改角色")
	@RequestMapping("/update")
	//@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return Result.ok();
	}
	
	@SysLog("删除角色")
	@RequestMapping("/delete")
	//@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}
