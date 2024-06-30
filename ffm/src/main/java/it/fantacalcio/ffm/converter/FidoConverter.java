package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FidoDto;
import it.fantacalcio.ffm.domain.entity.Fido;
import org.springframework.stereotype.Component;

@Component
public class FidoConverter {
    private FidoConverter() {}

    public static FidoDto toDto(Fido fido){
        return new FidoDto(fido.getId(), fido.getIdFantagazzetta(), fido.getNome(),fido.getRuolo(), fido.getQuotazione());
    }

//    public static Fido toEntity(FidoDto fido){
//        return new Fido(fido.getId(), fido.getIdFantagazzetta(), fido.getNome(),fido.getRuolo(), fido.getQuotazione());
//    }
}
