package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CredenzialiDto;
import it.fantacalcio.ffm.domain.entity.Credenziali;
import org.springframework.stereotype.Component;

@Component
public class CredenzialiConverter {
    private CredenzialiConverter() {}

    public static CredenzialiDto toDto(Credenziali credenziali){
        return new CredenzialiDto(credenziali.getId(),
                UtenteConverter.toDto(credenziali.getIdUtente()),
                credenziali.getUserName(),
                credenziali.getPassword());
    }

    public static Credenziali toEntity(CredenzialiDto credenziali){
        Credenziali credenzialiEntity = new Credenziali();
        credenzialiEntity.setId(credenziali.getId());
        credenzialiEntity.setUserName(credenziali.getUserName());
        credenzialiEntity.setPassword(credenziali.getPassword());
        credenzialiEntity.setIdUtente(UtenteConverter.toEntity(credenziali.getIdUtente()));
        return credenzialiEntity;
    }
}
