package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.DettaglioTrattativaDto;
import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import org.springframework.stereotype.Component;

@Component
public class DettaglioTrattativaConverter {
    private DettaglioTrattativaConverter() {}

    public static DettaglioTrattativaDto toDto(DettaglioTrattativa dettaglioTrattativa){
        return new DettaglioTrattativaDto(dettaglioTrattativa.getId(), TrattativaConverter.toDto(dettaglioTrattativa.getIdTrattativa()), TipoDettTrattativaConverter.toDto(dettaglioTrattativa.getIdTipoDettTrattativa()),SquadraConverter.toDto(dettaglioTrattativa.getIdSquadra()),GiocatoreConverter.toDto(dettaglioTrattativa.getIdGiocatore()), TipoOperazioneConverter.toDto(dettaglioTrattativa.getIdTipoOperazione()));
    }

//    public static DettaglioTrattativa toEntity(DettaglioTrattativaDto dettaglioTrattativa){
//        return new DettaglioTrattativa(dettaglioTrattativa.getId(), dettaglioTrattativa.getIdFantagazzetta(), dettaglioTrattativa.getNome(),dettaglioTrattativa.getRuolo(), dettaglioTrattativa.getQuotazione());
//    }
}
