package ru.yogago.goyoga.conditionals;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class FirebaseConditionImpl implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String enabled = context.getEnvironment().getProperty("ru.yogago.firebase.enabled");
		return Boolean.parseBoolean(enabled);
	}
}
