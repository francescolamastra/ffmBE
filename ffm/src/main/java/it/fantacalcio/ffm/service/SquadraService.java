package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.entity.Utente;
import it.fantacalcio.ffm.domain.entity.UtenteSquadra;
import it.fantacalcio.ffm.repository.SquadraRepository;
import it.fantacalcio.ffm.repository.UtenteRepository;
import it.fantacalcio.ffm.repository.UtenteSquadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SquadraService {

    private final SquadraRepository squadraRepository;
    private final UtenteRepository utenteRepository;
    private final UtenteSquadraRepository utenteSquadraRepository;

    @Autowired
    public SquadraService(SquadraRepository squadraRepository, UtenteRepository utenteRepository, UtenteSquadraRepository utenteSquadraRepository){
        this.squadraRepository = squadraRepository;
        this.utenteRepository = utenteRepository;
        this.utenteSquadraRepository = utenteSquadraRepository;
    }
    public SquadraDto save(SquadraDto squadraDto, Integer utenteId){
        Utente utente = utenteRepository.findById(utenteId).orElseThrow();
        Squadra squadraInserita = squadraRepository.save(SquadraConverter.toEntity(squadraDto));
        UtenteSquadra utenteSquadra = new UtenteSquadra();
        utenteSquadra.setIdUtente(utente);
        utenteSquadra.setIdSquadra(squadraInserita);
        utenteSquadraRepository.save(utenteSquadra);
        return SquadraConverter.toDto(squadraInserita);
    }

    public SquadraDto save(SquadraDto squadraDto){
        Squadra squadraInserita = squadraRepository.save(SquadraConverter.toEntity(squadraDto));
        return SquadraConverter.toDto(squadraInserita);
    }

    public List<SquadraDto> findAll(){
        return squadraRepository.findAll().stream().map(SquadraConverter::toDto).toList();
    }

    public Optional<SquadraDto> findByNome(String nome){
        return squadraRepository.findByNome(nome).map(SquadraConverter::toDto);
    }
}
