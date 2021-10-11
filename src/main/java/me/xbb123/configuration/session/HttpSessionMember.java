package me.xbb123.configuration.session;

import org.springframework.stereotype.Component;

import me.xbb123.example.session.SessionMember;

@Component
public class HttpSessionMember extends AbstractHttpSession<SessionMember> {

	@Override
	protected String name() {
		return "SESSION_MEMBER";
	}

}
