package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TransazioneOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TransazioneOperazione;
import org.springframework.stereotype.Component;

@Component
public class TransazioneOperazioneConverter {
    private TransazioneOperazioneConverter() {}

    public static TransazioneOperazioneDto toDto(TransazioneOperazione transazioneOperazione){
        return new TransazioneOperazioneDto(transazioneOperazione.getId(), transazioneOperazione.getIdFantagazzetta(), transazioneOperazione.getNome(),transazioneOperazione.getRuolo(), transazioneOperazione.getQuotazione());
    }

//    public static TransazioneOperazione toEntity(TransazioneOperazioneDto transazioneOperazione){
//        return new TransazioneOperazione(transazioneOperazione.getId(), transazioneOperazione.getIdFantagazzetta(), transazioneOperazione.getNome(),transazioneOperazione.getRuolo(), transazioneOperazione.getQuotazione());
//    }
}
