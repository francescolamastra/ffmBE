package it.fantacalcio.ffm.factory;

import it.fantacalcio.ffm.builder.SquadraBuilder;
import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.converter.CategoriaConverter;
import it.fantacalcio.ffm.converter.NazioneConverter;
import it.fantacalcio.ffm.converter.StadioConverter;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;

public class SquadraFactory {
    public static Squadra createSquadra(SquadraDto squadraDto) {
        return new SquadraBuilder()
                .setId(squadraDto.getId())
                .setIdFantagazzetta(squadraDto.getIdFantagazzetta())
                .setNome(squadraDto.getNome())
                .setIdCategoria(CategoriaConverter.toEntity(squadraDto.getIdCategoria()))
                .setIdNazione(NazioneConverter.toEntity(squadraDto.getIdNazione()))
                .build();
    }

    public static SquadraDto createSquadra(Squadra squadra) {
        return new SquadraDtoBuilder()
                .setId(squadra.getId())
                .setIdFantagazzetta(squadra.getIdFantagazzetta())
                .setNome(squadra.getNome())
                .setIdCategoria(CategoriaConverter.toDto(squadra.getIdCategoria()))
                .setIdNazione(NazioneConverter.toDto(squadra.getIdNazione()))
                .build();
    }
}