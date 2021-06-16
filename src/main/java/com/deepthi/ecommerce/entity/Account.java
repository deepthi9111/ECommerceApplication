package com.deepthi.ecommerce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_user_account")
public class Account 
{
	@Id
	@Column(name = "acno")
	private Long acno;
	
	@Column
	private String ifsc;
	
	@Column
	private String branch;
	
	@Column
	private String bank;
	
	public Account(Long acno, String ifsc, String branch, String bank) 
	{
		super();
		this.acno = acno;
		this.ifsc = ifsc;
		this.branch = branch;
		this.bank = bank;
	}
	public Account() 
	{
		super();
	}
	public Long getAcno() {
		return acno;
	}
	public void setAcno(Long acno) {
		this.acno = acno;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
}
