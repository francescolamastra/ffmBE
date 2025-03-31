package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.CredenzialiConverter;
import it.fantacalcio.ffm.converter.UtenteConverter;
import it.fantacalcio.ffm.domain.dto.CredenzialiDto;
import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.domain.entity.Credenziali;
import it.fantacalcio.ffm.repository.CredenzialiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredenzialiService {

    private final CredenzialiRepository credenzialiRepository;

    public Optional<CredenzialiDto> findById(Integer id){
        return credenzialiRepository.findById(id).map(CredenzialiConverter::toDto);
    }

    public Optional<CredenzialiDto> findByIdUtente(UtenteDto utenteDto){
        return credenzialiRepository.findByIdUtente(UtenteConverter.toEntity(utenteDto)).map(CredenzialiConverter::toDto);
    }

    public CredenzialiDto save(CredenzialiDto credenzialiDto){
        Credenziali credenzialiInserite = credenzialiRepository.save(CredenzialiConverter.toEntity(credenzialiDto));
        return CredenzialiConverter.toDto(credenzialiInserite);
    }
}
