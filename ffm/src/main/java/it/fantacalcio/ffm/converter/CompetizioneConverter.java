package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CompetizioneDto;
import it.fantacalcio.ffm.domain.entity.Competizione;
import org.springframework.stereotype.Component;

@Component
public class CompetizioneConverter {
    private CompetizioneConverter() {}

    public static CompetizioneDto toDto(Competizione competizione){
        return new CompetizioneDto(competizione.getId(), competizione.getIdFantagazzetta(), competizione.getNome(),competizione.getRuolo(), competizione.getQuotazione());
    }

//    public static Competizione toEntity(CompetizioneDto competizione){
//        return new Competizione(competizione.getId(), competizione.getIdFantagazzetta(), competizione.getNome(),competizione.getRuolo(), competizione.getQuotazione());
//    }
}
