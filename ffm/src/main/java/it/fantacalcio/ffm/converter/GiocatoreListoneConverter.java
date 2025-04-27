package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.GiocatoreListoneDto;
import it.fantacalcio.ffm.domain.entity.GiocatoreListone;

public class GiocatoreListoneConverter {
    private GiocatoreListoneConverter() {}

    public static GiocatoreListoneDto toDto(GiocatoreListone giocatoreListone){
        return new GiocatoreListoneDto(giocatoreListone.getId(),
                giocatoreListone.getIdFantagazzetta(),
                StagioneConverter.toDto(giocatoreListone.getIdStagione()),
                giocatoreListone.getFvm(),
                giocatoreListone.getTipologiaListone(),
                giocatoreListone.getDataCreazione());
    }

    public static GiocatoreListone toEntity(GiocatoreListoneDto giocatoreListoneDto){
        GiocatoreListone giocatoreListoneEntity = new GiocatoreListone();
        giocatoreListoneEntity.setId(giocatoreListoneDto.id());
        giocatoreListoneEntity.setIdFantagazzetta(giocatoreListoneDto.idFantagazzetta());
        giocatoreListoneEntity.setIdStagione(StagioneConverter.toEntity(giocatoreListoneDto.idStagione()));
        giocatoreListoneEntity.setFvm(giocatoreListoneDto.fvm());
        giocatoreListoneEntity.setTipologiaListone(giocatoreListoneDto.tipologiaListone());
        giocatoreListoneEntity.setDataCreazione(giocatoreListoneDto.dataCreazione());
        return giocatoreListoneEntity;
    }
}
