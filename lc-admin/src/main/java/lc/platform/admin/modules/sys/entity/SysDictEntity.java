package lc.platform.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 数据字典
 */
@TableName("sys_dict")
public class SysDictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String name;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String type;
    /**
     * 字典码
     */
    @NotBlank(message = "字典码不能为空")
    private String code;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String value;
    /**
     * 上级类型id
     */
    private String parentTypeCode;
    /**
     * 注销原因
     */
    private String delReason;
    /**
     * 创建人的id
     */
    private Long createUser;

    /**
     * 创建人的名称
     */
    @TableField(exist = false)
    private String createUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    public String getDelReason() {
        return delReason;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getDelTime() {
        return delTime;

    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }

    /**
     * 注销时间
     */
    private String delTime;

	public String getParentTypeCode() {
		return parentTypeCode;
	}

	public void setParentTypeCode(String parentTypeCode) {
		this.parentTypeCode = parentTypeCode;
	}

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String addTime;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标记  -1：已删除  0：正常
     */
    @TableLogic
    private Integer delFlag;
    /**
     * 字典状态 0-注销  1-正常
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：字典名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：字典名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：字典类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取：字典类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置：字典码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取：字典码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置：字典值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取：字典值
     */
    public String getValue() {
        return value;
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

    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置：删除标记  -1：已删除  0：正常
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取：删除标记  -1：已删除  0：正常
     */
    public Integer getDelFlag() {
        return delFlag;
    }
}
