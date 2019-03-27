package lc.platform.admin.modules.sys.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import lc.platform.admin.common.utils.Constant;
import lc.platform.admin.common.utils.Result;
import lc.platform.admin.common.utils.SpringContextUtils;
import lc.platform.admin.common.utils.UtilValidate;
import lc.platform.admin.modules.sys.entity.SysAreaEntity;
import lc.platform.admin.modules.sys.entity.SysDeptEntity;
import lc.platform.admin.modules.sys.entity.SysUserEntity;
import lc.platform.admin.modules.sys.service.SysAreaService;
import lc.platform.admin.modules.sys.service.SysDeptService;
import lc.platform.admin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {
    @Autowired
    private SysDeptService sysDeptService;

    private SysUserService sysUserService;

    @Autowired
    private SysAreaService areaService;

    @RequestMapping("/list")
    //@RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return deptList;
    }

    @RequestMapping("/seletTree")
    //@RequiresPermissions("sys:dept:select")
    public Result seletTree() {
        long start = System.currentTimeMillis();
//        //查询一级部门
//        List<SysDeptEntity> deptList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>()
//                .setSqlSelect("dept_Id", "name")
//                .eq("del_flag", 0)
//                .eq("parent_id", 0)
//                .orderBy("order_num", true));

//        //查询子部门
//        for (SysDeptEntity sysDeptEntity : deptList) {
//            //获取每一个一级部门id
//            Long deptId = sysDeptEntity.getDeptId();
//            //查询子部门并建立关联
//            getSonList(sysDeptEntity, deptId);
//        }

        //查询所有部门
        List<SysDeptEntity> deptAllList = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>()
                .setSqlSelect("dept_Id", "name","parent_id")
                .eq("del_flag", 0)
                .orderBy("order_num", true));

        List<SysDeptEntity> deptList = bulidDeptTree(deptAllList);

        long end = System.currentTimeMillis();
        logger.info("部门数据-执行时间："+(end-start) + " 毫秒");
        return Result.ok().put("trueData", deptList);
    }

    /**
     * 两层循环实现建树
     * @param list 传入的树节点列表
     * @return
     */
    private List<SysDeptEntity> bulidDeptTree(List<SysDeptEntity> list) {

        List<SysDeptEntity> finalTrees = new ArrayList<SysDeptEntity>();

        for (SysDeptEntity dept : list) {

            if (dept.getParentId() == 0) {
                finalTrees.add(dept);
            }

            for (SysDeptEntity d : list) {
                if ((d.getParentId()).longValue() == (dept.getId()).longValue()) {
                    if (dept.getList() == null) {
                        dept.setList(new ArrayList<SysDeptEntity>());
                    }
                    dept.getList().add(d);
                }
            }
        }
        return finalTrees;
    }

    private void getResult(SysDeptEntity sysDeptEntity) {
        List<?> list = sysDeptEntity.getList();
        //临时集合
        ArrayList<SysDeptEntity> tempList = new ArrayList<>();
        if (UtilValidate.isNotEmpty(list)) {
            for (Object o : list) {
                HashMap<String, Object> map = new HashMap<>();
                SysDeptEntity sd = (SysDeptEntity) o;
                map.put("id", sd.getDeptId());
                map.put("name", sd.getName());
            }
        }
    }

    @RequestMapping("/select")
    //@RequiresPermissions("sys:dept:select")
    public Result select() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return Result.ok().put("deptList", deptList);
    }

    @RequestMapping("/info")
    //@RequiresPermissions("sys:dept:list")
    public Result info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }

                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;
        }
        return Result.ok().put("deptId", deptId);
    }

    @RequestMapping("/info/{deptId}")
    //@RequiresPermissions("sys:dept:info")
    public Result info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.selectById(deptId);

        return Result.ok().put("dept", dept);
    }

    @RequestMapping("/save")
    //@RequiresPermissions("sys:dept:save")
    public Result save(@RequestBody SysDeptEntity dept) {
        /*SysDeptEntity entity=sysDeptService.selectById(dept.getDeptCode());
        if(UtilValidate.isNotEmpty(entity)){
            return Result.error("部门编号已存在！");
        }
        EntityWrapper<SysDeptEntity> entityWrapper = new EntityWrapper<>();
        entityWrapper.where("dept_code={0}", dept.getDeptCode());
        List<SysDeptEntity> sysDeptEntities = sysDeptService.selectList(entityWrapper);
        if (UtilValidate.isNotEmpty(sysDeptEntities)) {
            SysDeptEntity parentDept = sysDeptEntities.get(0);
            if (sysDeptEntities.size() > 1) {
                return Result.error("当前的部门编码存在多个，请删除掉冗余的部门数据！");
            } else {
                parentDept.setVirtualFlag(1);//虚拟
                sysDeptService.update(parentDept, new EntityWrapper<SysDeptEntity>());
            }
        }*/
        if (dept.getVirtualFlag() == 1 || UtilValidate.isEmpty(dept.getDeptCode())) {
            dept.setDeptCode(null);
        }
        dept.setDeptId(IdWorker.getId());//
        sysDeptService.insert(dept);

        return Result.ok();
    }

    @RequestMapping("/update")
    //@RequiresPermissions("sys:dept:update")
    public Result update(@RequestBody SysDeptEntity dept) {
        /*SysDeptEntity entity=sysDeptService.selectById(dept.getDeptCode());
        if(UtilValidate.isNotEmpty(entity)&&(!entity.getDeptId().equals(dept.getDeptId()))){
            return Result.error("部门编号已存在！");
        }*/
//        EntityWrapper<SysDeptEntity> entityWrapper = new EntityWrapper<>();
//        entityWrapper.where("parent_id={0}", 0);
//        List<SysDeptEntity> sysDeptEntity = sysDeptService.selectList(entityWrapper);
//        if ((0==dept.getParentId())&&(UtilValidate.isNotEmpty(sysDeptEntity) || sysDeptEntity.size() >= 1)) {
//            return Result.error("已存在一个一级部门，请勿再添加一级部门！");
//        }
        if (dept.getDeptId() != null && dept.getDeptId() > 0) {
            if (dept.getVirtualFlag() == 1 || UtilValidate.isEmpty(dept.getDeptCode())) {
                dept.setDeptCode(null);
            }
            dept.setDelFlag(0);
            sysDeptService.updateAllColumnById(dept);
            return Result.ok();
        }else{
            return Result.error("部门记录数据参数缺失！");
        }
    }

    @RequestMapping("/delete")
    //@RequiresPermissions("sys:dept:delete")
    public Result delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return Result.error("请先删除子部门");
        }
        sysDeptService.deleteById(deptId);
        return Result.ok();
    }

    /**
     * 通过父节点往下查询子节点
     *
     * @param sysDeptEntity
     * @param deptId
     */
    private void getSonList(SysDeptEntity sysDeptEntity, Long deptId) {
        //通过父节点查询子节点
        List<SysDeptEntity> sysDeptEntities = sysDeptService.selectList(new EntityWrapper<SysDeptEntity>()
                .setSqlSelect("dept_Id", "name", "parent_id")
                .eq("del_flag", 0)
                .eq("parent_id", deptId)
                .orderBy("order_num", true));
        //建立关联
        sysDeptEntity.setList(sysDeptEntities);

        //递归
        if (UtilValidate.isNotEmpty(sysDeptEntities))
            for (SysDeptEntity deptEntity : sysDeptEntities)
                getSonList(deptEntity, deptEntity.getDeptId());

    }

    @RequestMapping("/uNameToDname")
    //@RequiresPermissions("sys:user:info")
    public HashMap<String, String> uNameToDname(String userId) {
        sysUserService = SpringContextUtils.getBean("sysUserService", SysUserService.class);
        HashMap<String, String> resultMap = new HashMap<>();
        if (UtilValidate.isNotEmpty(userId)) {
            if (userId.contains(",")) {
                String[] split = userId.split(",");
                userId = split[0];
            }
            SysUserEntity sysUserEntity = sysUserService.selectById(userId);
            Long deptId = sysUserEntity.getDeptId();
            SysDeptEntity entity = sysDeptService.selectById(deptId);

            if (UtilValidate.isNotEmpty(entity)) {
                String name = entity.getName();
                Long id = entity.getDeptId();
                resultMap.put("name", name);
                resultMap.put("id", id + "");
            }
        } else {
            resultMap = null;
        }
        return resultMap;
    }

    @RequestMapping("/seletCodeTree")
    public Result seletCodeTree() {
        List<SysAreaEntity> areaEntityList = areaService.selectList(new EntityWrapper<SysAreaEntity>().orderBy("order_num"));
        return Result.ok().put("deptCodeList", areaEntityList);
    }
    @RequestMapping("/getUpinfo/{deptId}")
    //@RequiresPermissions("sys:dept:info")
    public Result getUpinfo(@PathVariable("deptId") Long deptId) {
        String executorDept="";
        SysDeptEntity dept = sysDeptService.selectById(deptId);
        if(UtilValidate.isNotEmpty(dept)){
            if(UtilValidate.isNotEmpty(dept.getDeptCode())){
                if(dept.getDeptCode().length()<=6){
                    if(dept.getName().length()>=3){
                        executorDept = dept.getName().substring(0,dept.getName().length()-3);
                    }else{
                        executorDept = dept.getName();
                    }
                }else{
                    String supcode=dept.getDeptCode().substring(0,6);
                    SysDeptEntity supEntity=sysDeptService.selectOne(new EntityWrapper<SysDeptEntity>().eq("dept_code",supcode));
                    if(UtilValidate.isNotEmpty(supEntity)){
                        if(UtilValidate.isNotEmpty(supEntity.getName())){
                            if(supEntity.getName().length()>=3){
                                executorDept = supEntity.getName().substring(0,supEntity.getName().length()-3);
                            }else{
                                executorDept = dept.getName();
                            }
                        }
                    }
                }
            }
        }
        return Result.ok().put("deptName", executorDept);
    }
}
