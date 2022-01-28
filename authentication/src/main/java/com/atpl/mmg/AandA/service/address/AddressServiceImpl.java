package com.atpl.mmg.AandA.service.address;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.common.JsonUtil;
import com.atpl.mmg.AandA.constant.AddressType;
import com.atpl.mmg.AandA.dao.profile.AddressDAO;
import com.atpl.mmg.AandA.domain.profile.AddressDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.ADDRESS_ALREADY_EXIST;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.log.EventsLogUtil;
import com.atpl.mmg.AandA.log.Loggly;
import com.atpl.mmg.AandA.log.ServiceTypes;
import com.atpl.mmg.AandA.log.SeverityTypes;
import com.atpl.mmg.AandA.mapper.profile.AddressMapper;
import com.atpl.mmg.AandA.model.profile.Address;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.atpl.mmg.AandA.utils.CommonUtils;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressDAO addressDAO;

	@Autowired
	AddressMapper addressMapper;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public String save(Address addressModel) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADDRESS.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Address Request: " + JsonUtil.toJsonString(addressModel)));
		validateAddress(addressModel);
		List<AddressDomain> addressDomain = new ArrayList<AddressDomain>();
		addressDomain = addressDAO.getAddressByTypeAndProfileIdWithRoleId(addressModel.getType(),
				addressModel.getProfileId(), addressModel.getRoleId());
		if (addressModel.getType().equalsIgnoreCase(AddressType.HOME.name())) {
			if(addressDomain.isEmpty())
				addressModel.setIsActive(true);
			saveAddr(addressModel);
		} 
		else
		if (addressModel.getType().equalsIgnoreCase(AddressType.OFFICE.name()) && addressDomain.isEmpty()) {
			saveAddr(addressModel);
		} else
			throw new ADDRESS_ALREADY_EXIST("Address already exists for the " + AddressType.OFFICE.name());
		return "Saved successfully";
	}

	private void saveAddr(Address addressModel) throws Exception {
		AddressDomain address = new AddressDomain();
		BeanUtils.copyProperties(addressModel, address);
		AddressDomain addr = addressDAO.getAddressByvalidateAddress(address);
		if (null == addr) {
			address.setUuid(CommonUtils.generateRandomId());
			address = addressDAO.save(address);
		}
	}

	@Override
	public Address getAddressesByUuid(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADDRESS.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "getAddressesByUuid Request: " + JsonUtil.toJsonString(uuid)));
		AddressDomain addressDomain = addressDAO.getAddressByUuid(uuid);
		if (null == addressDomain)
			throw new NOT_FOUND("Address not found");
		Address address = new Address();
		BeanUtils.copyProperties(addressDomain, address);
		return address;
	}

	@Override
	public List<Address> getAddresses(String profileId, Map<String, String> reqParam) throws Exception {
		int roleId = 0;
		boolean isActive = false;
		List<AddressDomain> addressDomainList = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("roleId"))
				roleId = Integer.parseInt(reqParam.get("roleId"));
			if (reqParam.containsKey("isActive"))
				isActive = Boolean.parseBoolean(reqParam.get("isActive"));
		}
		addressDomainList = profileUtil.getAddressDetails(profileId, roleId, isActive);
		return addressMapper.entityList(addressDomainList);
	}

	@Override
	public String update(Address address) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADDRESS.name(),
				SeverityTypes.INFORMATIONAL.ordinal(), "Address Request: " + JsonUtil.toJsonString(address)));
		validateAddress(address);
		validateAddressId(address.getUuid());
		AddressDomain addressDomain = new AddressDomain();
		BeanUtils.copyProperties(address, addressDomain);
		return addressDAO.update(addressDomain);
	}

	private void validateAddress(Address address) {
		if (null == address.getProfileId() || address.getProfileId().isEmpty())
			throw new NOT_FOUND("Please mention the profileId");
		if (null == address.getType())
			throw new NOT_FOUND("Please mention the type");
		if (null == address.getAddress1())
			throw new NOT_FOUND("Please mention the address");
		if (0 >= address.getCityId())
			throw new NOT_FOUND("Please mention the city");
		if (0 >= address.getStateId())
			throw new NOT_FOUND("Please mention the state");
		if (0 >= address.getCountryId())
			throw new NOT_FOUND("Please mention the country");

	}

	private void validateAddressId(String uuid) throws Exception {
		if (null == uuid || uuid.isEmpty())
			throw new NOT_FOUND("Please mention the uuid");
		AddressDomain addressDomain = addressDAO.getAddressByUuid(uuid);
		if (null == addressDomain)
			throw new NOT_FOUND("Address not found");
	}

	@Override
	public String activateAddress(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADDRESS.name(), SeverityTypes.DEBUG.ordinal(),
				" activateAddress in AddressServiceImpl ") + JsonUtil.toJsonString(uuid));
		if (CommonUtils.isNullCheck(uuid))
			throw new NOT_FOUND("Please mention the uuid");
		AddressDomain addressDomain = addressDAO.getAddressByUuid(uuid);
		if (null == addressDomain)
			throw new NOT_FOUND("Address not found");
		List<AddressDomain> address = addressDAO.getAddressByTypeAndProfileIdWithRoleId(addressDomain.getType(),
				addressDomain.getProfileId(), addressDomain.getRoleId());
		if (!address.isEmpty()) {
			for (AddressDomain addr : address) {
				addressDAO.activateDeactivateAddress(addr.getUuid(), false);
			}
		}
		return addressDAO.activateDeactivateAddress(uuid, true);
	}

	@Override
	public String deActivateAddress(String uuid) throws Exception {
		Loggly.sendLogglyEvent(EventsLogUtil.prepareLogEvent(ServiceTypes.ADDRESS.name(), SeverityTypes.DEBUG.ordinal(),
				" deActivateAddress in AddressServiceImpl ") + JsonUtil.toJsonString(uuid));
		if (CommonUtils.isNullCheck(uuid))
			throw new NOT_FOUND("Please mention the uuid");
		AddressDomain addressDomain = addressDAO.getAddressByUuid(uuid);
		if (null == addressDomain)
			throw new NOT_FOUND("Address not found");
		return addressDAO.activateDeactivateAddress(uuid, false);
	}
}
