package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.AcquistoDto;
import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import it.fantacalcio.ffm.domain.entity.Acquisto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import org.springframework.stereotype.Component;

@Component
public class AcquistoConverter {
    private AcquistoConverter() {}

    public static AcquistoDto toDto(Acquisto acquisto){
        return new AcquistoDto(acquisto.getId(), OperazioneConverter.toDto(acquisto.getIdOperazione()), acquisto.getAnniContratto());
    }

//    public static Giocatore toEntity(AcquistoDto giocatore){
//        return new Giocatore(giocatore.getId(), giocatore.getIdFantagazzetta(), giocatore.getNome(),giocatore.getRuolo(), giocatore.getQuotazione());
//    }
}
