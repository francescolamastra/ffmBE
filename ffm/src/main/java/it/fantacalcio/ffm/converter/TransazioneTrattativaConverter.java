package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.TransazioneTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import org.springframework.stereotype.Component;

@Component
public class TransazioneTrattativaConverter {
    private TransazioneTrattativaConverter() {}

    public static TransazioneTrattativaDto toDto(TransazioneTrattativa transazioneTrattativa){
        return new TransazioneTrattativaDto(transazioneTrattativa.getId(), TrattativaConverter.toDto(transazioneTrattativa.getIdTrattativa()),
                SquadraConverter.toDto(transazioneTrattativa.getIdSquadra()),
                transazioneTrattativa.getImporto(),
                transazioneTrattativa.getTipoTransazione(),
                transazioneTrattativa.getGettoniSpesi());
    }

//    public static TransazioneTrattativa toEntity(TransazioneTrattativaDto gransazioneTrattativa){
//        return new TransazioneTrattativa(gransazioneTrattativa.getId(), gransazioneTrattativa.getIdFantagazzetta(), gransazioneTrattativa.getNome(),gransazioneTrattativa.getRuolo(), gransazioneTrattativa.getQuotazione());
//    }
}
