package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.SituazioneEconomicaInizialeDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.SituazioneEconomicaIniziale;
import it.fantacalcio.ffm.mapper.SituazioneEconomicaInizialeMapper;
import it.fantacalcio.ffm.repository.SituazioneEconomicaInizialeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SituazioneEconomicaInizialeService {

    private final SituazioneEconomicaInizialeRepository situazioneEconomicaInizialeRepository;

    public SituazioneEconomicaInizialeDto save(SituazioneEconomicaInizialeDto situazioneEconomicaInizialeDto){
        SituazioneEconomicaIniziale situazioneEconomicaInizialeInsertita = situazioneEconomicaInizialeRepository.save(SituazioneEconomicaInizialeMapper.INSTANCE.toEntity(situazioneEconomicaInizialeDto));
        return SituazioneEconomicaInizialeMapper.INSTANCE.toDto(situazioneEconomicaInizialeInsertita);
    }

    public SituazioneEconomicaInizialeDto getSituazioneEconomicaInizialeBySquadraAndStagione(StagioneDto stagioneDto, SquadraDto squadraDto){
        return situazioneEconomicaInizialeRepository.findBySquadraAndStagione(SquadraConverter.toEntity(squadraDto), StagioneConverter.toEntity(stagioneDto));
    }
}
