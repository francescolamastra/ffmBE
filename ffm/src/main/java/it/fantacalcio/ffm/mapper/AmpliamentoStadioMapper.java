package it.fantacalcio.ffm.mapper;

import it.fantacalcio.ffm.domain.dto.AmpliamentoStadioDto;
import it.fantacalcio.ffm.domain.entity.AmpliamentoStadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AmpliamentoStadioMapper {
    AmpliamentoStadioMapper INSTANCE = Mappers.getMapper(AmpliamentoStadioMapper.class);

    AmpliamentoStadioDto toDto(AmpliamentoStadio ampliamentoStadio);
    AmpliamentoStadio toEntity(AmpliamentoStadioDto ampliamentoStadioDto);
}
