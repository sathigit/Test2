package com.atpl.mmg.AandA.dao.session;

import java.util.List;

import com.atpl.mmg.AandA.domain.session.SessionDomain;


public interface SessionDAO {
	

	public SessionDomain saveSession(SessionDomain session) throws Exception;

	public String updateSession(SessionDomain session) throws Exception;
	
	public String updateRecentSession(int userId) throws Exception;

	public SessionDomain getSession(String sessionId) throws Exception;
	
	public SessionDomain getSessionByProfileId(String userId,int roleId) throws Exception;
	
	public SessionDomain getLastAccessTime(String id) throws Exception;
	
	public List<SessionDomain> getLastAccessTime() throws Exception;
	
	public SessionDomain getSessionByProfileIdAndRoleId(String profileId, boolean isActive, Integer roleId) throws Exception;
	
}