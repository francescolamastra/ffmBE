package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiocatoreService {

    GiocatoreRepository giocatoreRepository;

    @Autowired
    public GiocatoreService(GiocatoreRepository giocatoreRepository){
        this.giocatoreRepository = giocatoreRepository;
    }

    public Optional<GiocatoreDto> findByIdFantagazzetta(Integer idFantagazzetta){
        return giocatoreRepository.findByIdFantagazzetta(idFantagazzetta).map(GiocatoreConverter::toDto);
    }

    public List<GiocatoreDto> findAll(){
        return giocatoreRepository.findAll().stream().map(GiocatoreConverter::toDto).toList();
    }

    public GiocatoreDto save(GiocatoreDto giocatoreDto){
        Giocatore giocatoreInserito = giocatoreRepository.save(GiocatoreConverter.toEntity(giocatoreDto));
        return GiocatoreConverter.toDto(giocatoreInserito);
    }
}
