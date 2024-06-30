package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TrattativaDto;
import it.fantacalcio.ffm.domain.entity.Trattativa;
import org.springframework.stereotype.Component;

@Component
public class TrattativaConverter {
    private TrattativaConverter() {}

    public static TrattativaDto toDto(Trattativa trattativa){
        return new TrattativaDto(trattativa.getId(), trattativa.getIdFantagazzetta(), trattativa.getNome(),trattativa.getRuolo(), trattativa.getQuotazione());
    }

//    public static Trattativa toEntity(TrattativaDto trattativa){
//        return new Trattativa(trattativa.getId(), trattativa.getIdFantagazzetta(), trattativa.getNome(),trattativa.getRuolo(), trattativa.getQuotazione());
//    }
}
