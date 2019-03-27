package lc.platform.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 *
 * @author sinorock.net
 * @email ${email}
 * @date 2018-12-29 14:18:35
 */
@TableName("sys_book")
public class SysBookEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 预约的记录的id
     */
    @TableId
    private String id;
    /**
     * 人名
     */
    private String userName;
    /**
     * 地址
     */
    private String userAddress;
    /**
     * 联系电话
     */
    private String userPhone;
    /**
     * 预约风格
     */
    private String bookType;
    /**
     * 预约摄影师
     */
    private String bookPerson;
    /**
     * 预约摄影师id
     */
    private String bookPersonId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预约时间
     */
    private Date bookTime;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 设置：预约的记录的id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：预约的记录的id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置：人名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取：人名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置：地址
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 获取：地址
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * 设置：联系电话
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * 获取：联系电话
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * 设置：预约风格
     */
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    /**
     * 获取：预约风格
     */
    public String getBookType() {
        return bookType;
    }

    /**
     * 设置：预约摄影师
     */
    public void setBookPerson(String bookPerson) {
        this.bookPerson = bookPerson;
    }

    /**
     * 获取：预约摄影师
     */
    public String getBookPerson() {
        return bookPerson;
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
     * 设置：预约时间
     */
    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    /**
     * 获取：预约时间
     */
    public Date getBookTime() {
        return bookTime;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    public String getBookPersonId() {
        return bookPersonId;
    }

    public void setBookPersonId(String bookPersonId) {
        this.bookPersonId = bookPersonId;
    }
}
