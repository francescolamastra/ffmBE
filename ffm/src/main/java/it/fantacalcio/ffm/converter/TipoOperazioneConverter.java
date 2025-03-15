package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TipoOperazione;

public class TipoOperazioneConverter {
    private TipoOperazioneConverter() {}

    public static TipoOperazioneDto toDto(TipoOperazione tipoOperazione){
        return new TipoOperazioneDto(tipoOperazione.getId(),
                tipoOperazione.getSigla(),
                tipoOperazione.getDescrizione());
    }

    public static TipoOperazione toEntity(TipoOperazioneDto tipoOperazione){
        TipoOperazione tipoOperazioneEntity = new TipoOperazione();
        tipoOperazioneEntity.setId(tipoOperazione.getId());
        tipoOperazioneEntity.setSigla(tipoOperazione.getSigla());
        tipoOperazioneEntity.setDescrizione(tipoOperazione.getDescrizione());
        return tipoOperazioneEntity;
    }
}
