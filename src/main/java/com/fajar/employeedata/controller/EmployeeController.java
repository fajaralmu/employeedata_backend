package com.fajar.employeedata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fajar.employeedata.dto.EmployeeDTO;
import com.fajar.employeedata.dto.TableRequest;
import com.fajar.employeedata.entities.Employee;
import com.fajar.employeedata.entities.Position;
import com.fajar.employeedata.service.EmployeeService;
import com.fajar.employeedata.service.PositionService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private PositionService positionService;
	
	@PostMapping(value = "/index", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<Employee> index(@RequestBody TableRequest tableRequest, HttpServletRequest httpServletRequest) {
		return employeeService.getListForPagination(tableRequest, httpServletRequest);
	}
	@GetMapping(value = {"/addEdit/{id}", "/addEdit"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeDTO addEdit(@PathVariable(name="id", required = false) Integer id) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setPositionList(positionService.getList());
		if (null != id) {
			employeeDTO.setEmployee(employeeService.getById(id));
		}
		return employeeDTO;
	}
	@PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee insert(@RequestBody Employee employee) {
		return employeeService.insert(employee);
	}
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee update(@RequestBody Employee employee) {
		return employeeService.update(employee);
	}
	@PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee delete(@RequestBody Employee employee) {
		return employeeService.delete(employee);
	}
	
	@PostMapping(value = "/insertposition", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Position insertposition(@RequestBody Position position) {
		return positionService.insert(position);
	}

}
