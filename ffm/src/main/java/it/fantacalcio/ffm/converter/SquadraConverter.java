package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.entity.Stadio;
import org.springframework.stereotype.Component;

@Component
public class SquadraConverter {
    private SquadraConverter() {}

    public static SquadraDto toDto(Squadra squadra){
        return new SquadraDto(squadra.getId(), NazioneConverter.toDto(squadra.getIdNazione()), CategoriaConverter.toDto(squadra.getIdCategoria()),squadra.getNome(), StadioConverter.toDto(squadra.getIdStadio()));
    }

//    public static Squadra toEntity(SquadraDto squadra){
//        return new Squadra(squadra.getId(), squadra.getIdFantagazzetta(), squadra.getNome(),squadra.getRuolo(), squadra.getQuotazione());
//    }
}
