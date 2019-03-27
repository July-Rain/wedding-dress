package lc.platform.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统增删改日志
 * 
 * @author XuMinglu
 * @email 542686693@qq.com
 * @date 2018-03-21 17:51:17
 */
@TableName("sys_record_oper")
public class SysRecordOperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作记录id
	 */
	@TableId
	private Long id;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 操作类型-insert delect update
	 */
	private String operSql;
	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 最后修改时间
	 */
	private Date lastUpdTime;
	/**
	 * 最后执行修改事务人id
	 */
	private String lastUpdPersonId;
	/**
	 * 最后执行修改事务人名称
	 */
	private String lastUpdPersonName;

	/**
	 * 设置：操作记录id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：操作记录id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：请求方法
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * 获取：请求方法
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * 设置：操作类型-insert delect update
	 */
	public void setOperSql(String operSql) {
		this.operSql = operSql;
	}
	/**
	 * 获取：操作类型-insert delect update
	 */
	public String getOperSql() {
		return operSql;
	}
	/**
	 * 设置：IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：IP地址
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：部门id
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 * 获取：部门id
	 */
	public String getDeptId() {
		return deptId;
	}
	/**
	 * 设置：最后修改时间
	 */
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}
	/**
	 * 获取：最后修改时间
	 */
	public Date getLastUpdTime() {
		return lastUpdTime;
	}
	/**
	 * 设置：最后执行修改事务人id
	 */
	public void setLastUpdPersonId(String lastUpdPersonId) {
		this.lastUpdPersonId = lastUpdPersonId;
	}
	/**
	 * 获取：最后执行修改事务人id
	 */
	public String getLastUpdPersonId() {
		return lastUpdPersonId;
	}
	/**
	 * 设置：最后执行修改事务人名称
	 */
	public void setLastUpdPersonName(String lastUpdPersonName) {
		this.lastUpdPersonName = lastUpdPersonName;
	}
	/**
	 * 获取：最后执行修改事务人名称
	 */
	public String getLastUpdPersonName() {
		return lastUpdPersonName;
	}
}
