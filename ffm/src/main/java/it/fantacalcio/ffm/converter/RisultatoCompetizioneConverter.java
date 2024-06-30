package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import org.springframework.stereotype.Component;

@Component
public class RisultatoCompetizioneConverter {
    private RisultatoCompetizioneConverter() {}

    public static RisultatoCompetizioneDto toDto(RisultatoCompetizione risultatoCompetizione){
        return new RisultatoCompetizioneDto(risultatoCompetizione.getId(), risultatoCompetizione.getIdFantagazzetta(), risultatoCompetizione.getNome(),risultatoCompetizione.getRuolo(), risultatoCompetizione.getQuotazione());
    }

//    public static RisultatoCompetizione toEntity(RisultatoCompetizioneDto risultatoCompetizione){
//        return new RisultatoCompetizione(risultatoCompetizione.getId(), risultatoCompetizione.getIdFantagazzetta(), risultatoCompetizione.getNome(),risultatoCompetizione.getRuolo(), risultatoCompetizione.getQuotazione());
//    }
}
