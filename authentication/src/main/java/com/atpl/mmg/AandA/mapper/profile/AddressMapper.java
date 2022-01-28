package com.atpl.mmg.AandA.mapper.profile;

import org.springframework.stereotype.Component;

import com.atpl.mmg.AandA.domain.profile.AddressDomain;
import com.atpl.mmg.AandA.mapper.AbstractModelMapper;
import com.atpl.mmg.AandA.model.profile.Address;

@Component
public class AddressMapper extends AbstractModelMapper<Address, AddressDomain> {

	@Override
	public Class<Address> entityType() {
		// TODO Auto-generated method stub
		return Address.class;
	}

	@Override
	public Class<AddressDomain> modelType() {
		// TODO Auto-generated method stub
		return AddressDomain.class;
	}

}
