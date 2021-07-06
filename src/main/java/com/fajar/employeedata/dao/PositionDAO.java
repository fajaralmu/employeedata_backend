package com.fajar.employeedata.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fajar.employeedata.entities.Position;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionDAO {

	@Autowired
	private SessionFactory sessionFactory;
	public List<Position> getList() {
		try (SessionWrapper wrapper = new SessionWrapper(sessionFactory.openSession())){

			Criteria criteria = wrapper.createCriteria(Position.class);
			criteria.add(Restrictions.eq("isDelete", 0));
			return criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			log.error("ERROR GET LIST: {}", e.getMessage());
			return new ArrayList<Position>();
		} 
		
	}

}
