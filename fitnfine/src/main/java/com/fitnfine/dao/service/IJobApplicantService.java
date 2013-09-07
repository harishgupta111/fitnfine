package com.fitnfine.dao.service;

import java.util.Set;

import com.fitnfine.model.JobApplicant;

public interface IJobApplicantService extends
		IBaseDaoService<JobApplicant, String> {

	Set<JobApplicant> searchByNamePart(String nameString);

}
