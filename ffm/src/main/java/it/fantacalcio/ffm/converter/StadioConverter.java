package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.StadioDto;
import it.fantacalcio.ffm.domain.entity.Stadio;
import org.springframework.stereotype.Component;

@Component
public class StadioConverter {
    private StadioConverter() {}

    public static StadioDto toDto(Stadio stadio){
        return new StadioDto(stadio.getId(),
                stadio.getLivello(),
                stadio.getCoefficiente(),
                stadio.getCompensoVittoria(),
                stadio.getCompensoPareggio(),
                stadio.getBonus(),
                stadio.getManutenzione());
    }

    public static Stadio toEntity(StadioDto stadioDto){
        Stadio stadioEntity = new Stadio();
        stadioEntity.setId(stadioDto.getId());
        stadioEntity.setLivello(stadioDto.getLivello());
        stadioEntity.setBonus(stadioDto.getBonus());
        stadioEntity.setCoefficiente(stadioDto.getCoefficiente());
        stadioEntity.setManutenzione(stadioDto.getManutenzione());
        stadioEntity.setCompensoPareggio(stadioDto.getCompensoPareggio());
        stadioEntity.setCompensoVittoria(stadioDto.getCompensoVittoria());
        return stadioEntity;
    }
}
