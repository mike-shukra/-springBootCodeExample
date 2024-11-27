package ru.yogago.goyoga.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import ru.yogago.goyoga.config.firebase.FirebaseTokenHolder;
import ru.yogago.goyoga.exception.FirebaseTokenInvalidException;
import ru.yogago.goyoga.util.StringUtil;


public class FirebaseParser {
    public FirebaseTokenHolder parseToken(String idToken) {
        if (StringUtil.isBlank(idToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        try {
            FirebaseToken authTask = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return new FirebaseTokenHolder(authTask);
        } catch (Exception e) {
            throw new FirebaseTokenInvalidException(e.getMessage());
        }
    }
}
