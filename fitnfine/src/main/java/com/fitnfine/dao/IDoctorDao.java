package com.fitnfine.dao;

import java.util.Set;

import com.fitnfine.model.Doctor;

public interface IDoctorDao extends IBaseDao<Doctor, String> {
	
	Set<Doctor> searchByNamePart(String nameString);

}
