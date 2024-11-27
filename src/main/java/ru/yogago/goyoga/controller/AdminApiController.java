package ru.yogago.goyoga.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.yogago.goyoga.data.*;
import ru.yogago.goyoga.service.ClientService;
import ru.yogago.goyoga.service.RegisterService;
import ru.yogago.goyoga.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin-api-controller")
public class AdminApiController {
    private final ClientService clientService;
    private final RegisterService registerService;
    private final AsanaService asanaService;
    private final UserService userService;
    private final Logger logger;

    @Autowired
    public AdminApiController(ClientService clientService, RegisterService registerService, AsanaService asanaService, UserService userService) {
        this.clientService = clientService;
        this.registerService = registerService;
        this.asanaService = asanaService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(AdminApiController.class);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> hello(Authentication authentication) {
        logger.info("authentication: " + authentication);
        try {
            return ResponseEntity.ok().body("hello");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = "/delete-user", method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteUserById(Authentication authentication, @RequestBody Long id) {
        try {
            this.userService.deleteUserById(id);
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = "/find-all-users", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> findAllUsers(Authentication authentication) {
        try {
            return ResponseEntity.ok().body(this.userService.findAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @RequestMapping(value = "/find-user-by-id", method = RequestMethod.POST, produces="application/json")
    @ResponseBody
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> findUserById(Authentication authentication, @RequestBody Long id) {
        try {
            return ResponseEntity.ok().body(this.userService.findUserById(id).get());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Operation(summary = "This method is used to get the asanas.", security = { @SecurityRequirement(name = "bearer-key") })
    @RequestMapping(value = "/asana-list", method = RequestMethod.GET)
    public ResponseEntity<?> getAsanaList(Authentication authentication) {
        try {
            return ResponseEntity.ok().body(asanaService.getAsanaList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @Operation(summary = "Перенос асан из старой базы", security = { @SecurityRequirement(name = "bearer-key") })
    @RequestMapping(value = "/transit-asanas", method = RequestMethod.GET)
    public ResponseEntity<?> getAsanaListTransit(Authentication authentication) {
        try {
            return ResponseEntity.ok().body(asanaService.transitOldBase());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "", security = { @SecurityRequirement(name = "bearer-key") })
    @RequestMapping(value = "/delete-all-asanas", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll(Authentication authentication) {
        try {
            asanaService.deleteAll();
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "", security = { @SecurityRequirement(name = "bearer-key") })
    @RequestMapping(value = "/client-asana-list", method = RequestMethod.GET)
    public ResponseEntity<?> getClientAsanaList(Authentication authentication) {
        try {
            List<AsanaEntity> asanaEntityList = asanaService.filterAsanas(authentication.getName());
            ClientEntity clientEntity = asanaService.saveAsanasChoice(authentication.getName(), asanaEntityList);
            return ResponseEntity.ok().body(clientEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/asana-post")
    @Operation(
            summary = "Add one",
            description = "One new asana add",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> postAsana(Authentication authentication, @RequestBody AsanaDTO asanaDTO) {
        try {
            AsanaEntity asanaEntity = new AsanaEntity(asanaDTO);

            return ResponseEntity.ok().body(asanaService.postAsana(asanaEntity));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
