package ru.yogago.goyoga.data;

import lombok.Data;

@Data
public class AsanaSendDTO {
    Long id;
    String name;
    String eng;
    String description;
    String description_en;
    String photo;
    String symmetric;
    String side;
    long times;

    public AsanaSendDTO() {
    }

    public AsanaSendDTO(AsanaEntity asanaEntity) {
        this.id = asanaEntity.getId();
        this.name = asanaEntity.getAsanaNameRU();
        this.eng = asanaEntity.getAsanaNameENG();
        this.description = asanaEntity.getDescriptionRU();
        this.description_en = asanaEntity.getDescriptionENG();
        this.photo = asanaEntity.getPhoto();
        this.symmetric = "" + asanaEntity.isSymmetric();
        this.side = "symmetric";
        this.times = asanaEntity.getTimes();
    }

    private String getSideString(boolean b) {
        if (b) return "second";
        else return "first";
    }
}
