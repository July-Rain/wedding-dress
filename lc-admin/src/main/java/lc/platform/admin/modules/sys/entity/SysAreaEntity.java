package lc.platform.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 区域信息
 * 
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-04-17 23:54:04
 */

@TableName("sys_area")
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@TableId
	private String areaId;
	/**
	 * 地区编码 
	 */
	private String areaCode;
	/**
	 * 上级地区编码
	 */
	private String parentId;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 是否删除  -1：已删除  0：正常
	 */
	private Integer delFlag;
	@TableField(exist = false)
	private List<?> list;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	/**
	 * 设置：区域id
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	/**
	 * 获取：区域id
	 */
	public String getAreaId() {
		return areaId;
	}
	/**
	 * 设置：地区编码 
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取：地区编码 
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置：上级地区编码
	 */
	public void setParentId(String parentCode) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级地区编码
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：区域名称
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	/**
	 * 获取：区域名称
	 */
	public String getAreaName() {
		return areaName;
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
	 * 设置：是否删除  -1：已删除  0：正常
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：是否删除  -1：已删除  0：正常
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
