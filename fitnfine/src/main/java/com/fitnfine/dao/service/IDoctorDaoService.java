package com.fitnfine.dao.service;

import java.util.Set;

import com.fitnfine.model.Doctor;

public interface IDoctorDaoService extends IBaseDaoService<Doctor, String>{

	Set<Doctor> searchByNamePart(String nameString);
}
