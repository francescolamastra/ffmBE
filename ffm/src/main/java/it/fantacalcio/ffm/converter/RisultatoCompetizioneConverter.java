package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;

public class RisultatoCompetizioneConverter {
    private RisultatoCompetizioneConverter() {}

    public static RisultatoCompetizioneDto toDto(RisultatoCompetizione risultatoCompetizione){
        return new RisultatoCompetizioneDto(risultatoCompetizione.getId(),
                StagioneCompetizioneConverter.toDto(risultatoCompetizione.getStagioneCompetizione()),
                SquadraConverter.toDto(risultatoCompetizione.getSquadra()),
                risultatoCompetizione.getRisultato(),
                risultatoCompetizione.getGiornataSerieA(),
                risultatoCompetizione.getGiornataCompetizione(),
                risultatoCompetizione.getLuogoRisultato(),
                FaseCompetizioneConverter.toDto(risultatoCompetizione.getFaseCompetizione()),
                risultatoCompetizione.getBonusPunti());
    }

    public static RisultatoCompetizione toEntity(RisultatoCompetizioneDto risultatoCompetizione){
        RisultatoCompetizione risultatoCompetizioneEntity = new RisultatoCompetizione();
        risultatoCompetizioneEntity.setId(risultatoCompetizione.getId());
        risultatoCompetizioneEntity.setStagioneCompetizione(StagioneCompetizioneConverter.toEntity(risultatoCompetizione.getStagioneCompetizione()));
        risultatoCompetizioneEntity.setSquadra(SquadraConverter.toEntity(risultatoCompetizione.getSquadra()));
        risultatoCompetizioneEntity.setRisultato(risultatoCompetizione.getRisultato());
        risultatoCompetizioneEntity.setGiornataSerieA(risultatoCompetizione.getGiornataSerieA());
        risultatoCompetizioneEntity.setGiornataCompetizione(risultatoCompetizione.getGiornataCompetizione());
        risultatoCompetizioneEntity.setLuogoRisultato(risultatoCompetizione.getLuogoRisultato());
        risultatoCompetizioneEntity.setFaseCompetizione(FaseCompetizioneConverter.toEntity(risultatoCompetizione.getFaseCompetizione()));
        risultatoCompetizioneEntity.setBonusPunti(risultatoCompetizione.getBonusPunti());
        return risultatoCompetizioneEntity;
    }
}
