package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import it.fantacalcio.ffm.domain.entity.Operazione;
import org.springframework.stereotype.Component;

@Component
public class OperazioneConverter {
    private OperazioneConverter() {}

    public static OperazioneDto toDto(Operazione operazione){
        return new OperazioneDto(operazione.getId(),
                SquadraConverter.toDto(operazione.getIdSquadra()),
                GiocatoreConverter.toDto(operazione.getIdGiocatore()),
                TipoOperazioneConverter.toDto(operazione.getIdTipoOperazione()),
                StagioneConverter.toDto(operazione.getIdStagione()),
                operazione.getData());
    }

    public static Operazione toEntity(OperazioneDto operazione){
        Operazione operazioneEntity = new Operazione();
        operazioneEntity.setId(operazione.getId());
        operazioneEntity.setIdTipoOperazione(TipoOperazioneConverter.toEntity(operazione.getIdTipoOperazione()));
        operazioneEntity.setIdSquadra(SquadraConverter.toEntity(operazione.getIdSquadra()));
        operazioneEntity.setIdStagione(StagioneConverter.toEntity(operazione.getIdStagione()));
        operazioneEntity.setIdGiocatore(GiocatoreConverter.toEntity(operazione.getIdGiocatore()));
        operazioneEntity.setData(operazione.getData());
        return operazioneEntity;
    }
}
