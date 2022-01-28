package com.atpl.mmg.AandA.service.session;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atpl.mmg.AandA.config.MMGProperties;
import com.atpl.mmg.AandA.dao.session.SessionDAO;
import com.atpl.mmg.AandA.domain.session.SessionDomain;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.mapper.session.SessionMapper;
import com.atpl.mmg.AandA.model.session.SessionModel;
import com.atpl.mmg.AandA.utils.IDGeneration;

@Service("SessionService")
public class SessionServiceImpl implements SessionService {
	@Autowired
	SessionDAO sessionDAO;

	@Autowired
	SessionMapper sessionMapper;

	@Autowired
	MMGProperties mmgProperties;

	@Autowired
	IDGeneration idGeneration;


	@Override
	public SessionModel saveSession(SessionModel session) throws Exception {
		SessionDomain sessionDomain = new SessionDomain();
		BeanUtils.copyProperties(session, sessionDomain);
		BigInteger sessionId = idGeneration.RandomNumberUserSession();
		sessionDomain.setSessionId(sessionId);
		sessionDAO.saveSession(sessionDomain);
		BeanUtils.copyProperties(sessionDomain, session);
		return session;
	}

	@Override
	public String updateSession(SessionModel session) throws Exception {
		SessionDomain sessionDomain = new SessionDomain();
		BeanUtils.copyProperties(session, sessionDomain);
		return sessionDAO.updateSession(sessionDomain);

	}

	@Override
	public SessionModel getSession(String sessionId) throws Exception {
		SessionDomain sessionDomain = sessionDAO.getSession(sessionId);
		SessionModel sessionModel = new SessionModel();
		if (sessionDomain == null)
			throw new NOT_FOUND("Session not found");
		BeanUtils.copyProperties(sessionDomain, sessionModel);
		return sessionModel;
	}

	/**
	 * Author:Vidya S K Created Date: 29/1/2020 Modified Date: Description: Status
	 * :To take Recent customer Last accessDate based Id.
	 * 
	 */

	@Override
	public SessionModel getLastAccessTime(String id) throws Exception {
		SessionDomain sessionDomain = sessionDAO.getLastAccessTime(id);
		SessionModel sessionModel = new SessionModel();
		if (sessionDomain == null)
			throw new NOT_FOUND("Session not found");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(sessionDomain.getLastAccessTime());
		sessionDomain.setAccessTime(strDate);
		BeanUtils.copyProperties(sessionDomain, sessionModel);
		return sessionModel;
	}

	/**
	 * Author:Vidya S K Created Date: 29/1/2020 Modified Date: Description: Status
	 * :To List customer Last accessDate.
	 * 
	 */
	@Override
	public List<SessionModel> getLastAccessTime() throws Exception {
		List<SessionDomain> sessionDomainLast = new ArrayList<SessionDomain>();
		List<SessionDomain> sessionDomain = sessionDAO.getLastAccessTime();
		for (SessionDomain sessionDomains : sessionDomain) {
			SessionModel last = getLastAccessTime(sessionDomains.getUserId());
			String userId = last.getUserId();
			BigInteger mobileNumber = last.getMobileNumber();
			String accessTime = last.getAccessTime();
			int cityId = last.getCityId();
			String firstName = last.getFirstName();
			ArrayList<SessionDomain> arraylist = new ArrayList<SessionDomain>();
			arraylist.add(new SessionDomain(userId, mobileNumber, accessTime, cityId, firstName));
			sessionDomainLast.addAll(arraylist);
		}
		return sessionMapper.entityList(sessionDomainLast);

	}
}
