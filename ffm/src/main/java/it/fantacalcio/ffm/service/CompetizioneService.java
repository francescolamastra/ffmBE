package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.CompetizioneCache;
import it.fantacalcio.ffm.converter.CompetizioneConverter;
import it.fantacalcio.ffm.domain.dto.CompetizioneDto;
import it.fantacalcio.ffm.domain.entity.Competizione;
import it.fantacalcio.ffm.repository.CompetizioneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetizioneService {

    private final CompetizioneRepository competizioneRepository;
    private final CompetizioneCache competizioneCache;

    public CompetizioneService(CompetizioneRepository competizioneRepository, CompetizioneCache competizioneCache) {
        this.competizioneRepository = competizioneRepository;
        this.competizioneCache = competizioneCache;
    }

    @PostConstruct
    public void init() {
        List<CompetizioneDto> competizioni = findAll();
        competizioneCache.setListCompetizioni(competizioni);
    }

    public Optional<CompetizioneDto> findById(Integer id){
        return competizioneCache.getListCompetizioni().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> competizioneRepository.findById(id).map(competizione -> {
                    CompetizioneDto competizioneDto = CompetizioneConverter.toDto(competizione);
                    competizioneCache.addCompetizione(competizioneDto);
                    return competizioneDto;
                }));
    }

    public Optional<CompetizioneDto> findBySigla(String sigla){
        return competizioneCache.getListCompetizioni().stream()
                .filter(c -> c.getSigla().equalsIgnoreCase(sigla))
                .findFirst()
                .or(() -> competizioneRepository.findBySiglaIgnoreCase(sigla).map(competizione -> {
                    CompetizioneDto competizioneDto = CompetizioneConverter.toDto(competizione);
                    competizioneCache.addCompetizione(competizioneDto);
                    return competizioneDto;
                }));
    }

    public List<CompetizioneDto> findAll(){
        if (competizioneCache.isEmpty()) {
            List<CompetizioneDto> competizioni = competizioneRepository.findAll().stream()
                    .map(CompetizioneConverter::toDto)
                    .toList();
            competizioneCache.setListCompetizioni(competizioni);
        }
        return competizioneCache.getListCompetizioni();
    }

    public CompetizioneDto save(CompetizioneDto competizioneDto){
        Competizione competizioneInserita = competizioneRepository.save(CompetizioneConverter.toEntity(competizioneDto));
        return CompetizioneConverter.toDto(competizioneInserita);
    }
}
