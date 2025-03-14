package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.ListoneDto;
import it.fantacalcio.ffm.domain.entity.Listone;

public class ListoneConverter {
    private ListoneConverter() {}

    public static ListoneDto toDto(Listone listone){
        return new ListoneDto(listone.getId(),
                listone.getIdFantagazzetta(),
                StagioneConverter.toDto(listone.getIdStagione()),
                listone.getFvm(),
                listone.getDataCreazione());
    }

    public static Listone toEntity(ListoneDto listoneDto){
        Listone listoneEntity = new Listone();
        listoneEntity.setId(listoneDto.id());
        listoneEntity.setIdFantagazzetta(listoneDto.idFantagazzetta());
        listoneEntity.setIdStagione(StagioneConverter.toEntity(listoneDto.idStagione()));
        listoneEntity.setFvm(listoneDto.fvm());
        listoneEntity.setDataCreazione(listoneDto.dataCreazione());
        return listoneEntity;
    }
}
