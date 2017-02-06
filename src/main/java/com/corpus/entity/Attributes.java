package com.corpus.entity;

import java.sql.Timestamp;

/**
 * 属性表
 * @author Lenovo
 *
 */
public class Attributes {
	private Integer id;//属性ID
	
	private String attrname;//属性名
	
	private byte attrtype;//属性类型0为已知属性 1为待标注属性
	
	private byte oprtype;//操作类型 0为选择 1为填写

	private byte distribased;//是否可作为任务分配依据 0为否 1为是
	
	private Timestamp createtime;//创建时间
	
	private Timestamp updatetime;//更新时间

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public byte getAttrtype() {
		return attrtype;
	}

	public void setAttrtype(byte attrtype) {
		this.attrtype = attrtype;
	}

	public byte getOprtype() {
		return oprtype;
	}

	public void setOprtype(byte oprtype) {
		this.oprtype = oprtype;
	}

	public byte getDistribased() {
		return distribased;
	}

	public void setDistribased(byte distribased) {
		this.distribased = distribased;
	}
}
