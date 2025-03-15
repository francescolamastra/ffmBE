package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.TipoOperazioneCache;
import it.fantacalcio.ffm.converter.TipoOperazioneConverter;
import it.fantacalcio.ffm.domain.dto.TipoOperazioneDto;
import it.fantacalcio.ffm.domain.entity.TipoOperazione;
import it.fantacalcio.ffm.repository.TipoOperazioneRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoOperazioneService {

    private final TipoOperazioneRepository tipoOperazioneRepository;
    private final TipoOperazioneCache tipoOperazioneCache;

    public TipoOperazioneService(TipoOperazioneRepository tipoOperazioneRepository, TipoOperazioneCache tipoOperazioneCache) {
        this.tipoOperazioneRepository = tipoOperazioneRepository;
        this.tipoOperazioneCache = tipoOperazioneCache;
    }

    @PostConstruct
    public void init() {
        List<TipoOperazioneDto> tipiOperazione = findAll();
        tipoOperazioneCache.setTipiOperazione(tipiOperazione);
    }

    public Optional<TipoOperazioneDto> findById(Integer id){
        return tipoOperazioneCache.getTipiOperazione().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> tipoOperazioneRepository.findById(id).map(tipoOperazione -> {
                    TipoOperazioneDto tipoOperazioneDto = TipoOperazioneConverter.toDto(tipoOperazione);
                    tipoOperazioneCache.addTipoOperazione(tipoOperazioneDto);
                    return tipoOperazioneDto;
                }));
    }

    public Optional<TipoOperazioneDto> findBySigla(String sigla){
        return tipoOperazioneCache.getTipiOperazione().stream()
                .filter(c -> c.getSigla().equals(sigla))
                .findFirst()
                .or(() -> tipoOperazioneRepository.findBySigla(sigla).map(tipoOperazione -> {
                    TipoOperazioneDto tipoOperazioneDto = TipoOperazioneConverter.toDto(tipoOperazione);
                    tipoOperazioneCache.addTipoOperazione(tipoOperazioneDto);
                    return tipoOperazioneDto;
                }));
    }

    public List<TipoOperazioneDto> findAll(){
        if (tipoOperazioneCache.isEmpty()) {
            List<TipoOperazioneDto> tipiOperazione = tipoOperazioneRepository.findAll().stream()
                    .map(TipoOperazioneConverter::toDto)
                    .toList();
            tipoOperazioneCache.setTipiOperazione(tipiOperazione);
        }
        return tipoOperazioneCache.getTipiOperazione();
    }

    public TipoOperazioneDto save(TipoOperazioneDto tipoOperazioneDto){
        TipoOperazione tipoOperazioneInserita = tipoOperazioneRepository.save(TipoOperazioneConverter.toEntity(tipoOperazioneDto));
        return TipoOperazioneConverter.toDto(tipoOperazioneInserita);
    }
}
