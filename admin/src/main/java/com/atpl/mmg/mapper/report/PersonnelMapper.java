package com.atpl.mmg.mapper.report;

import org.springframework.stereotype.Component;

import com.atpl.mmg.domain.report.PersonnelDomain;
import com.atpl.mmg.mapper.AbstractModelMapper;
import com.atpl.mmg.model.report.PersonnelModel;

@Component
public class PersonnelMapper extends AbstractModelMapper<PersonnelModel, PersonnelDomain> {

	@Override
	public Class<PersonnelModel> entityType() {
		// TODO Auto-generated method stub
		return PersonnelModel.class;
	}

	@Override
	public Class<PersonnelDomain> modelType() {
		// TODO Auto-generated method stub
		return PersonnelDomain.class;
	}

}
