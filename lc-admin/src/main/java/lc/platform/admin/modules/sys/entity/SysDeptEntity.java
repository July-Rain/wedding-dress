package lc.platform.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 */
@TableName("sys_dept")
@SuppressWarnings("all")
public class SysDeptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //部门ID
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    //部门或地区编码
    private String deptCode;
    //部门或地区编码
    @TableField(exist = false)
    private String codeName;
    //上级部门ID，一级部门为0
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 虚拟标识：存在一个虚拟的部门，用来查询所有下级部门中的数据；
     */
    private int virtualFlag;

    //部门名称
    private String name;
    /**
     * 部门简称
     */
    private String abbName;
    //上级部门名称
    @TableField(exist = false)
    private String parentName;
    //排序
    private Integer orderNum;
    //区域的名称
    private String areaName;
    @TableLogic
    private Integer delFlag;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<SysDeptEntity> list;

    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private List<SysDeptEntity> children;

    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private String label;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAbbName() {
        return abbName;
    }

    public void setAbbName(String abbName) {
        this.abbName = abbName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Long getId() {
        return deptId;
    }

    public List<SysDeptEntity> getChildren() {
        return list;
    }

    public void setChildren(List<SysDeptEntity> children) {
        this.children = children;
    }

    public String getLabel() {
        return name;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    /**
     * 设置：上级部门ID，一级部门为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：上级部门ID，一级部门为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：部门名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：排序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：排序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<SysDeptEntity> getList() {
        return list;
    }

    public void setList(List<SysDeptEntity> list) {
        this.list = list;
    }

    public int getVirtualFlag() {
        return virtualFlag;
    }

    public void setVirtualFlag(int virtualFlag) {
        this.virtualFlag = virtualFlag;
    }
}
