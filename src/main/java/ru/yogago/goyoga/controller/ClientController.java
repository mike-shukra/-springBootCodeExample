package ru.yogago.goyoga.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.yogago.goyoga.data.*;
import ru.yogago.goyoga.service.ClientService;
import ru.yogago.goyoga.service.RegisterService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Tag(name = "client controller")
@RequestMapping(value = "/api")
public class ClientController {
    private final ClientService clientService;
    private final RegisterService registerService;
    private final AsanaService asanaService;
    private final Logger logger;


    @Autowired
    public ClientController(ClientService clientService, RegisterService registerService, AsanaService asanaService) {
        this.clientService = clientService;
        this.registerService = registerService;
        this.asanaService = asanaService;
        this.logger = LoggerFactory.getLogger(ClientController.class);
    }

    @GetMapping("/get-parameters")
    @Operation(
            summary = "Получить параметры",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> getParameters(Authentication authentication) {
        try {
            ClientEntity clientEntity = clientService.getClientByUsername(authentication.getName());
            return ResponseEntity.ok().body(new ParametersDTO(clientEntity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-parameters")
    @Operation(
            summary = "Обновить параметры",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> updateParameters(Authentication authentication, @RequestBody ParametersDTO parametersDTO) {
        try {
            ClientEntity clientEntity = clientService.getClientByUsername(authentication.getName());
            clientEntity.setParameters(parametersDTO);
            DataDTO dataDTO = new DataDTO(clientService.updateClient(clientEntity));
            return ResponseEntity.ok().body(dataDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/current")
    @Operation(
            summary = "Получить данные клиента",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> getCurrent(Authentication authentication) {
        try {
            ClientEntity clientEntity = clientService.getClientByUsername(authentication.getName());
            return ResponseEntity.ok().body(clientEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @Operation(
            summary = "Получить заново отфильтрованные данные",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> createN(Authentication authentication, @RequestBody ParametersDTO parametersDTO) {
        try {
            ClientEntity clientEntity = clientService.getClientByUsername(authentication.getName());
            clientEntity.setParameters(parametersDTO);
            clientService.updateClient(clientEntity);

            List<AsanaEntity> asanaEntityList = asanaService.filterAsanas(authentication.getName());
            clientEntity = asanaService.saveAsanasChoice(authentication.getName(), asanaEntityList);

            DataDTO dataDTO = new DataDTO(clientEntity);

            return ResponseEntity.ok().body(dataDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/data")
    @Operation(
            summary = "Получить данные",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> getData(Authentication authentication, HttpServletRequest request) {
        try {
            String firebaseToken = request.getHeader("X-Authorization-Firebase");
            if (!registerService.isExistsUser(firebaseToken))
                registerService.registerUser(firebaseToken);
            ClientEntity clientEntity = clientService.getClientByUsername(authentication.getName());

            if (clientEntity.getAsanaEntityList().isEmpty()) {
                List<AsanaEntity> asanaEntityList = asanaService.filterAsanas(authentication.getName());
                clientEntity = asanaService.saveAsanasChoice(authentication.getName(), asanaEntityList);
            }
            DataDTO dataDTO = new DataDTO(clientEntity);
            logger.info("DataDTO asana list size: " + dataDTO.getAsanas().size());
            return ResponseEntity.ok().body(dataDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/update-display-name", method = RequestMethod.POST)
    @Operation(
            summary = "Обновить отображаемое имя",
            description = "Описание",
            security = { @SecurityRequirement(name = "bearer-key") }
    )
    public ResponseEntity<?> updateDisplayName(Authentication authentication, @RequestBody TransferDto name) {
        try {
            return ResponseEntity.ok().body(this.clientService.updateClientData(authentication.getName(), name.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
