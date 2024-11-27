package ru.yogago.goyoga.data;

import lombok.Data;
import ru.yogago.goyoga.data.enumiration.Level;

@Data
public class ParametersDTO {
    private int now = 1;
    private int allTime = 0;
    private int allCount = 0;
    private Long timeOfFiltered;
    private Level level = Level.NOT_SPECIFIED;
    private Float proportionally = 1F;
    private int addTime = 0;
    private boolean isDangerKnee;
    private boolean isDangerLoins;
    private boolean isDangerNeck;
    private boolean isInverted;
    private boolean isSideBySideSort;

    public ParametersDTO() {
    }

    public ParametersDTO(ClientEntity clientEntity) {
        this.now = clientEntity.getNow();
        this.allTime = clientEntity.getAllTime();
        this.allCount = clientEntity.getAllCount();
        this.level = clientEntity.getLevel();
        this.proportionally = clientEntity.getProportionally();
        this.addTime = clientEntity.getAddTime();
        this.isDangerKnee = clientEntity.isDangerKnee();
        this.isDangerLoins = clientEntity.isDangerLoins();
        this.isDangerNeck = clientEntity.isDangerNeck();
        this.isInverted = clientEntity.isInverted();
        this.timeOfFiltered = clientEntity.getTimeOfFiltered();
        this.isSideBySideSort = clientEntity.isSideBySideSort();
    }
}
