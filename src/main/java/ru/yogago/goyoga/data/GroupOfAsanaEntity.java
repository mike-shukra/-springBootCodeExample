package ru.yogago.goyoga.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.yogago.goyoga.data.enumiration.InGroup;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_group_of")
@Schema(description = "Группа")
public class GroupOfAsanaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    private InGroup inGroup;
}