package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.GettoneConverter;
import it.fantacalcio.ffm.domain.dto.GettoneDto;
import it.fantacalcio.ffm.domain.entity.Gettone;
import it.fantacalcio.ffm.repository.GettoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GettoneService {

    private final GettoneRepository gettoneRepository;

    public GettoneDto save(GettoneDto gettoneDto){
        Gettone gettoneInserito = gettoneRepository.save(GettoneConverter.toEntity(gettoneDto));
        return GettoneConverter.toDto(gettoneInserito);
    }
}
