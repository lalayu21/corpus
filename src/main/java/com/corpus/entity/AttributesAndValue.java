package com.corpus.entity;

import java.util.List;

import com.corpus.entity.AttributeValues;

/**
 * 属性和属性值的扩展类
 * @author Lenovo
 *
 */
public class AttributesAndValue {
	private Integer aid;//属性id
	
	private String attrname;//属性名称
	
	private byte oprtype;//操作类型
	
	private byte status;//属性状态 
	
	private List<AttributeValues> listv;

	
	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getOprtype() {
		return oprtype;
	}

	public void setOprtype(byte oprtype) {
		this.oprtype = oprtype;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public List<AttributeValues> getListv() {
		return listv;
	}

	public void setListv(List<AttributeValues> listv) {
		this.listv = listv;
	}
	
}
