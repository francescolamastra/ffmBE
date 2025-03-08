package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.repository.StagioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StagioneService {

    private final StagioneRepository stagioneRepository;

    @Autowired
    public StagioneService(StagioneRepository stagioneRepository){
        this.stagioneRepository = stagioneRepository;
    }

    public StagioneDto save(StagioneDto stagioneDto){
        Stagione stagioneInserita = stagioneRepository.save(StagioneConverter.toEntity(stagioneDto));
        return StagioneConverter.toDto(stagioneInserita);
    }

    public List<StagioneDto> findAll(){
        return stagioneRepository.findAll().stream().map(StagioneConverter::toDto).toList();
    }
}
