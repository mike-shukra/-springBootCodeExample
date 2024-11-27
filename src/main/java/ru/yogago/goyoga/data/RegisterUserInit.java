package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class RegisterUserInit {
	private final String userName;

	public RegisterUserInit(String userName) {
		super();
		this.userName = userName;
	}

}
