package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.GiocatoreRosaDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.StagioneDto;
import it.fantacalcio.ffm.domain.entity.GiocatoreRosa;
import it.fantacalcio.ffm.mapper.GiocatoreRosaMapper;
import it.fantacalcio.ffm.repository.GiocatoreRosaRepository;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiocatoreRosaService {

    private final GiocatoreRosaRepository giocatoreRosaRepository;

    public GiocatoreRosaDto save(GiocatoreRosaDto giocatoreRosaDto){
        GiocatoreRosa giocatoreRosaInsertita = giocatoreRosaRepository.save(GiocatoreRosaMapper.INSTANCE.toEntity(giocatoreRosaDto));
        return GiocatoreRosaMapper.INSTANCE.toDto(giocatoreRosaInsertita);
    }

    public List<GiocatoreRosaDto> findAllByIdStagioneAndIdSquadraAndTipologiaRosa(StagioneDto idStagione, SquadraDto idSquadra, Constants.TipologiaRosaEnum tipologiaRosa){
        return giocatoreRosaRepository.findAllByIdStagioneAndIdSquadraAndTipologiaRosa(StagioneConverter.toEntity(idStagione), SquadraConverter.toEntity(idSquadra), tipologiaRosa).stream().map(GiocatoreRosaMapper.INSTANCE::toDto).toList();
    }

    public boolean existsByStagioneAndSquadraAndTipologiaRosa(StagioneDto idStagione, SquadraDto idSquadra, Constants.TipologiaRosaEnum tipologiaRosa) {
        return giocatoreRosaRepository.existsByIdStagioneAndIdSquadraAndTipologiaRosa(StagioneConverter.toEntity(idStagione), SquadraConverter.toEntity(idSquadra), tipologiaRosa);
    }

    public Optional<GiocatoreRosaDto> findByIdStagioneAndTipologiaRosa(StagioneDto idStagione, Constants.TipologiaRosaEnum tipologiaRosa){
        return giocatoreRosaRepository.findByIdStagioneAndTipologiaRosa(StagioneConverter.toEntity(idStagione), tipologiaRosa).map(GiocatoreRosaMapper.INSTANCE::toDto);
    }
}
