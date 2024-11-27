package ru.yogago.goyoga.service;

public interface RegisterService {

	boolean registerUser(String firebaseToken);

	boolean isExistsUser(String firebaseToken);

}
