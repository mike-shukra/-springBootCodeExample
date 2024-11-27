package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String username;
    private String displayName;
    private String email;
    private String phone;
    private String apiKey;
    private String secretKey;

    private int now = 1;
    private int allTime = 0;
    private int allCount = 0;
    private String level = "NOT_SPECIFIED";
    private boolean isDangerKnee;
    private boolean isDangerLoins;
    private boolean isDangerNeck;
    private boolean isInverted;


    public ClientDTO() {
    }

    public ClientDTO(ClientEntity clientEntity) {
        this.id = clientEntity.getId();
        this.username = clientEntity.getUsername();
        this.displayName = clientEntity.getDisplayName();
        this.apiKey = "";
        this.secretKey = "";
        this.now = clientEntity.getNow();
        this.allTime = clientEntity.getAllTime();
        this.allCount = clientEntity.getAllCount();
        this.level = clientEntity.getLevel().toString();
        this.isDangerKnee = clientEntity.isDangerKnee();
        this.isDangerLoins = clientEntity.isDangerLoins();
        this.isDangerNeck = clientEntity.isDangerNeck();
        this.isInverted = clientEntity.isInverted();
    }
}
