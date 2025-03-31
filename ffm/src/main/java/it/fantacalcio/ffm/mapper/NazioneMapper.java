package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.entity.Nazione;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NazioneMapper {
    NazioneMapper INSTANCE = Mappers.getMapper(NazioneMapper.class);

    NazioneDto toDto(Nazione nazione);
    Nazione toEntity(NazioneDto nazioneDto);
}
