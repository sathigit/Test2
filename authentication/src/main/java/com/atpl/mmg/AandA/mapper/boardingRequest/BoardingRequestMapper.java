package com.atpl.mmg.AandA.mapper.boardingRequest;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.boardingRequest.BoardingRequestDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.boardingRequest.BoardingRequestModel;

@Component
public class BoardingRequestMapper extends AbstractModelMapper<BoardingRequestModel, BoardingRequestDomain> {

	@Override
	public Class<BoardingRequestModel> entityType() {
		// TODO Auto-generated method stub
		return BoardingRequestModel.class;
	}

	@Override
	public Class<BoardingRequestDomain> modelType() {
		// TODO Auto-generated method stub
		return BoardingRequestDomain.class;
	}

}
