package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.utils.*;
import lc.platform.admin.common.validator.ValidatorUtils;
import lc.platform.admin.modules.sys.entity.SysDictEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 字典管理
 */
@RestController
@RequestMapping("sys/sysdict")
public class SysDictController extends AbstractController {
    @Autowired
    private SysDictService sysDictService;

    @RequestMapping("/list")
    //@RequiresPermissions("sys:dict:list")
    public Result list(@RequestParam Map<String, String> params) {

        EntityWrapper<SysDictEntity> ew = new EntityWrapper();

        ew.orderBy("type").orderBy("order_num", false);

        if (UtilValidate.isEmpty(params))
            //如果没有不传条件即查询所有
            ew = null;
        else {
            if (UtilValidate.isNotEmpty(params.get("name"))) {
                //获取字典名称
                String name = params.get("name");
                ew.eq("name", name);
            }
            if (UtilValidate.isNotEmpty(params.get("status"))) {
                //获取字典状态
                String status = params.get("status");
                ew.eq("status", status);
            }

            if (UtilValidate.isNotEmpty(params.get("code"))) {
                //获取字典码
                String code = params.get("code");
                ew.eq("code", code);
            }
            if (UtilValidate.isNotEmpty(params.get("value"))) {
                //获取字典值
                String value = params.get("value");
                ew.like("value", value);
            }
        }
        Page<SysDictEntity> page = new Page(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        PageUtils pageUtils = UtilPage.getPageUtils(page, sysDictService, ew);


//        PageUtils page = sysDictService.queryPage(params);

        return Result.ok().put("page", pageUtils);
    }

    @RequestMapping("/info/{id}")
    //@RequiresPermissions("sys:dict:info")
    public Result info(@PathVariable("id") Long id) {
        SysDictEntity dict = sysDictService.selectById(id);
        return Result.ok().put("dict", dict);
    }

    @RequestMapping("/save")
    //@RequiresPermissions("sys:dict:save")
    public Result save(@RequestBody SysDictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);
        dict.setId(IdWorker.getId());
        sysDictService.insert(dict);
        return Result.ok();
    }

    @RequestMapping("/update")
    //@RequiresPermissions("sys:dict:update")
    public Result update(@RequestBody SysDictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict);
        sysDictService.updateById(dict);
        return Result.ok();
    }

    @RequestMapping("/delete")
    //@RequiresPermissions("sys:dict:delete")
    public Result delete(@RequestBody Long[] ids) {
        sysDictService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 多条件查询字典
     *
     * @param infos
     * @return
     */
    @RequestMapping("/queryInfos")
    //@RequiresPermissions("sys:dict:list")
    public Result queryInfos(@RequestParam Map<String, String> infos) {
        //构造统一返回对象
        Result ok = Result.ok();
        //构造查询条件
        EntityWrapper<SysDictEntity> ew = new EntityWrapper<>();
        ew.orderBy("type").orderBy("order_num", true);

        if (UtilValidate.isEmpty(infos))
            //如果没有不传条件即查询所有
            ew = null;
        else {
            if (UtilValidate.isNotEmpty(infos.get("name"))) {
                //获取字典名称
                String name = infos.get("name");
                ew.eq("name", name);
            }
            if (UtilValidate.isNotEmpty(infos.get("delFlag"))) {
                //获取字典状态
                String del_flag = infos.get("delFlag");
                ew.eq("del_flag", del_flag);
            }
            if (UtilValidate.isNotEmpty(infos.get("type"))) {
                //获取分类
                String type = infos.get("type");
                ew.eq("type", type);
            }
            if (UtilValidate.isNotEmpty(infos.get("code"))) {
                //获取字典码
                String code = infos.get("code");
                ew.eq("code", code);
            }
            if (UtilValidate.isNotEmpty(infos.get("value"))) {
                //获取字典值
                String value = infos.get("value");
                ew.eq("value", value);
            }
        }
        //构建分页对象
        Page<SysDictEntity> page = null;
        if (UtilValidate.isNotEmpty(infos.get("page")) && UtilValidate.isNotEmpty(infos.get("rows"))) {
            //有分页参数
            page = new Page<>(Integer.parseInt(infos.get("page")), Integer.parseInt(infos.get("rows")));
        } else {
            //没有分页参数
            page = new Page<>(1, 1000);
        }

        //转换对象
        PageUtils dataPage = UtilPage.getPageUtils(page, sysDictService, ew);

        //封装数据
        ok.put("data", dataPage);

        return ok;
    }

    /**
     * 　　* @Description: 获取当前登录人信息以及系统时间
     * 　　* @param
     * 　　* @return
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/3/20 19:30
     */
    @RequestMapping("/getcreator")
    public Result getCreator() {
        Result ok = Result.ok();

        //获取当前登录用户
        SysUserEntity user = getUser();

        //获取登录用户名称
        String username = user.getNickName();

        //获取当前时间
        ok.put("addTime", UtilMisc.getStringDate());
        ok.put("createUser", user.getUserId());
        ok.put("createUserName", username);
        return ok;
    }
    /**
     * 　　* @Description: 根据type获取字典表中相应的配置项数据
     * 　　* @param type
     * 　　* @return Result
     * 　　* @throws
     * 　　* @author MengyuWu
     * 　　* @date 2018/3/14 19:48
     */

    @RequestMapping("/getdicinfo/{type}")
    //@RequiresPermissions("mgt:planexamine:list")
    public Result getDicInfo(@PathVariable("type") String type){
        Result ok = Result.ok();
        List<SysDictEntity> sysDictEntities = sysDictService.selectList(new EntityWrapper<SysDictEntity>().eq("type", type).orderBy("order_num",true));
        ok.put("data", sysDictEntities);
        return ok;
    }

}
