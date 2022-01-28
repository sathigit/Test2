package com.atpl.mmg.service.report;

import java.util.List;

import com.atpl.mmg.model.report.PersonnelModel;

public interface PersonnelService {

	public String addPersonnel(PersonnelModel personnelModel) throws Exception;

	public String addPersonnelSource(PersonnelModel personnelModel) throws Exception;

	public PersonnelModel getPersonnelSource(int id) throws Exception;
	
	public String updatePersonnelSource(PersonnelModel id) throws Exception;

	public String deletePersonnelSource(int id)throws Exception;
	
	public List<PersonnelModel> getPersonnelSource() throws Exception;
}
