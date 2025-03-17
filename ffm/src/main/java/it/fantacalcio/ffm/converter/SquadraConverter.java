package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.factory.SquadraFactory;

public class SquadraConverter {
    private SquadraConverter() {}

    public static SquadraDto toDto(Squadra squadra){
        return SquadraFactory.createSquadra(squadra);
    }

    public static Squadra toEntity(SquadraDto squadraDto) {
        return SquadraFactory.createSquadra(squadraDto);
    }

}
