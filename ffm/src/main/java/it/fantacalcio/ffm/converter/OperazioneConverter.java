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

//    public static Operazione toEntity(OperazioneDto operazione){
//        return new Operazione(operazione.getId(), operazione.getIdFantagazzetta(), operazione.getNome(),operazione.getRuolo(), operazione.getQuotazione());
//    }
}
