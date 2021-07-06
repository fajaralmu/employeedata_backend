package com.fajar.employeedata.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class SessionWrapper implements AutoCloseable {

	final Session session;
	public SessionWrapper(Session s) { this.session = s; }
	public SessionWrapper(SessionFactory factory) { this.session = factory.openSession(); }
	public Session getSession() {
		return session;
	}
	public Criteria createCriteria(Class<?> _class) {
		return session.createCriteria(_class);
	}
	@Override
	public void close() throws Exception {
		if(null != session) { session.close();}
	}

}
