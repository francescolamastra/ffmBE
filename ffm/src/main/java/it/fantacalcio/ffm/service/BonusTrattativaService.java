package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.BonusTrattativaConverter;
import it.fantacalcio.ffm.domain.dto.BonusTrattativaDto;
import it.fantacalcio.ffm.domain.entity.BonusTrattativa;
import it.fantacalcio.ffm.repository.BonusTrattativaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BonusTrattativaService {

    private final BonusTrattativaRepository bonusTrattativaRepository;

    public BonusTrattativaDto save(BonusTrattativaDto bonusTrattativaDto){
        BonusTrattativa bonusInserito = bonusTrattativaRepository.save(BonusTrattativaConverter.toEntity(bonusTrattativaDto));
        return BonusTrattativaConverter.toDto(bonusInserito);
    }
}
