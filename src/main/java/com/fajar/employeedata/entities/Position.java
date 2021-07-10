package com.fajar.employeedata.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="tb_master_user_role")
@Data
public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3580710317231046773L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="CODE", unique = true, nullable = false, length = 50)
	private String code;
	@Column(name="NAME", nullable = false, length = 100)
	private String name;
	
	@JsonIgnore
	@Column(name="IS_DELETE", nullable = false)
	private int isDelete;

}
