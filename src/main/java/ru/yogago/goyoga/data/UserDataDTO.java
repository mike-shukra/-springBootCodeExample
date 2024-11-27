package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class UserDataDTO {
    private Long id;
    private String email;
    private String first_name;
    private int now = 1;
    private int allTime = 0;
    private int allCount = 0;
    private int level;
    private boolean dangerknee;
    private boolean dangerloins;
    private boolean dangerneck;
    private boolean inverted;
    private boolean sideBySideSort;
    private String date;
}
