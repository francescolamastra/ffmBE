package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.StagioneCache;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.repository.StagioneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StagioneService {

    private final StagioneRepository stagioneRepository;
    private final StagioneCache stagioneCache;

    public StagioneService(StagioneRepository stagioneRepository, StagioneCache stagioneCache){
        this.stagioneRepository = stagioneRepository;
        this.stagioneCache = stagioneCache;
    }

    @PostConstruct
    public void init() {
        List<StagioneDto> stagioneDtoList = findAll();
        stagioneCache.setStagioni(stagioneDtoList);
    }

    public StagioneDto save(StagioneDto stagioneDto){
        Stagione stagioneInserita = stagioneRepository.save(StagioneConverter.toEntity(stagioneDto));
        StagioneDto stagioneInseritaDto = StagioneConverter.toDto(stagioneInserita);
        stagioneCache.addStagione(stagioneInseritaDto);
        return stagioneInseritaDto;
    }

    public List<StagioneDto> findAll(){
        if (stagioneCache.isEmpty()) {
            List<StagioneDto> stagioneDtoList = stagioneRepository.findAll().stream()
                    .map(StagioneConverter::toDto)
                    .toList();
            stagioneCache.setStagioni(stagioneDtoList);
        }
        return stagioneCache.getStagioni();
    }

    public Optional<StagioneDto> getLastStagione(){
        if (stagioneCache.isEmpty()) {
            stagioneRepository.findFirstByOrderByAnnoFineDesc()
                    .map(stagione -> {
                        StagioneDto stagioneDto = StagioneConverter.toDto(stagione);
                        stagioneCache.addStagione(stagioneDto);
                        return stagioneDto;
                    });
        }
        return stagioneCache.getStagioni().stream().max(Comparator.comparing(StagioneDto::getAnnoFine));
    }
}
