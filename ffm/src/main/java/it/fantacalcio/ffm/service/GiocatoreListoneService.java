package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.GiocatoreListoneConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.GiocatoreListoneDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.GiocatoreListone;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.repository.GiocatoreListoneRepository;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiocatoreListoneService {

    private final GiocatoreListoneRepository giocatoreListoneRepository;

    public GiocatoreListoneDto save(GiocatoreListoneDto giocatoreListoneDto){
        GiocatoreListone giocatoreListoneInserito = giocatoreListoneRepository.save(GiocatoreListoneConverter.toEntity(giocatoreListoneDto));
        return GiocatoreListoneConverter.toDto(giocatoreListoneInserito);
    }

    public Optional<GiocatoreListoneDto> findByIdStagioneAndIdFantagazzettaAndTipologiaListone(StagioneDto idStagione, Integer idFantagazzetta, Constants.TipologiaListoneEnum tipologiaListoneEnum){
        return giocatoreListoneRepository.findByIdStagioneAndIdFantagazzettaAndTipologiaListone(StagioneConverter.toEntity(idStagione), idFantagazzetta, tipologiaListoneEnum).map(GiocatoreListoneConverter::toDto);
    }

    public boolean existsByStagioneAndTipologiaListoneAndIdFantagazzetta(Integer idFantagazzetta, StagioneDto stagione, Constants.TipologiaListoneEnum tipologiaListone) {
        return giocatoreListoneRepository.existsByIdFantagazzettaAndIdStagioneAndTipologiaListone(idFantagazzetta, StagioneConverter.toEntity(stagione), tipologiaListone);
    }
}
