package ru.yogago.goyoga.exception;

import org.springframework.security.authentication.BadCredentialsException;

import java.io.Serial;

public class FirebaseTokenInvalidException extends BadCredentialsException {

	public FirebaseTokenInvalidException(String msg) {
		super(msg);
	}

	@Serial
	private static final long serialVersionUID = 789949671713648425L;

}
