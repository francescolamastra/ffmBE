package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.converter.TransazioneTrattativaConverter;
import it.fantacalcio.ffm.converter.TrattativaConverter;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.TransazioneTrattativaDto;
import it.fantacalcio.ffm.domain.dto.TrattativaDto;
import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import it.fantacalcio.ffm.repository.TransazioneTrattativaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransazioneTrattativaService {

    private final TransazioneTrattativaRepository transazioneTrattativaRepository;

    public TransazioneTrattativaDto save(TransazioneTrattativaDto transazioneTrattativaDto){
        TransazioneTrattativa transazioneTrattativaInserita = transazioneTrattativaRepository.save(TransazioneTrattativaConverter.toEntity(transazioneTrattativaDto));
        return TransazioneTrattativaConverter.toDto(transazioneTrattativaInserita);
    }

    public Optional<TransazioneTrattativaDto> findByTrattativaAndSquadra(TrattativaDto trattativaDto, SquadraDto squadraDto) {
        return transazioneTrattativaRepository.findByIdTrattativaAndIdSquadra(TrattativaConverter.toEntity(trattativaDto), SquadraConverter.toEntity(squadraDto)).map(TransazioneTrattativaConverter::toDto);
    }
}
