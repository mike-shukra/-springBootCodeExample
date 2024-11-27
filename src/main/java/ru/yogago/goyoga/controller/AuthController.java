package ru.yogago.goyoga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yogago.goyoga.data.BooleanDTO;
import ru.yogago.goyoga.service.RegisterService;

import java.util.logging.Logger;

@RestController
@Tag(name = "1. signup-api-controller")
@RequestMapping("/api/public")
public class AuthController {

	private static final Logger logger = Logger.getLogger("AuthController");

	private final RegisterService registerService;

	@Autowired
	public AuthController(RegisterService registerService) {
		this.registerService = registerService;
	}

	@RequestMapping(value = "/firebase-signup", method = RequestMethod.POST)
	@Operation(
			summary = "Зарегистрировать нового пользователя",
			description = "Описание"
	)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> signUp(@RequestHeader String firebaseToken) {
		logger.info(firebaseToken);
		try {
			BooleanDTO is = new BooleanDTO();
			is.setValue(registerService.registerUser(firebaseToken));
			return ResponseEntity.ok().body(is);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@RequestMapping(value = "/is-exists", method = RequestMethod.POST)
	@Operation(
			summary = "Проверка, зарегистрирован ли пользователь.",
			description = "Описание"
	)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> isExists(@RequestHeader String firebaseToken) {
		logger.info(firebaseToken);
		try {
			BooleanDTO isExistsUser = new BooleanDTO();
			isExistsUser.setValue(registerService.isExistsUser(firebaseToken));
			return ResponseEntity.ok().body(isExistsUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
