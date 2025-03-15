package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.StadioCache;
import it.fantacalcio.ffm.converter.StadioConverter;
import it.fantacalcio.ffm.domain.dto.StadioDto;
import it.fantacalcio.ffm.domain.entity.Stadio;
import it.fantacalcio.ffm.repository.StadioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StadioService {
    private final StadioRepository stadioRepository;
    private final StadioCache stadioCache;

    @Autowired
    public StadioService(StadioRepository stadioRepository,StadioCache stadioCache){
        this.stadioRepository = stadioRepository;
        this.stadioCache = stadioCache;
    }

    @PostConstruct
    public void init() {
        List<StadioDto> stadioDtoList = findAll();
        stadioCache.setStadioList(stadioDtoList);
    }

    public List<StadioDto> findAll(){
        if (stadioCache.isEmpty()) {
            List<StadioDto> stadioList = stadioRepository.findAll().stream()
                    .map(StadioConverter::toDto)
                    .toList();
            stadioCache.setStadioList(stadioList);
        }
        return stadioCache.getStadioList();
    }

    public Optional<StadioDto> findById(Integer id){
        return stadioCache.getStadioList().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> stadioRepository.findById(id).map(stadio -> {
                    StadioDto stadioDto = StadioConverter.toDto(stadio);
                    stadioCache.addStadio(stadioDto);
                    return stadioDto;
                }));
    }

    public Optional<StadioDto> findByLivello(Integer livello){
        return stadioCache.getStadioList().stream()
                .filter(c -> c.getLivello().equals(livello))
                .findFirst()
                .or(() -> stadioRepository.findByLivello(livello).map(stadio -> {
                    StadioDto stadioDto = StadioConverter.toDto(stadio);
                    stadioCache.addStadio(stadioDto);
                    return stadioDto;
                }));
    }

    public StadioDto save(StadioDto stadioDto){
        Stadio stadioInserito = stadioRepository.save(StadioConverter.toEntity(stadioDto));
        return StadioConverter.toDto(stadioInserito);
    }
}
