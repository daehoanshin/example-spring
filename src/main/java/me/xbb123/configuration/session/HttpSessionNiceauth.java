package me.xbb123.configuration.session;

import org.springframework.stereotype.Component;

import me.xbb123.example.session.SessionNiceauth;

@Component
public class HttpSessionNiceauth extends AbstractHttpSession<SessionNiceauth> {

	@Override
	protected String name() {
		return "SESSION_NICEAUTH";
	}

}
