package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.StadioConverter;
import it.fantacalcio.ffm.domain.dto.StadioDto;
import it.fantacalcio.ffm.domain.entity.Stadio;
import it.fantacalcio.ffm.repository.StadioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StadioService {
    StadioRepository stadioRepository;

    @Autowired
    public StadioService(StadioRepository stadioRepository){
        this.stadioRepository = stadioRepository;
    }

    public List<StadioDto> findAll(){
        return stadioRepository.findAll().stream().map(StadioConverter::toDto).toList();
    }

    public Optional<StadioDto> findById(Integer idStadio){
        return stadioRepository.findById(idStadio).map(StadioConverter::toDto);
    }

    public StadioDto save(StadioDto stadioDto){
        Stadio stadioInserito = stadioRepository.save(StadioConverter.toEntity(stadioDto));
        return StadioConverter.toDto(stadioInserito);
    }
}
