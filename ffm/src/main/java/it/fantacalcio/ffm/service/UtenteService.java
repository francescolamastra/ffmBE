package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.UtenteConverter;
import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.domain.entity.Utente;
import it.fantacalcio.ffm.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    UtenteRepository utenteRepository;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository){
        this.utenteRepository = utenteRepository;
    }

    public UtenteDto save(UtenteDto utenteDto){
        Utente utenteInsertito = utenteRepository.save(UtenteConverter.toEntity(utenteDto));
        return UtenteConverter.toDto(utenteInsertito);
    }

    public List<UtenteDto> findAll(){
        return utenteRepository.findAll().stream().map(UtenteConverter::toDto).toList();
    }
}
