package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.TransazioneTrattativaConverter;
import it.fantacalcio.ffm.domain.dto.TransazioneTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TransazioneTrattativa;
import it.fantacalcio.ffm.repository.TransazioneTrattativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransazioneTrattativaService {

    private final TransazioneTrattativaRepository transazioneTrattativaRepository;

    @Autowired
    public TransazioneTrattativaService(TransazioneTrattativaRepository transazioneTrattativaRepository){
        this.transazioneTrattativaRepository = transazioneTrattativaRepository;
    }

    public TransazioneTrattativaDto save(TransazioneTrattativaDto transazioneTrattativaDto){
        TransazioneTrattativa transazioneTrattativaInserita = transazioneTrattativaRepository.save(TransazioneTrattativaConverter.toEntity(transazioneTrattativaDto));
        return TransazioneTrattativaConverter.toDto(transazioneTrattativaInserita);
    }
}
