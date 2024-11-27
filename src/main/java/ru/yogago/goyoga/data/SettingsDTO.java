package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class SettingsDTO {
    Long id = 0L;
    String language = "";
    Float proportionately = 1F;
    int addTime = 0;
    boolean speakAsanaName = true;
    Long timeOfFiltered;
}
