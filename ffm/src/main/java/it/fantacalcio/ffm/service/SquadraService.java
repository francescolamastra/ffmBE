package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.SquadraCache;
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
    private final SquadraCache squadraCache;
    private final UtenteRepository utenteRepository;
    private final UtenteSquadraRepository utenteSquadraRepository;

    @Autowired
    public SquadraService(SquadraRepository squadraRepository, SquadraCache squadraCache, UtenteRepository utenteRepository, UtenteSquadraRepository utenteSquadraRepository){
        this.squadraRepository = squadraRepository;
        this.squadraCache = squadraCache;
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

    public Optional<SquadraDto> findById(Integer id){
        return squadraRepository.findById(id).map(SquadraConverter::toDto);
    }

    public Optional<SquadraDto> findByNome(String nome){
        return squadraRepository.findByNome(nome).map(SquadraConverter::toDto);
    }

    public Optional<SquadraDto> findByIdFantagazzetta(Integer idFantagazzetta){
        return squadraRepository.findByIdFantagazzetta(idFantagazzetta).map(SquadraConverter::toDto);
    }

    public SquadraDto findByNomeOrSave(SquadraDto squadraDto) {
        return squadraCache.getSquadraList().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(squadraDto.getNome()))
                .findFirst()
                .orElseGet(() -> {
                    Optional<SquadraDto> optionalSquadraFromDB = findByNome(squadraDto.getNome());
                    if (optionalSquadraFromDB.isPresent()) {
                        return squadraCache.addSquadra(optionalSquadraFromDB.get());
                    } else {
                        return save(squadraDto);
                    }
                });
    }
    public SquadraDto findByIdFantagazzetta(SquadraDto squadraDto) {
        return squadraCache.getSquadraList().stream()
                .filter(c -> c.getIdFantagazzetta().equals(squadraDto.getIdFantagazzetta()))
                .findFirst()
                .orElseGet(() -> {
                    Optional<SquadraDto> optionalSquadraFromDB = findByIdFantagazzetta(squadraDto.getIdFantagazzetta());
                    return optionalSquadraFromDB.map(squadraCache::addSquadra).orElse(null);
                });
    }
}
