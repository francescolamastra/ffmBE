package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.RisultatoCompetizioneDto;
import it.fantacalcio.ffm.domain.entity.RisultatoCompetizione;
import org.springframework.stereotype.Component;

@Component
public class RisultatoCompetizioneConverter {
    private RisultatoCompetizioneConverter() {}

    public static RisultatoCompetizioneDto toDto(RisultatoCompetizione risultatoCompetizione){
        return new RisultatoCompetizioneDto(risultatoCompetizione.getId(),
                StagioneCompetizioneConverter.toDto(risultatoCompetizione.getIdStagioneCompetizione()),
                SquadraConverter.toDto(risultatoCompetizione.getIdSquadra()),
                risultatoCompetizione.getRisultato(),
                risultatoCompetizione.getGiornataSerieA(),
                risultatoCompetizione.getGiornataCompetizione(),
                risultatoCompetizione.getLuogoRisultato(),
                FaseCompetizioneConverter.toDto(risultatoCompetizione.getIdFaseCompetizione()));
    }

    public static RisultatoCompetizione toEntity(RisultatoCompetizioneDto risultatoCompetizione){
        RisultatoCompetizione risultatoCompetizioneEntity = new RisultatoCompetizione();
        risultatoCompetizioneEntity.setId(risultatoCompetizione.getId());
        risultatoCompetizioneEntity.setRisultato(risultatoCompetizione.getRisultato());
        risultatoCompetizioneEntity.setGiornataCompetizione(risultatoCompetizione.getGiornataCompetizione());
        risultatoCompetizioneEntity.setIdFaseCompetizione(FaseCompetizioneConverter.toEntity(risultatoCompetizione.getIdFaseCompetizione()));
        risultatoCompetizioneEntity.setLuogoRisultato(risultatoCompetizione.getLuogoRisultato());
        risultatoCompetizioneEntity.setGiornataCompetizione(risultatoCompetizione.getGiornataCompetizione());
        risultatoCompetizioneEntity.setIdStagioneCompetizione(StagioneCompetizioneConverter.toEntity(risultatoCompetizione.getIdStagioneCompetizione()));
        risultatoCompetizioneEntity.setGiornataSerieA(risultatoCompetizione.getGiornataSerieA());
        return risultatoCompetizioneEntity;
    }
}
