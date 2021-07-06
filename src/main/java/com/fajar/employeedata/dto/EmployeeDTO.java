package com.fajar.employeedata.dto;

import java.io.Serializable;
import java.util.List;

import com.fajar.employeedata.entities.Employee;
import com.fajar.employeedata.entities.Position;

import lombok.Data;

@Data
public class EmployeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4995307456252714766L;
	
	private List<Position> positionList;
	private Employee employee;

}
