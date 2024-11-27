package ru.yogago.goyoga.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
public class DataDTO {
    UserDataDTO userData;
    List<AsanaSendDTO> asanas;
    ActionStateDTO actionState;
    SettingsDTO settings;
    String error = "no";

    public DataDTO() {
    }

    public DataDTO(ClientEntity clientEntity) {
        this.setAsanas(addAsanaList(clientEntity));
        this.setUserData(addUserData(clientEntity));
        this.setActionState(new ActionStateDTO());
        this.setSettings(addSettings(clientEntity));
    }

    private SettingsDTO addSettings(ClientEntity clientEntity) {
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setLanguage(clientEntity.getLanguage());
        settingsDTO.setTimeOfFiltered(clientEntity.getTimeOfFiltered());
        settingsDTO.setProportionately(clientEntity.getProportionally());
        settingsDTO.setAddTime(clientEntity.getAddTime());
        return settingsDTO;
    }

    private List<AsanaSendDTO> addAsanaList(ClientEntity clientEntity) {
        List<AsanaSendDTO> asanaSendDTOList = new ArrayList<>();
        List<AsanaEntity> asanaEntityList = clientEntity.getAsanaEntityList();

        Comparator<AsanaEntity> comparatorGroupsFor = Comparator.comparing(AsanaEntity::getGroupFor);
        asanaEntityList.sort(comparatorGroupsFor);
        Comparator<AsanaEntity> comparatorIsSymmetric = Comparator.comparing(AsanaEntity::isSymmetric);
        asanaEntityList.sort(comparatorIsSymmetric);

        List<AsanaSendDTO> finalAsanaSendDTOList = asanaSendDTOList;
        asanaEntityList.forEach(asanaEntity -> {
            if (!asanaEntity.isSymmetric()){
                AsanaSendDTO asanaSendDTO = new AsanaSendDTO(asanaEntity);
                asanaSendDTO.side = "first";
                finalAsanaSendDTOList.add(asanaSendDTO);
                asanaSendDTO = new AsanaSendDTO(asanaEntity);
                asanaSendDTO.side = "second";
                finalAsanaSendDTOList.add(asanaSendDTO);
            } else
                finalAsanaSendDTOList.add(new AsanaSendDTO(asanaEntity));
        });

        if (!clientEntity.isSideBySideSort())
            asanaSendDTOList = compareFilter(finalAsanaSendDTOList);
        else
            asanaSendDTOList = finalAsanaSendDTOList;

        for (int i = 0; i < asanaSendDTOList.size(); i++) {
            asanaSendDTOList.get(i).setId(i + 1L);
        }

        return asanaSendDTOList;
    }

    private UserDataDTO addUserData(ClientEntity clientEntity) {
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setId(0L);
        userDataDTO.setAllCount(asanas.size());
        userDataDTO.setLevel(clientEntity.getLevel().ordinal());

        int allTIme = asanas.stream().mapToInt(asanaSendDTO -> (int) asanaSendDTO.times).sum();
        userDataDTO.setAllTime(allTIme);

        userDataDTO.setDate(new Date().toString());
        userDataDTO.setDangerknee(clientEntity.isDangerKnee());
        userDataDTO.setDangerneck(clientEntity.isDangerNeck());
        userDataDTO.setDangerloins(clientEntity.isDangerLoins());
        userDataDTO.setInverted(clientEntity.isInverted());
        userDataDTO.setSideBySideSort(clientEntity.isSideBySideSort());

        return userDataDTO;
    }

    private List<AsanaSendDTO> compareFilter(List<AsanaSendDTO> asanaSendDTOList) {
        List<AsanaSendDTO> sortedAsanaList = new ArrayList<>();
        asanaSendDTOList.forEach (asana -> {
            if (asana.side.equals("first"))
                sortedAsanaList.add(asana);
        });
        asanaSendDTOList.forEach (asana -> {
            if (asana.side.equals("second"))
                sortedAsanaList.add(asana);
        });
        asanaSendDTOList.forEach (asana -> {
            if (asana.side.equals("symmetric"))
                sortedAsanaList.add(asana);
        });

        return sortedAsanaList;
    }

}
