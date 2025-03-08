package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.OperazioneConverter;
import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.repository.OperazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperazioneService {

    private final OperazioneRepository operazioneRepository;

    @Autowired
    public OperazioneService(OperazioneRepository operazioneRepository){
        this.operazioneRepository = operazioneRepository;
    }

    public OperazioneDto save(OperazioneDto operazioneDto){
        Operazione operazioneInsertita = operazioneRepository.save(OperazioneConverter.toEntity(operazioneDto));
        return OperazioneConverter.toDto(operazioneInsertita);
    }
}
