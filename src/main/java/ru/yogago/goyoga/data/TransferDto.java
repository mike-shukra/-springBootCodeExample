package ru.yogago.goyoga.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransferDto {
    private String text;
    private List<String> texts = new ArrayList<>();
    private boolean b;

    public void addToText(String t) {
        texts.add(t);
    }
}
