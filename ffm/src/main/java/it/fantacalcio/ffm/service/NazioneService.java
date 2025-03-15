package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.NazioneCache;
import it.fantacalcio.ffm.converter.NazioneConverter;
import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.entity.Nazione;
import it.fantacalcio.ffm.repository.NazioneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NazioneService {

    private final NazioneRepository nazioneRepository;
    private final NazioneCache nazioneCache;

    @Autowired
    public NazioneService(NazioneRepository nazioneRepository, NazioneCache nazioneCache){
        this.nazioneRepository = nazioneRepository;
        this.nazioneCache = nazioneCache;
    }

    @PostConstruct
    public void init() {
        List<NazioneDto> nazioni = findAll();
        nazioneCache.setNazioni(nazioni);
    }

    public Optional<NazioneDto> findById(Integer id) {
        return nazioneCache.getNazioni().stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .or(() -> nazioneRepository.findById(id).map(nazione -> {
                    NazioneDto nazioneDto = NazioneConverter.toDto(nazione);
                    nazioneCache.addNazione(nazioneDto);
                    return nazioneDto;
                }));
    }

    public Optional<NazioneDto> findBySigla(String sigla){
        return nazioneCache.getNazioni().stream()
                .filter(n -> n.getSigla().equals(sigla))
                .findFirst()
                .or(() -> nazioneRepository.findBySigla(sigla).map(nazione -> {
                    NazioneDto nazioneDto = NazioneConverter.toDto(nazione);
                    nazioneCache.addNazione(nazioneDto);
                    return nazioneDto;
                }));
    }

    public List<NazioneDto> findAll(){
        if (nazioneCache.isEmpty()) {
            List<NazioneDto> nazioneDtoList = nazioneRepository.findAll().stream()
                    .map(NazioneConverter::toDto)
                    .toList();
            nazioneCache.setNazioni(nazioneDtoList);
        }
        return nazioneCache.getNazioni();
    }

    public NazioneDto save(NazioneDto nazioneDto){
        Nazione nazioneInserita = nazioneRepository.save(NazioneConverter.toEntity(nazioneDto));
        return NazioneConverter.toDto(nazioneInserita);
    }
}
