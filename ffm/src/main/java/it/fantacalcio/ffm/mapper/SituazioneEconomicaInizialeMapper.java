package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.SituazioneEconomicaInizialeDto;
import it.fantacalcio.ffm.domain.entity.SituazioneEconomicaIniziale;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SituazioneEconomicaInizialeMapper {
    SituazioneEconomicaInizialeMapper INSTANCE = Mappers.getMapper(SituazioneEconomicaInizialeMapper.class);

    SituazioneEconomicaInizialeDto toDto(SituazioneEconomicaIniziale situazioneEconomicaIniziale);
    SituazioneEconomicaIniziale toEntity(SituazioneEconomicaInizialeDto situazioneEconomicaInizialeDto);
}
