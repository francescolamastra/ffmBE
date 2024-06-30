package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TransazioneTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import org.springframework.stereotype.Component;

@Component
public class TransazioneTrattativaConverter {
    private TransazioneTrattativaConverter() {}

    public static TransazioneTrattativaDto toDto(TransazioneTrattativa gransazioneTrattativa){
        return new TransazioneTrattativaDto(gransazioneTrattativa.getId(), gransazioneTrattativa.getIdFantagazzetta(), gransazioneTrattativa.getNome(),gransazioneTrattativa.getRuolo(), gransazioneTrattativa.getQuotazione());
    }

//    public static TransazioneTrattativa toEntity(TransazioneTrattativaDto gransazioneTrattativa){
//        return new TransazioneTrattativa(gransazioneTrattativa.getId(), gransazioneTrattativa.getIdFantagazzetta(), gransazioneTrattativa.getNome(),gransazioneTrattativa.getRuolo(), gransazioneTrattativa.getQuotazione());
//    }
}
