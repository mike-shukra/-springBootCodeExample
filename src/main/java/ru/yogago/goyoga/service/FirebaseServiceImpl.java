package ru.yogago.goyoga.service;

import org.springframework.stereotype.Service;
import ru.yogago.goyoga.conditionals.FirebaseCondition;
import ru.yogago.goyoga.config.firebase.FirebaseTokenHolder;

@Service
@FirebaseCondition
public class FirebaseServiceImpl implements FirebaseService {
	@Override
	public FirebaseTokenHolder parseToken(String firebaseToken) {
		return new FirebaseParser().parseToken(firebaseToken);
	}
}
