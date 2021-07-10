package com.fajar.employeedata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fajar.employeedata.dao.PositionDAO;
import com.fajar.employeedata.entities.Employee;
import com.fajar.employeedata.entities.Position;

@Service
public class PositionService {

	@Autowired
	private PositionDAO positionDAO;
	
	public PositionDAO getDao(){
		return positionDAO;
	}
	
	public List<Position> getList(){
		return positionDAO.getList();
	}

	public Position insert(Position position) {
		return positionDAO.insert(position);
	}
}
