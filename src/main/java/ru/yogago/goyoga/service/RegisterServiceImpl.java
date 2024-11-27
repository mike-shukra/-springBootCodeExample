package ru.yogago.goyoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yogago.goyoga.config.firebase.FirebaseTokenHolder;
import ru.yogago.goyoga.data.ClientEntity;
import ru.yogago.goyoga.data.RegisterUserInit;
import ru.yogago.goyoga.data.UserEntity;
import ru.yogago.goyoga.util.StringUtil;

import javax.transaction.Transactional;
import java.util.logging.Logger;

@Service
public class RegisterServiceImpl implements RegisterService {

	private static final Logger logger = Logger.getLogger("RegisterServiceImpl");
	private final FirebaseService firebaseService;
	private final UserService userService;
	private final ClientService clientService;

	@Autowired
	public RegisterServiceImpl(FirebaseService firebaseService, @Qualifier(value = "userService") UserService userService, ClientService clientService) {
		this.firebaseService = firebaseService;
		this.userService = userService;
		this.clientService = clientService;
	}

	@Transactional
	@Override
	public boolean registerUser(String firebaseToken) {
		if (StringUtil.isBlank(firebaseToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
		logger.info(tokenHolder.toString());
		UserEntity userEntity = userService.registerUser(new RegisterUserInit(tokenHolder.getUid()));
		return (userEntity != null);
	}

	@Override
	public boolean isExistsUser(String firebaseToken) throws IllegalArgumentException {
		if (StringUtil.isBlank(firebaseToken)) {
			throw new IllegalArgumentException("FirebaseTokenBlank");
		}
		FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
		logger.info(tokenHolder.toString());
		String username = tokenHolder.getUid();
		return userService.loadUserByUsername(username) != null;
	}
}
