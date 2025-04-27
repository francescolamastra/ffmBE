package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.TrattativaConverter;
import it.fantacalcio.ffm.domain.dto.TrattativaDto;
import it.fantacalcio.ffm.domain.entity.Trattativa;
import it.fantacalcio.ffm.repository.TrattativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrattativaService {

    private final TrattativaRepository trattativaRepository;

    @Autowired
    public TrattativaService(TrattativaRepository trattativaRepository){
        this.trattativaRepository = trattativaRepository;
    }

    public TrattativaDto save(TrattativaDto trattativaDto){
        Trattativa trattativaInsertita = trattativaRepository.save(TrattativaConverter.toEntity(trattativaDto));
        return TrattativaConverter.toDto(trattativaInsertita);
    }

    public Optional<TrattativaDto> findTrattativaById(Integer idTrattativa) {
        return trattativaRepository.findById(idTrattativa).map(TrattativaConverter::toDto);
    }
}
