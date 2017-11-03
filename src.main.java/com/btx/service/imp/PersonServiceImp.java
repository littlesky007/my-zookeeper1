package com.btx.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btx.dao.PersonDao;
import com.btx.domain.Person;
import com.btx.service.IPersonServiceInter;

@Service
public class PersonServiceImp implements IPersonServiceInter{
	
	@Autowired
	private PersonDao personDao;
	
	
	
	public List<Person> getAll() {
		
		return personDao.getAllPerson();
	}

}
