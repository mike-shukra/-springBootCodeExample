package ru.yogago.goyoga.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yogago.goyoga.exception.FirebaseTokenInvalidException;

import java.io.IOException;
import java.io.InputStream;

@RestController
@Tag(name = "image controller")
@RequestMapping("/api/image/")
public class ImageController {

    @GetMapping(value = "get/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(
            summary = "Получить изображение",
            description = "Load the JPG image from the classpath or any other source",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> getJpgImage(Authentication authentication, @PathVariable String imageName) {
        try {
            ClassPathResource resource = new ClassPathResource("asana/" + imageName);
            try (InputStream inputStream = resource.getInputStream()) {
                byte[] imageBytes = new byte[inputStream.available()];
                inputStream.read(imageBytes);
                return ResponseEntity.ok().body(imageBytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
}
