package ru.yogago.goyoga.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.yogago.goyoga.data.enumiration.Level;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "t_client")
@Schema(description = "Сущность клиента")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @NotEmpty
    @Size(min=2, max=30, message = "От 2 до 30 знаков")
    private String username;
    @Size(max=30, message = "до 30 знаков")
    private String displayName;
    private int now = 1;
    private int allTime;
    private int allCount;
    private String language = "";
    private Long timeOfFiltered;
    private Level level = Level.NOT_SPECIFIED;
    private Float proportionally = 1F;
    private int addTime = 0;
    private boolean isDangerKnee;
    private boolean isDangerLoins;
    private boolean isDangerNeck;
    private boolean isInverted;
    private boolean isSideBySideSort;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<AsanaEntity> asanaEntityList = new ArrayList<>();

    public ClientEntity(UserEntity user) {
        this.username = user.getUsername();
    }

    public ClientEntity() {
    }

    public void setParameters(ParametersDTO parametersDTO) {
        this.now = parametersDTO.getNow();
        this.allTime = parametersDTO.getAllTime();
        this.allCount = parametersDTO.getAllCount();
        this.level = parametersDTO.getLevel();
        this.proportionally = parametersDTO.getProportionally();
        this.addTime = parametersDTO.getAddTime();
        this.isDangerKnee = parametersDTO.isDangerKnee();
        this.isDangerLoins = parametersDTO.isDangerLoins();
        this.isDangerNeck = parametersDTO.isDangerNeck();
        this.isInverted = parametersDTO.isInverted();
        this.timeOfFiltered = parametersDTO.getTimeOfFiltered();
        this.isSideBySideSort = parametersDTO.isSideBySideSort();
    }

}
