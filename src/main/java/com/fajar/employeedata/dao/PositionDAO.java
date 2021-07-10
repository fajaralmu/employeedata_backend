package com.fajar.employeedata.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
		try (SessionWrapper wrapper = new SessionWrapper(sessionFactory)){

			Criteria criteria = wrapper.createCriteria(Position.class);
			criteria.add(Restrictions.eq("isDelete", 0));
			return criteria.list();
		} catch(Exception e) {
			e.printStackTrace();
			log.error("ERROR GET LIST: {}", e.getMessage());
			return new ArrayList<Position>();
		} 
		
	}
	public Position insert(Position position) {
		Transaction tx = null;
		try (SessionWrapper sessionWrapper = new SessionWrapper(sessionFactory)) {
			tx = sessionWrapper.getSession().beginTransaction();
			Serializable id = sessionWrapper.getSession().save(position);
			position.setId((int) id);
			tx.commit();
			
			return position;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}

}
