package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.PrestitoDto;
import it.fantacalcio.ffm.domain.entity.Prestito;
import org.springframework.stereotype.Component;

@Component
public class PrestitoConverter {
    private PrestitoConverter() {}

    public static PrestitoDto toDto(Prestito prestito){
        return new PrestitoDto(prestito.getId(), prestito.getIdFantagazzetta(), prestito.getNome(),prestito.getRuolo(), prestito.getQuotazione());
    }

//    public static Prestito toEntity(PrestitoDto prestito){
//        return new Prestito(prestito.getId(), prestito.getIdFantagazzetta(), prestito.getNome(),prestito.getRuolo(), prestito.getQuotazione());
//    }
}
