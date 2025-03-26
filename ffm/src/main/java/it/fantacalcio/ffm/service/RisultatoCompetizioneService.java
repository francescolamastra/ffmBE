package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.RisultatoCompetizioneCache;
import it.fantacalcio.ffm.converter.RisultatoCompetizioneConverter;
import it.fantacalcio.ffm.converter.StagioneCompetizioneConverter;
import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.dto.StagioneCompetizioneDto;
import it.fantacalcio.ffm.repository.RisultatoCompetizioneRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RisultatoCompetizioneService {
    private final RisultatoCompetizioneRepository risultatoCompetizioneRepository;
    private final RisultatoCompetizioneCache risultatoCompetizioneCache;

    public RisultatoCompetizioneService(RisultatoCompetizioneRepository risultatoCompetizioneRepository, RisultatoCompetizioneCache risultatoCompetizioneCache) {
        this.risultatoCompetizioneRepository = risultatoCompetizioneRepository;
        this.risultatoCompetizioneCache = risultatoCompetizioneCache;
    }

    public Optional<RisultatoCompetizioneDto> findById(Integer id){
        return risultatoCompetizioneRepository.findById(id).map(RisultatoCompetizioneConverter::toDto);
    }

    public boolean existsByStagioneCompetizioneAndGiornataSerieA(StagioneCompetizioneDto stagioneCompetizioneDto, Integer giornataSerieA){
        // Controlla se il risultato è presente nella cache
        return Optional.ofNullable(risultatoCompetizioneCache.existsByStagioneCompetizioneAndGiornataSerieA(stagioneCompetizioneDto, giornataSerieA))
                .orElseGet(() -> {
                    // Se non è presente nella cache, controlla nel repository
                    boolean existsInRepo = risultatoCompetizioneRepository.existsByStagioneCompetizioneAndGiornataSerieA(
                            StagioneCompetizioneConverter.toEntity(stagioneCompetizioneDto), giornataSerieA);
                    // Aggiungi il risultato alla cache
                    risultatoCompetizioneCache.addRisultatoCompetizione(stagioneCompetizioneDto, giornataSerieA, existsInRepo);
                    return existsInRepo;
                });
    }
}
