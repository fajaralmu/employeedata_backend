package com.fajar.employeedata.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fajar.employeedata.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="T2_EMPLOYEE")
@Data
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5683082420744242943L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="NAME", nullable = false, length = 100)
	private String name;
	@ManyToOne
	@JoinColumn(name = "POSITION_ID", nullable = false)
	private Position position;
	@Column(name="BIRTH_DATE", nullable = false)
	@JsonFormat(pattern = "YYYY-MM-DD")
	private Date birthDate;
	@Column(name="ID_NUMBER", unique = true, nullable = false)
	private long idNumber;
//	@JsonIgnore
	@Column(name="GENDER", nullable = false)
	private int gender;
	@JsonIgnore
	@Column(name="IS_DELETE",nullable = false)
	private int isDelete;
	
	public String getGenderString() {
		return gender == 1?"Pria":"Wanita";
	}
	public String getBirthDateString() {
		return DateUtil.getDateString(birthDate);
	}
	
	@JsonIgnore
	public boolean hasBeenDeleted() {
		return isDelete == 1;
	}
	public void setDeleted() {
		setIsDelete(1);
	}

}
