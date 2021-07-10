package com.fajar.employeedata.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fajar.employeedata.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee	, Integer>{

	@Query("select e from Employee e where isDelete = 0 and (lower(e.name)"
			+ " like %?1% or lower(e.email) like %?1%) ")
	Page<Employee> findAllNotDeleted(String searchLowercase, Pageable pageable);

	Employee findTop1ByIdNumber(long idNumber);
 
	 

}
