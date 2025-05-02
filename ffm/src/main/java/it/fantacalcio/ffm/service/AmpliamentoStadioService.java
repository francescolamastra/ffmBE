package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.dto.AmpliamentoStadioDto;
import it.fantacalcio.ffm.domain.entity.AmpliamentoStadio;
import it.fantacalcio.ffm.mapper.AmpliamentoStadioMapper;
import it.fantacalcio.ffm.repository.AmpliamentoStadioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmpliamentoStadioService {

    private final AmpliamentoStadioRepository ampliamentoStadioRepository;

    public AmpliamentoStadioDto save(AmpliamentoStadioDto ampliamentoStadioDto){
        AmpliamentoStadio ampliamentoStadio = ampliamentoStadioRepository.save(AmpliamentoStadioMapper.INSTANCE.toEntity(ampliamentoStadioDto));
        return AmpliamentoStadioMapper.INSTANCE.toDto(ampliamentoStadio);
    }
}
