package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.entity.Nazione;
import org.springframework.stereotype.Component;

public class NazioneConverter {
    private NazioneConverter() {}

    public static NazioneDto toDto(Nazione nazione){
        return new NazioneDto(nazione.getId(),
                nazione.getDescrizione(),
                nazione.getSigla());
    }

    public static Nazione toEntity(NazioneDto nazione) {
        Nazione nazioneEntity = new Nazione();
        nazioneEntity.setId(nazione.getId());
        nazioneEntity.setDescrizione(nazione.getDescrizione());
        nazioneEntity.setSigla(nazione.getSigla());
        return nazioneEntity;
    }
}
