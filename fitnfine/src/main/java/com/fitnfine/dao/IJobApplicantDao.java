package com.fitnfine.dao;

import java.util.Set;

import com.fitnfine.model.JobApplicant;

public interface IJobApplicantDao extends IBaseDao<JobApplicant, String> {
	
	Set<JobApplicant> searchByNamePart(String nameString);

}
