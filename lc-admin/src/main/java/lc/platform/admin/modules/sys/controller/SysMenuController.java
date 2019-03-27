package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.annotation.SysLog;
import lc.platform.admin.common.exception.GeneralRuntimeException;
import lc.platform.admin.common.utils.Constant;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.modules.sys.entity.SysMenuEntity;
import lc.platform.admin.modules.sys.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 菜单管理
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {
	@Autowired
	private SysMenuService sysMenuService;

	@RequestMapping("/nav")
	public Result nav(){
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		return Result.ok().put("menuList", menuList);
	}

	@RequestMapping("/list")
	//@RequiresPermissions("sys:menu:list")
	public Object list(){
		List<Map<String, Object>> maps = sysMenuService.queryForZtree();
		return maps;
	}


	@RequestMapping("/select")
	//@RequiresPermissions("sys:menu:select")
	public Result select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		return Result.ok().put("menuList", menuList);
	}

	@RequestMapping("/info/{menuId}")
	//@RequiresPermissions("sys:menu:info")
	public Result info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.selectById(menuId);
		return Result.ok().put("menu", menu);
	}

	@SysLog("保存菜单")
	@RequestMapping("/save")
	//@RequiresPermissions("sys:menu:save")
	public Result save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		menu.setMenuId(IdWorker.getId());//
		sysMenuService.insert(menu);
		return Result.ok();
	}

	@SysLog("修改菜单")
	@RequestMapping("/update")
	//@RequiresPermissions("sys:menu:update")
	public Result update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		sysMenuService.updateById(menu);
		return Result.ok();
	}
	

	@SysLog("删除菜单")
	@RequestMapping("/delete")
	//@RequiresPermissions("sys:menu:delete")
	public Result delete(long menuId){
//		if(menuId <= 31){
//			return Result.error("系统菜单，不能删除");
//		}
		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return Result.error("请先删除子菜单或按钮");
		}
		sysMenuService.delete(menuId);
		return Result.ok();
	}
	

	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new GeneralRuntimeException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new GeneralRuntimeException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new GeneralRuntimeException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.selectById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
//		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
//				menu.getType() == Constant.MenuType.MENU.getValue()){
//			if(parentType != Constant.MenuType.CATALOG.getValue()){
//				throw new GeneralRuntimeException("上级菜单只能为目录类型");
//			}
//			return ;
//		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new GeneralRuntimeException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
