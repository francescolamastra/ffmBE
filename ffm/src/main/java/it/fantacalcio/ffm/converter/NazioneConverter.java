package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.entity.Nazione;
import org.springframework.stereotype.Component;

@Component
public class NazioneConverter {
    private NazioneConverter() {}

    public static NazioneDto toDto(Nazione nazione){
        return new NazioneDto(nazione.getId(), nazione.getIdFantagazzetta(), nazione.getNome(),nazione.getRuolo(), nazione.getQuotazione());
    }

//    public static Nazione toEntity(NazioneDto nazione){
//        return new Nazione(nazione.getId(), nazione.getIdFantagazzetta(), nazione.getNome(),nazione.getRuolo(), nazione.getQuotazione());
//    }
}
