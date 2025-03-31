package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.TokenCredenzialiProjectionDto;
import it.fantacalcio.ffm.domain.entity.TokenCredenziali;
import it.fantacalcio.ffm.domain.entity.TokenCredenzialiInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = NazioneMapper.class)
public interface TokenCredenzialiProjectionMapper {
    TokenCredenzialiProjectionMapper INSTANCE = Mappers.getMapper(TokenCredenzialiProjectionMapper.class);

    TokenCredenzialiProjectionDto toDto(TokenCredenzialiInfo tokenCredenzialiInfo);

    TokenCredenzialiProjectionDto toDto(TokenCredenziali tokenCredenziali);

    TokenCredenziali toEntity(TokenCredenzialiProjectionDto tokenCredenzialiProjectionDto);
}

