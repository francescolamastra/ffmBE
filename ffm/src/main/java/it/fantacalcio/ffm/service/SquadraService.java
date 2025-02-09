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
        Squadra squadraInsertita = squadraRepository.save(SquadraConverter.toEntity(squadraDto));
        UtenteSquadra utenteSquadra = new UtenteSquadra();
        utenteSquadra.setIdUtente(utente);
        utenteSquadra.setIdSquadra(squadraInsertita);
        utenteSquadraRepository.save(utenteSquadra);
        return SquadraConverter.toDto(squadraInsertita);
    }

    public SquadraDto save(SquadraDto squadraDto){
        Squadra squadraInsertita = squadraRepository.save(SquadraConverter.toEntity(squadraDto));
        return SquadraConverter.toDto(squadraInsertita);
    }
}
