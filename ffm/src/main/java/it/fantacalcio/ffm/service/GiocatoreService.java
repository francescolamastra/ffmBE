package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.repository.GiocatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiocatoreService {

    GiocatoreRepository giocatoreRepository;
    GiocatoreConverter giocatoreConverter;

    @Autowired
    public GiocatoreService(GiocatoreRepository giocatoreRepository, GiocatoreConverter giocatoreConverter){
        this.giocatoreRepository = giocatoreRepository;
        this.giocatoreConverter = giocatoreConverter;
    }

    public Giocatore findById(Integer id){
        return giocatoreRepository.findById(id).orElseThrow();
    }

    public List<GiocatoreDto> findAll(){
        return giocatoreRepository.findAll().stream().map(GiocatoreConverter::toDto).toList();
    }
}
