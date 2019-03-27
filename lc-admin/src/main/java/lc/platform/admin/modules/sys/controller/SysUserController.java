package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.annotation.SysLog;
import lc.platform.admin.common.utils.PageUtils;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.common.utils.UtilPage;
import lc.platform.admin.common.utils.UtilValidate;
import lc.platform.admin.common.validator.Assert;
import lc.platform.admin.common.validator.ValidatorUtils;
import lc.platform.admin.common.validator.group.AddGroup;
import lc.platform.admin.common.validator.group.UpdateGroup;
import lc.platform.admin.modules.sys.entity.SysDeptEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysDeptService;
import lc.platform.admin.modules.sys.service.SysUserRoleService;
import lc.platform.admin.modules.sys.service.SysUserService;
import lc.platform.admin.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/sys/user")
@SuppressWarnings("all")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Resource
    private SysDeptService sysDeptService;

    @RequestMapping("/list")
    //@RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    @RequestMapping("/info")
    public Result info() {
        return Result.ok().put("user", getUser());
    }

    @SysLog("修改密码")
    @RequestMapping("/password")
    public Result password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");
        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return Result.error("原密码不正确");
        }
        return Result.ok();
    }

    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.selectById(userId);
        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return Result.ok().put("user", user);
    }

    @RequestMapping("/getCard")
    //@RequiresPermissions("sys:user:info")
    public Result getCard(String ids) {
        String card="";
        if(UtilValidate.isNotEmpty(ids)){
            String [] arr=ids.split(",");
            for(int i=0;i<arr.length;i++){
                SysUserEntity user = sysUserService.selectById(arr[i]);
                if(UtilValidate.isNotEmpty(user)){
                    if(UtilValidate.isEmpty(card)){
                        card=user.getCardId();
                    }else{
                        card=card+","+user.getCardId();
                    }
                }
            }
        }
        return Result.ok().put("card", card);
    }

    @SysLog("保存用户")
    @RequestMapping("/save")
    //@RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setUserId(IdWorker.getId());//
        sysUserService.save(user);
        return Result.ok();
    }

    @SysLog("修改用户")
    @RequestMapping("/update")
    //@RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        sysUserService.update(user);
        return Result.ok();
    }

    @SysLog("删除用户")
    @RequestMapping("/delete")
    //@RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return Result.error("当前用户不能删除");
        }
        sysUserService.deleteBatchIds(Arrays.asList(userIds));
        return Result.ok();
    }

    /**
     * 多条件查询人员
     *
     * @param infos
     * @return
     */
    @RequestMapping("/queryInfos")
    //@RequiresPermissions("sys:user:list")
    public Result queryInfos(@RequestParam Map<String, String> infos) {
        //初始化统一返回对象
        Result ok = Result.ok();
        //构造查询条件
        EntityWrapper<SysUserEntity> ew = new EntityWrapper<>();
        // ew.orderBy("order_num");
        if (UtilValidate.isEmpty(infos))
            //如果没有不传条件即查询所有
            ew = null;
        else {
            //获取部门id
            String deptId = infos.get("deptId");
            if (UtilValidate.isNotEmpty(deptId))
                ew.eq("dept_id", deptId);

            //获取证件号
            String cardId = infos.get("cardId");
            if (UtilValidate.isNotEmpty(cardId))
                ew.like("card_id", cardId);

            //获取用户id
            String userId = infos.get("userId");
            if (UtilValidate.isNotEmpty(userId) && !userId.equals("0"))
                ew.eq("user_id", userId);

            //获取用户名
            String username = infos.get("username");
            if (UtilValidate.isNotEmpty(username))
                ew.like("username", username);

            //获取email
            String email = infos.get("email");
            if (UtilValidate.isNotEmpty(email))
                ew.eq("email", email);

            //获取手机
            String mobile = infos.get("mobile");
            if (UtilValidate.isNotEmpty(mobile))
                ew.eq("mobile", mobile);

            //获取状态
            String status = infos.get("status");
            if (UtilValidate.isNotEmpty(status))
                ew.eq("status", status);

            //获取创建时间
            String createTime = infos.get("createTime");
            if (UtilValidate.isNotEmpty(createTime))
                ew.ge("create_time", createTime);

        }

        //初始化分页参数
        Integer current = null;
        Integer limit = null;
        Boolean pageFlag = true;

        if (UtilValidate.isNotEmpty(infos.get("page"))) {
            current = Integer.parseInt(infos.get("page"));

            if (UtilValidate.isNotEmpty(infos.get("limit")))
                limit = Integer.parseInt(infos.get("limit"));
            else if (UtilValidate.isNotEmpty(infos.get("rows")))
                limit = Integer.parseInt(infos.get("rows"));
            else
                pageFlag = false;
        } else
            pageFlag = false;

        if (pageFlag) {
            //查询
            Page<SysUserEntity> page = new Page<>(current, limit);
            Page<SysUserEntity> dataPage = sysUserService.selectPage(page, ew);
            ok.put("data", dataPage);
        } else {
            List<SysUserEntity> sysUserEntities = sysUserService.selectList(ew);
            ok.put("data", sysUserEntities);
        }

        return ok;
    }

    /**
     * 多条件查询人员
     *
     * @param infos
     * @return
     */
    @RequestMapping("/queryInfosList")
    //@RequiresPermissions("sys:user:list")
    public Result queryInfosList(@RequestParam Map<String, String> infos) {
        //初始化统一返回对象
        Result ok = Result.ok();
        String pageNo=infos.get("pageNo").toString();
        String pageSize=infos.get("pageSize").toString();
        //构造查询条件
        EntityWrapper<SysUserEntity> ew = new EntityWrapper<>();
        // ew.orderBy("order_num");
        if (UtilValidate.isEmpty(infos))
            //如果没有不传条件即查询所有
            ew = null;
        else {
            //获取部门id
            String deptId = infos.get("deptId");
            if (UtilValidate.isNotEmpty(deptId))
                ew.eq("dept_id", deptId);

            //获取证件号
            String cardId = infos.get("id");
            if (UtilValidate.isNotEmpty(cardId))
                ew.like("card_id", cardId);


            //获取用户名
            String username = infos.get("name");
            if (UtilValidate.isNotEmpty(username))
                ew.like("nick_name", username);

        }

        //构建分页对象
        Page<SysUserEntity> page = new Page<>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        //转换对象
        PageUtils pageUtils = UtilPage.getPageUtils(page, sysUserService, ew);
        List<SysUserEntity> dataList = (List<SysUserEntity>) pageUtils.getList();

        return Result.ok().put("data", dataList).put("totalCount",pageUtils.getTotalCount()).put("pageNo",pageNo).put("pageSize",pageSize);
    }
    @RequestMapping("/getUser")
    //@RequiresPermissions("sys:user:list")
    public Result getUser(@RequestParam Map<String, String> infos) {
        //初始化统一返回对象
        Result ok = Result.ok();
        SysUserEntity user = getUser();

        //获取部门名称
        SysDeptEntity entity = sysDeptService.selectById(user.getDeptId());
        user.setDeptName(entity.getName());
        user.setDeptInfo(entity);
        return ok.put("data", user);
    }


    @RequestMapping("/getAll4Peoper")
    //@RequiresPermissions("sys:user:info")
    public Result getAll4Peoper(String userId) {

        if (UtilValidate.isEmpty(userId)) {
            userId = String.valueOf(getUserId());
        }


        SysUserEntity sysUserEntity = sysUserService.selectById(userId);
        if (UtilValidate.isEmpty(sysUserEntity))
            return Result.error("未查询到相关用户信息!");

        Long deptId = sysUserEntity.getDeptId();
        SysDeptEntity deptEntity = sysDeptService.selectById(deptId);

        if (UtilValidate.isNotEmpty(deptEntity))
            sysUserEntity.setSysDeptEntity(deptEntity);

        return Result.ok().put("data", sysUserEntity);
    }
    @RequestMapping("/listNotAdmin")
    //@RequiresPermissions("sys:user:list")
    public Result listNotAdmin(@RequestParam Map<String, Object> params) {
        List<SysUserEntity> data = sysUserService.selectList(new EntityWrapper<SysUserEntity>().eq("is_admin","0"));
        return Result.ok().put("data", data);
    }
}
