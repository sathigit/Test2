package com.atpl.mmg.AandA.dao.profile;

import com.atpl.mmg.AandA.domain.profile.ChannelPartnerDomain;

public interface ChannelPartnerDAO {
	ChannelPartnerDomain save(ChannelPartnerDomain channelPartnerDomain) throws Exception;
	String update(ChannelPartnerDomain channelPartnerDomain) throws Exception;
	ChannelPartnerDomain getChannelPartnerByProfileId(String profileId) throws Exception;
}

