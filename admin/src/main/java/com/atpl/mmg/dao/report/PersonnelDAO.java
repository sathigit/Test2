package com.atpl.mmg.dao.report;

import java.util.List;

import com.atpl.mmg.domain.report.PersonnelDomain;


public interface PersonnelDAO {

	public String addPersonnel(PersonnelDomain personnelDomain) throws Exception;

	public String addPersonnelSource(PersonnelDomain personnelDomain) throws Exception;

	public PersonnelDomain getPersonnelSource(int id) throws Exception;
	
	public String updatePersonnelSource(PersonnelDomain id) throws Exception;
	
	public String deletePersonnelSource(int id)throws Exception;
	
	public List<PersonnelDomain> getPersonnelSource() throws Exception;
}
