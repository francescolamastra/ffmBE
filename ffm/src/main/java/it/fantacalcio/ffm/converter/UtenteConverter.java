package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.domain.entity.Utente;

public class UtenteConverter {
    private UtenteConverter() {}

    public static UtenteDto toDto(Utente utenteEntity){
        return new UtenteDto(utenteEntity.getId(),
                utenteEntity.getNome(),
                utenteEntity.getCognome(),
                utenteEntity.getTelefono(),
                utenteEntity.getEmail(),
                utenteEntity.getNickname(),
                utenteEntity.getDataCreazione());
    }

    public static Utente toEntity(UtenteDto utenteDto){
        Utente utenteEntity = new Utente();
        utenteEntity.setId(utenteDto.getId());
        utenteEntity.setCognome(utenteDto.getCognome());
        utenteEntity.setNome(utenteDto.getNome());
        utenteEntity.setTelefono(utenteDto.getTelefono());
        utenteEntity.setEmail(utenteDto.getEmail());
        utenteEntity.setNickname(utenteDto.getNickname());
        return utenteEntity;
    }
}
