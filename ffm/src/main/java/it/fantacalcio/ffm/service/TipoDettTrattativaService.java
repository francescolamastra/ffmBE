package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.TipoDettTrattativaCache;
import it.fantacalcio.ffm.converter.TipoDettTrattativaConverter;
import it.fantacalcio.ffm.domain.dto.TipoDettTrattativaDto;
import it.fantacalcio.ffm.domain.entity.TipoDettTrattativa;
import it.fantacalcio.ffm.repository.TipoDettTrattativaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoDettTrattativaService {

    private final TipoDettTrattativaRepository tipoDettTrattativaRepository;
    private final TipoDettTrattativaCache tipoDettTrattativaCache;

    public TipoDettTrattativaService(TipoDettTrattativaRepository tipoDettTrattativaRepository, TipoDettTrattativaCache tipoDettTrattativaCache) {
        this.tipoDettTrattativaRepository = tipoDettTrattativaRepository;
        this.tipoDettTrattativaCache = tipoDettTrattativaCache;
    }

    @PostConstruct
    public void init() {
        List<TipoDettTrattativaDto> tipoDettTrattativaList = findAll();
        tipoDettTrattativaCache.setTipoDettTrattativaList(tipoDettTrattativaList);
    }

    public Optional<TipoDettTrattativaDto> findById(Integer id){
        return tipoDettTrattativaCache.getTipoDettTrattativaList().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> tipoDettTrattativaRepository.findById(id).map(tipoDettTrattativa -> {
                    TipoDettTrattativaDto tipoDettTrattativaDto = TipoDettTrattativaConverter.toDto(tipoDettTrattativa);
                    tipoDettTrattativaCache.addTipoDettTrattativa(tipoDettTrattativaDto);
                    return tipoDettTrattativaDto;
                }));
    }

    public Optional<TipoDettTrattativaDto> findBySigla(String sigla){
        return tipoDettTrattativaCache.getTipoDettTrattativaList().stream()
                .filter(c -> c.getSigla().equalsIgnoreCase(sigla))
                .findFirst()
                .or(() -> tipoDettTrattativaRepository.findBySiglaIgnoreCase(sigla).map(tipoDettTrattativa -> {
                    TipoDettTrattativaDto tipoDettTrattativaDto = TipoDettTrattativaConverter.toDto(tipoDettTrattativa);
                    tipoDettTrattativaCache.addTipoDettTrattativa(tipoDettTrattativaDto);
                    return tipoDettTrattativaDto;
                }));
    }

    public List<TipoDettTrattativaDto> findAll(){
        if (tipoDettTrattativaCache.isEmpty()) {
            List<TipoDettTrattativaDto> tipoDettTrattativaList = tipoDettTrattativaRepository.findAll().stream()
                    .map(TipoDettTrattativaConverter::toDto)
                    .toList();
            tipoDettTrattativaCache.setTipoDettTrattativaList(tipoDettTrattativaList);
        }
        return tipoDettTrattativaCache.getTipoDettTrattativaList();
    }

    public TipoDettTrattativaDto save(TipoDettTrattativaDto tipoDettTrattativaDto){
        TipoDettTrattativa tipoDettTrattativaInserita = tipoDettTrattativaRepository.save(TipoDettTrattativaConverter.toEntity(tipoDettTrattativaDto));
        return TipoDettTrattativaConverter.toDto(tipoDettTrattativaInserita);
    }
}
