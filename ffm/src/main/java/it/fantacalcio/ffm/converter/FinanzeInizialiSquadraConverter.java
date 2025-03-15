package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FinanzeInizialiSquadraDto;
import it.fantacalcio.ffm.domain.entity.FinanzeInizialiSquadra;

public class FinanzeInizialiSquadraConverter {
    private FinanzeInizialiSquadraConverter() {}

    public static FinanzeInizialiSquadraDto toDto(FinanzeInizialiSquadra finanzeInizialiSquadra){
        return new FinanzeInizialiSquadraDto(finanzeInizialiSquadra.getId(),
                StagioneConverter.toDto(finanzeInizialiSquadra.getIdStagione()),
                SquadraConverter.toDto(finanzeInizialiSquadra.getIdSquadra()),
                finanzeInizialiSquadra.getImporto());
    }

    public static FinanzeInizialiSquadra toEntity(FinanzeInizialiSquadraDto finanzeInizialiSquadra){
        FinanzeInizialiSquadra finanzeInizialiSquadraEntity = new FinanzeInizialiSquadra();
        finanzeInizialiSquadraEntity.setId(finanzeInizialiSquadra.getId());
        finanzeInizialiSquadraEntity.setIdSquadra(SquadraConverter.toEntity(finanzeInizialiSquadra.getIdSquadra()));
        finanzeInizialiSquadraEntity.setIdStagione(StagioneConverter.toEntity(finanzeInizialiSquadra.getIdStagione()));
        finanzeInizialiSquadraEntity.setImporto(finanzeInizialiSquadra.getImporto());
        return finanzeInizialiSquadraEntity;
    }
}
