package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.GiocatoreRosaDto;
import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GiocatoreRosaMapper {
    GiocatoreRosaMapper INSTANCE = Mappers.getMapper(GiocatoreRosaMapper.class);

    GiocatoreRosaDto toDto(GiocatoreRosa giocatoreRosa);
    GiocatoreRosa toEntity(GiocatoreRosaDto giocatoreRosaDto);
}
