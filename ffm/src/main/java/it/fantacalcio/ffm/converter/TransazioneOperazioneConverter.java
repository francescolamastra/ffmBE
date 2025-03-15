package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TransazioneOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TransazioneOperazione;

public class TransazioneOperazioneConverter {
    private TransazioneOperazioneConverter() {}

    public static TransazioneOperazioneDto toDto(TransazioneOperazione transazioneOperazione){
        return new TransazioneOperazioneDto(transazioneOperazione.getId(),
                OperazioneConverter.toDto(transazioneOperazione.getIdOperazione()),
                transazioneOperazione.getImporto(),
                transazioneOperazione.getTipoTransazione());
    }

    public static TransazioneOperazione toEntity(TransazioneOperazioneDto transazioneOperazione){
        TransazioneOperazione transazioneOperazioneEntity = new TransazioneOperazione();
        transazioneOperazioneEntity.setId(transazioneOperazione.getId());
        transazioneOperazioneEntity.setIdOperazione(OperazioneConverter.toEntity(transazioneOperazione.getIdOperazione()));
        transazioneOperazioneEntity.setTipoTransazione(transazioneOperazione.getTipoTransazione());
        transazioneOperazioneEntity.setImporto(transazioneOperazione.getImporto());
        return transazioneOperazioneEntity;
    }
}
