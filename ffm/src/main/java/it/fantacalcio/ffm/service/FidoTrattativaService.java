package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.FidoConverter;
import it.fantacalcio.ffm.domain.dto.FidoDto;
import it.fantacalcio.ffm.domain.entity.Fido;
import it.fantacalcio.ffm.repository.FidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FidoTrattativaService {

    private final FidoRepository fidoRepository;

    @Autowired
    public FidoTrattativaService(FidoRepository fidoRepository){
        this.fidoRepository = fidoRepository;
    }

    public FidoDto save(FidoDto fidoDto){
        Fido fidoInserito = fidoRepository.save(FidoConverter.toEntity(fidoDto));
        return FidoConverter.toDto(fidoInserito);
    }
}
