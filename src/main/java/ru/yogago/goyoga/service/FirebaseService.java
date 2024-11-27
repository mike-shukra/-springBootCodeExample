package ru.yogago.goyoga.service;


import ru.yogago.goyoga.config.firebase.FirebaseTokenHolder;

public interface FirebaseService {

	FirebaseTokenHolder parseToken(String idToken);

}
