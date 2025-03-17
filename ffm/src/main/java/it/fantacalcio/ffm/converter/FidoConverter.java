package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.FidoDto;
import it.fantacalcio.ffm.domain.entity.Fido;

public class FidoConverter {
    private FidoConverter() {}

    public static FidoDto toDto(Fido fido){
        return new FidoDto(fido.getId(),
                TrattativaConverter.toDto(fido.getIdTrattativa()),
                SquadraConverter.toDto(fido.getIdSquadra()),
                fido.getImporto(), fido.getSegnoFido());
    }

    public static Fido toEntity(FidoDto fido){
        Fido fidoEntity = new Fido();
        fidoEntity.setId(fido.getId());
        fidoEntity.setSegnoFido(fido.getSegnoFido());
        fidoEntity.setImporto(fido.getImporto());
        fidoEntity.setIdSquadra(SquadraConverter.toEntity(fido.getIdSquadra()));
        fidoEntity.setIdTrattativa(TrattativaConverter.toEntity(fido.getIdTrattativa()));
        return fidoEntity;
    }
}
