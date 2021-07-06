package com.fajar.employeedata.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fajar.employeedata.dao.EmployeeDAO;
import com.fajar.employeedata.dto.EmployeeDTO;
import com.fajar.employeedata.dto.TableRequest;
import com.fajar.employeedata.entities.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	public EmployeeDAO getDAO() {
		return employeeDAO;
	}
	
	public Page<Employee> getListForPagination(TableRequest tableRequest, HttpServletRequest httpRequest) {
		return employeeDAO.getListForPagination(tableRequest, httpRequest);
	}

	public Employee getById(Integer id) {
		return employeeDAO.getById(id);
	}

	public Employee insert(Employee employee) {
		return employeeDAO.insert(employee);
	}

	public Employee update(Employee employee) {
		return employeeDAO.update(employee);
	}

	public Employee delete(Employee employee) {
		return employeeDAO.delete(employee);
	} 
}
