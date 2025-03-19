package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.DettaglioTrattativaConverter;
import it.fantacalcio.ffm.domain.dto.DettaglioTrattativaDto;
import it.fantacalcio.ffm.domain.entity.DettaglioTrattativa;
import it.fantacalcio.ffm.repository.DettaglioTrattativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DettaglioTrattativaService {

    private final DettaglioTrattativaRepository dettaglioTrattativaRepository;

    @Autowired
    public DettaglioTrattativaService(DettaglioTrattativaRepository dettaglioTrattativaRepository){
        this.dettaglioTrattativaRepository = dettaglioTrattativaRepository;
    }

    public DettaglioTrattativaDto save(DettaglioTrattativaDto dettaglioTrattativaDto){
        DettaglioTrattativa dettaglioTrattativaInsertita = dettaglioTrattativaRepository.save(DettaglioTrattativaConverter.toEntity(dettaglioTrattativaDto));
        return DettaglioTrattativaConverter.toDto(dettaglioTrattativaInsertita);
    }
}
