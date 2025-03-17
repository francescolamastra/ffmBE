package it.fantacalcio.ffm.factory;

import it.fantacalcio.ffm.builder.SquadraBuilder;
import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.converter.CategoriaConverter;
import it.fantacalcio.ffm.converter.NazioneConverter;
import it.fantacalcio.ffm.converter.StadioConverter;

public class SquadraFactory {
    public static Squadra createSquadra(SquadraDto squadraDto) {
        return new SquadraBuilder()
                .setId(squadraDto.getId())
                .setNome(squadraDto.getNome())
                .setIdCategoria(CategoriaConverter.toEntity(squadraDto.getIdCategoria()))
                .setIdNazione(NazioneConverter.toEntity(squadraDto.getIdNazione()))
                .setIdStadio(squadraDto.getIdStadio() != null ? StadioConverter.toEntity(squadraDto.getIdStadio()) : null)
                .build();
    }

    public static SquadraDto createSquadra(Squadra squadra) {
        return new SquadraDtoBuilder()
                .setId(squadra.getId())
                .setNome(squadra.getNome())
                .setIdCategoria(CategoriaConverter.toDto(squadra.getIdCategoria()))
                .setIdNazione(NazioneConverter.toDto(squadra.getIdNazione()))
                .setIdStadio(squadra.getIdStadio() != null ? StadioConverter.toDto(squadra.getIdStadio()) : null)
                .build();
    }
}