package com.atpl.mmg.AandA.mapper.boardingRequest;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.boardingRequest.EnquiryReasonDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.boardingRequest.EnquiryReasonModel;

@Component
public class EnquiryReasonMapper extends AbstractModelMapper<EnquiryReasonModel, EnquiryReasonDomain> {

	@Override
	public Class<EnquiryReasonModel> entityType() {
		// TODO Auto-generated method stub
		return EnquiryReasonModel.class;
	}

	@Override
	public Class<EnquiryReasonDomain> modelType() {
		// TODO Auto-generated method stub
		return EnquiryReasonDomain.class;
	}

}
