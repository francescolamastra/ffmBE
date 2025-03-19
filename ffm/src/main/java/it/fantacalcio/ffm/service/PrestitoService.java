package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.PrestitoConverter;
import it.fantacalcio.ffm.domain.dto.PrestitoDto;
import it.fantacalcio.ffm.domain.entity.Prestito;
import it.fantacalcio.ffm.repository.PrestitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestitoService {

    private final PrestitoRepository prestitoRepository;

    @Autowired
    public PrestitoService(PrestitoRepository prestitoRepository){
        this.prestitoRepository = prestitoRepository;
    }

    public PrestitoDto save(PrestitoDto prestitoDto){
        Prestito prestitoInserito = prestitoRepository.save(PrestitoConverter.toEntity(prestitoDto));
        return PrestitoConverter.toDto(prestitoInserito);
    }
}
