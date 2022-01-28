package com.atpl.mmg.AandA.service.session;

import java.util.List;

import com.atpl.mmg.AandA.model.session.SessionModel;

public interface SessionService {
	

	public SessionModel saveSession(SessionModel session) throws Exception;

	public String updateSession(SessionModel session) throws Exception;

	public SessionModel getSession(String sessionId) throws Exception;
	
	public List<SessionModel> getLastAccessTime() throws Exception;
	
	public SessionModel getLastAccessTime(String id) throws Exception;
}
