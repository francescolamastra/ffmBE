package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.DettaglioTrattativaDto;
import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import org.springframework.stereotype.Component;

@Component
public class DettaglioTrattativaConverter {
    private DettaglioTrattativaConverter() {}

    public static DettaglioTrattativaDto toDto(DettaglioTrattativa dettaglioTrattativa){
        return new DettaglioTrattativaDto(dettaglioTrattativa.getId(),
                TrattativaConverter.toDto(dettaglioTrattativa.getIdTrattativa()),
                TipoDettTrattativaConverter.toDto(dettaglioTrattativa.getIdTipoDettTrattativa()),
                SquadraConverter.toDto(dettaglioTrattativa.getIdSquadra()),
                GiocatoreConverter.toDto(dettaglioTrattativa.getIdGiocatore()),
                TipoOperazioneConverter.toDto(dettaglioTrattativa.getIdTipoOperazione()));
    }

    public static DettaglioTrattativa toEntity(DettaglioTrattativaDto dettaglioTrattativa){
        DettaglioTrattativa dettaglioTrattativaEntity = new DettaglioTrattativa();
        dettaglioTrattativaEntity.setId(dettaglioTrattativa.getId());
        dettaglioTrattativaEntity.setIdTrattativa(TrattativaConverter.toEntity(dettaglioTrattativa.getIdTrattativa()));
        dettaglioTrattativaEntity.setIdTipoDettTrattativa(TipoDettTrattativaConverter.toEntity(dettaglioTrattativa.getIdTipoDettTrattativa()));
        dettaglioTrattativaEntity.setIdSquadra(SquadraConverter.toEntity(dettaglioTrattativa.getIdSquadra()));
        dettaglioTrattativaEntity.setIdGiocatore(GiocatoreConverter.toEntity(dettaglioTrattativa.getIdGiocatore()));
        dettaglioTrattativaEntity.setIdTipoOperazione(TipoOperazioneConverter.toEntity(dettaglioTrattativa.getIdTipoOperazione()));
        return dettaglioTrattativaEntity;
    }
}
