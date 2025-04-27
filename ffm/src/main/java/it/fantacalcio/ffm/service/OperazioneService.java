package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.*;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.repository.OperazioneRepository;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperazioneService {

    private final OperazioneRepository operazioneRepository;

    @Autowired
    public OperazioneService(OperazioneRepository operazioneRepository){
        this.operazioneRepository = operazioneRepository;
    }

    public OperazioneDto save(OperazioneDto operazioneDto){
        Operazione operazioneInsertita = operazioneRepository.save(OperazioneConverter.toEntity(operazioneDto));
        return OperazioneConverter.toDto(operazioneInsertita);
    }

    public boolean existsByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(StagioneDto stagione, SquadraDto squadra, GiocatoreDto giocatore, TipoOperazioneDto tipoOperazione, List<Constants.SessioneMercatoOpAcquistoEnum> sessioneMercato){
        return operazioneRepository.existsByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(StagioneConverter.toEntity(stagione), SquadraConverter.toEntity(squadra), GiocatoreConverter.toEntity(giocatore), TipoOperazioneConverter.toEntity(tipoOperazione), sessioneMercato);
    }
}
