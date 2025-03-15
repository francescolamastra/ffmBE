package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;

public class SquadraConverter {
    private SquadraConverter() {}

    public static SquadraDto toDto(Squadra squadra){
        return new SquadraDto(squadra.getId(),
                NazioneConverter.toDto(squadra.getIdNazione()),
                CategoriaConverter.toDto(squadra.getIdCategoria()),
                squadra.getNome(),
                StadioConverter.toDto(squadra.getIdStadio()),
                squadra.getDataCreazione());
    }

    public static Squadra toEntity(SquadraDto squadraDto) {
        Squadra squadraEntity = new Squadra();
        squadraEntity.setId(squadraDto.getId());
        squadraEntity.setNome(squadraDto.getNome());
        squadraEntity.setIdCategoria(CategoriaConverter.toEntity(squadraDto.getIdCategoria()));
        squadraEntity.setIdNazione(NazioneConverter.toEntity(squadraDto.getIdNazione()));
        squadraEntity.setIdStadio(StadioConverter.toEntity(squadraDto.getIdStadio()));
        return squadraEntity;
    }

}
