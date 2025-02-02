package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FinanzeInizialiSquadraDto;
import it.fantacalcio.ffm.domain.entity.FinanzeInizialiSquadra;
import it.fantacalcio.ffm.domain.entity.Squadra;
import org.springframework.stereotype.Component;

@Component
public class FinanzeInizialiSquadraConverter {
    private FinanzeInizialiSquadraConverter() {}

    public static FinanzeInizialiSquadraDto toDto(FinanzeInizialiSquadra finanzeInizialiSquadra){
        return new FinanzeInizialiSquadraDto(finanzeInizialiSquadra.getId(), StagioneConverter.toDto(finanzeInizialiSquadra.getIdStagione()), SquadraConverter.toDto(finanzeInizialiSquadra.getIdSquadra()),finanzeInizialiSquadra.getImporto());
    }

//    public static FinanzeInizialiSquadra toEntity(FinanzeInizialiSquadraDto finanzeInizialiSquadra){
//        return new FinanzeInizialiSquadra(finanzeInizialiSquadra.getId(), finanzeInizialiSquadra.getIdFantagazzetta(), finanzeInizialiSquadra.getNome(),finanzeInizialiSquadra.getRuolo(), finanzeInizialiSquadra.getQuotazione());
//    }
}
