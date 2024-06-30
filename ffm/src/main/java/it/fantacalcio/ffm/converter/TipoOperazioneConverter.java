package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TipoOperazione;
import org.springframework.stereotype.Component;

@Component
public class TipoOperazioneConverter {
    private TipoOperazioneConverter() {}

    public static TipoOperazioneDto toDto(TipoOperazione tipoOperazione){
        return new TipoOperazioneDto(tipoOperazione.getId(), tipoOperazione.getIdFantagazzetta(), tipoOperazione.getNome(),tipoOperazione.getRuolo(), tipoOperazione.getQuotazione());
    }

//    public static TipoOperazione toEntity(TipoOperazioneDto tipoOperazione){
//        return new TipoOperazione(tipoOperazione.getId(), tipoOperazione.getIdFantagazzetta(), tipoOperazione.getNome(),tipoOperazione.getRuolo(), tipoOperazione.getQuotazione());
//    }
}
