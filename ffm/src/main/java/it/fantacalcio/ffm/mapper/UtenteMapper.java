package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.domain.entity.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UtenteMapper {
    UtenteMapper INSTANCE = Mappers.getMapper(UtenteMapper.class);

    UtenteDto toDto(Utente utente);
    Utente toEntity(UtenteDto utenteDto);
}
