package com.atpl.mmg.service.report;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.report.PersonnelDAO;
import com.atpl.mmg.domain.report.PersonnelDomain;
import com.atpl.mmg.exception.GenericRes;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.mapper.report.PersonnelMapper;
import com.atpl.mmg.model.report.PersonnelModel;

@Service("personnelService")
@SuppressWarnings("rawtypes")
public class PersonnelServiceImpl implements PersonnelService, Constants {

	@Autowired
	PersonnelDAO personnelDAO;

	@Autowired
	PersonnelMapper personnelMapper;

	private static final Logger logger = LoggerFactory.getLogger(PersonnelServiceImpl.class);

	/**
	 * Author:pooja Modified Date: 25/2/2020 done by vidya
	 */

	@Override
	public String addPersonnel(PersonnelModel personnelModel) throws Exception {
		PersonnelDomain personnelDomain = new PersonnelDomain();
		BeanUtils.copyProperties(personnelModel, personnelDomain);
		return personnelDAO.addPersonnel(personnelDomain);

	}

	@Override
	public String  addPersonnelSource(PersonnelModel personnelModel) throws Exception {
		PersonnelDomain personnelDomain = new PersonnelDomain();
		BeanUtils.copyProperties(personnelModel, personnelDomain);
		return personnelDAO.addPersonnelSource(personnelDomain);
	}

	@Override
	public PersonnelModel getPersonnelSource(int id) throws Exception {
		PersonnelDomain personnelDomain = personnelDAO.getPersonnelSource(id);
		PersonnelModel personnelModel = new PersonnelModel();
		if (personnelDomain == null)
			throw new NOT_FOUND("PersonnelSource not found");
		BeanUtils.copyProperties(personnelDomain, personnelModel);
		return personnelModel;

	}

	@Override
	public String updatePersonnelSource(PersonnelModel personnelModel) throws Exception {
		PersonnelDomain personnelDomain = new PersonnelDomain();
		BeanUtils.copyProperties(personnelModel, personnelDomain);
		return  personnelDAO.updatePersonnelSource(personnelDomain);
	}

	@Override
	public String deletePersonnelSource(int id) throws Exception {
		PersonnelDomain personnelDomain = new PersonnelDomain();
		PersonnelModel personnelModel = new PersonnelModel();
		BeanUtils.copyProperties(personnelModel, personnelDomain);
		return  personnelDAO.deletePersonnelSource(id);

	}

	@Override
	public List<PersonnelModel> getPersonnelSource() throws Exception {
		List<PersonnelDomain> personnelDomain = personnelDAO.getPersonnelSource();
		return personnelMapper.entityList(personnelDomain);

	}

}
