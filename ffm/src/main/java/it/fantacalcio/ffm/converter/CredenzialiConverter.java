package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CredenzialiDto;
import it.fantacalcio.ffm.domain.entity.Credenziali;
import org.springframework.stereotype.Component;

@Component
public class CredenzialiConverter {
    private CredenzialiConverter() {}

    public static CredenzialiDto toDto(Credenziali credenziali){
        return new CredenzialiDto(credenziali.getId(), credenziali.getIdFantagazzetta(), credenziali.getNome(),credenziali.getRuolo(), credenziali.getQuotazione());
    }

//    public static Credenziali toEntity(CredenzialiDto credenziali){
//        return new Credenziali(credenziali.getId(), credenziali.getIdFantagazzetta(), credenziali.getNome(),credenziali.getRuolo(), credenziali.getQuotazione());
//    }
}
