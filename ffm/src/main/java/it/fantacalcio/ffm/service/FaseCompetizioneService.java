package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.FaseCompetizioneCache;
import it.fantacalcio.ffm.converter.FaseCompetizioneConverter;
import it.fantacalcio.ffm.domain.dto.FaseCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.FaseCompetizione;
import it.fantacalcio.ffm.repository.FaseCompetizioneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaseCompetizioneService {

    private final FaseCompetizioneRepository faseCompetizioneRepository;
    private final FaseCompetizioneCache faseCompetizioneCache;

    public FaseCompetizioneService(FaseCompetizioneRepository faseCompetizioneRepository, FaseCompetizioneCache faseCompetizioneCache) {
        this.faseCompetizioneRepository = faseCompetizioneRepository;
        this.faseCompetizioneCache = faseCompetizioneCache;
    }

    @PostConstruct
    public void init() {
        List<FaseCompetizioneDto> faseCompetizioneList = findAll();
        faseCompetizioneCache.setFaseCompetizioneList(faseCompetizioneList);
    }

    public Optional<FaseCompetizioneDto> findById(Integer id){
        return faseCompetizioneCache.getFaseCompetizioneList().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> faseCompetizioneRepository.findById(id).map(faseCompetizione -> {
                    FaseCompetizioneDto faseCompetizioneDto = FaseCompetizioneConverter.toDto(faseCompetizione);
                    faseCompetizioneCache.addFaseCompetizione(faseCompetizioneDto);
                    return faseCompetizioneDto;
                }));
    }

    public Optional<FaseCompetizioneDto> findBySigla(String sigla){
        return faseCompetizioneCache.getFaseCompetizioneList().stream()
                .filter(c -> c.getSigla().equalsIgnoreCase(sigla))
                .findFirst()
                .or(() -> faseCompetizioneRepository.findBySiglaIgnoreCase(sigla).map(faseCompetizione -> {
                    FaseCompetizioneDto faseCompetizioneDto = FaseCompetizioneConverter.toDto(faseCompetizione);
                    faseCompetizioneCache.addFaseCompetizione(faseCompetizioneDto);
                    return faseCompetizioneDto;
                }));
    }

    public List<FaseCompetizioneDto> findAll(){
        if (faseCompetizioneCache.isEmpty()) {
            List<FaseCompetizioneDto> faseCompetizioneList = faseCompetizioneRepository.findAll().stream()
                    .map(FaseCompetizioneConverter::toDto)
                    .toList();
            faseCompetizioneCache.setFaseCompetizioneList(faseCompetizioneList);
        }
        return faseCompetizioneCache.getFaseCompetizioneList();
    }

    public FaseCompetizioneDto save(FaseCompetizioneDto faseCompetizioneDto){
        FaseCompetizione faseCompetizioneInserita = faseCompetizioneRepository.save(FaseCompetizioneConverter.toEntity(faseCompetizioneDto));
        return FaseCompetizioneConverter.toDto(faseCompetizioneInserita);
    }
}
