package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class ActionStateDTO {
    Long id = 0L;
    int currentId = 0;
    boolean isPlay = false;
}
