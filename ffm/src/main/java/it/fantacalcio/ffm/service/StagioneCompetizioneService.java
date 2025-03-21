package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.CompetizioneConverter;
import it.fantacalcio.ffm.converter.StagioneCompetizioneConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.CompetizioneDto;
import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import it.fantacalcio.ffm.repository.StagioneCompetizioneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StagioneCompetizioneService {

    private final StagioneCompetizioneRepository stagioneCompetizioneRepository;

    public StagioneCompetizioneService(StagioneCompetizioneRepository stagioneCompetizioneRepository) {
        this.stagioneCompetizioneRepository = stagioneCompetizioneRepository;
    }

    public Optional<StagioneCompetizioneDto> findById(Integer id){
        return stagioneCompetizioneRepository.findById(id).map(StagioneCompetizioneConverter::toDto);
    }

    public Optional<StagioneCompetizioneDto> findByStagioneAndCompetizione(StagioneDto stagioneDto, CompetizioneDto competizioneDto){
        return stagioneCompetizioneRepository.findByIdStagioneAndIdCompetizione(StagioneConverter.toEntity(stagioneDto), CompetizioneConverter.toEntity(competizioneDto)).map(StagioneCompetizioneConverter::toDto);
    }

    public List<StagioneCompetizioneDto> findAllByStagione(StagioneDto stagioneDto){
        return stagioneCompetizioneRepository.findAllByIdStagione(StagioneConverter.toEntity(stagioneDto));
    }

    public StagioneCompetizioneDto save(StagioneCompetizioneDto stagioneCompetizioneDto){
        StagioneCompetizione stagioneCompetizioneInserita = stagioneCompetizioneRepository.save(StagioneCompetizioneConverter.toEntity(stagioneCompetizioneDto));
        return StagioneCompetizioneConverter.toDto(stagioneCompetizioneInserita);
    }

    public StagioneCompetizioneDto save(StagioneCompetizione stagioneCompetizione){
        StagioneCompetizione stagioneCompetizioneInserita = stagioneCompetizioneRepository.save(stagioneCompetizione);
        return StagioneCompetizioneConverter.toDto(stagioneCompetizioneInserita);
    }
}
