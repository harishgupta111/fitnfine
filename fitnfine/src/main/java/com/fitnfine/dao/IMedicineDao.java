package com.fitnfine.dao;

import java.util.Set;

import com.fitnfine.model.Medicine;

public interface IMedicineDao extends IBaseDao<Medicine, String> {
	
	Set<Medicine> searchByNamePart(String nameString);

}
