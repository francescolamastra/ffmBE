package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.TokenCredenzialiDto;
import it.fantacalcio.ffm.domain.entity.TokenCredenziali;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtenteMapper.class, NazioneMapper.class})
public interface TokenCredenzialiMapper {
    TokenCredenzialiMapper INSTANCE = Mappers.getMapper(TokenCredenzialiMapper.class);

    TokenCredenzialiDto toDto(TokenCredenziali tokenCredenziali);
    TokenCredenziali toEntity(TokenCredenzialiDto tokenCredenzialiDto);
}
