package com.fitnfine.dao.service;

import java.util.Set;

import com.fitnfine.model.Medicine;

public interface IMedicineDaoService extends IBaseDaoService<Medicine, String> {

	Set<Medicine> searchByNamePart(String nameString);
}
