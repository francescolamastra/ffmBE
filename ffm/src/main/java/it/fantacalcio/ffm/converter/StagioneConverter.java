package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.Stagione;
import org.springframework.stereotype.Component;

@Component
public class StagioneConverter {
    private StagioneConverter() {}

    public static StagioneDto toDto(Stagione stagione){
        return new StagioneDto(stagione.getId(), stagione.getIdFantagazzetta(), stagione.getNome(),stagione.getRuolo(), stagione.getQuotazione());
    }

//    public static Stagione toEntity(StagioneDto stagione){
//        return new Stagione(stagione.getId(), stagione.getIdFantagazzetta(), stagione.getNome(),stagione.getRuolo(), stagione.getQuotazione());
//    }
}
