package com.fajar.employeedata.dao;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fajar.employeedata.dto.TableRequest;
import com.fajar.employeedata.entities.Employee;

@Service
public class EmployeeDAO {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee getById(Integer id) {
		try (SessionWrapper sessionWrapper = new SessionWrapper(sessionFactory)) {
			Criteria criteria = sessionWrapper.createCriteria(Employee.class);
			criteria.setMaxResults(1);
			criteria.add(Restrictions.eq("id", id));
			criteria.add(Restrictions.eq("isDelete", 0));
			if (criteria.list().size() > 0) {
				return (Employee) criteria.list().get(0);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public Employee insert(Employee employee) {

		Transaction tx = null;
		try (SessionWrapper sessionWrapper = new SessionWrapper(sessionFactory)) {
			tx = sessionWrapper.getSession().beginTransaction();
			Serializable id = sessionWrapper.getSession().save(employee);
			employee.setId((int) id);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
		return employee;
	}

	public Employee update(Employee employee) {
		employee.setIsDelete(0);
		return employeeRepository.save(employee);
	}

	public Employee delete(Employee employee) {

		Transaction tx = null;
		try (SessionWrapper sessionWrapper = new SessionWrapper(sessionFactory)) {
			Employee existing = (Employee) sessionWrapper.getSession().get(Employee.class, employee.getId());
			if (null == existing || existing.hasBeenDeleted()) {
				throw new RuntimeException("Record not exist");
			}
			tx = sessionWrapper.getSession().beginTransaction();
			existing.setDeleted();
			sessionWrapper.getSession().persist(existing);
			tx.commit();

			return existing;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}

	public Page<Employee> getListForPagination(TableRequest tableRequest, HttpServletRequest httpRequest) {
		boolean validated = validateOrderBy(tableRequest.getOrderBy());
		if (!validated || null == tableRequest.getOrderBy()) {
			tableRequest.setOrderBy("id");
		}
		if ("position".equals(tableRequest.getOrderBy())) {
			tableRequest.setOrderBy("position.name");
		}
		Page<Employee> result = employeeRepository.findAllNotDeleted(tableRequest.getPageRequest());
		return result;

	}

	private boolean validateOrderBy(String orderBy) {

		try {
			Field field = Employee.class.getDeclaredField(orderBy);
			if (null == field) {
				return false;
			}
			return true;
		} catch (NoSuchFieldException | SecurityException e) { 
			e.printStackTrace(); 
		}
		return false;

	}

}
