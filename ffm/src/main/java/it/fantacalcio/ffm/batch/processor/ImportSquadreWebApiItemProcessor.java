package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.entity.Squadra;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportSquadreWebApiItemProcessor implements ItemProcessor<FantalegheTeam, Squadra> {

    private final ApiGatewayFacade apiGatewayFacade;

    @Override
    public Squadra process(FantalegheTeam item) throws Exception {
        if (item.getJoinedIdCalciatori().isBlank()) {
            return null; // Filtra le righe di squadre senza giocatori in rosa
        }
        SquadraDto squadraDto = apiGatewayFacade.squadraFromJoinedString(item.getNomeTeam());
        if(squadraDto != null) {
                squadraDto.setIdFantagazzetta(item.getIdTeam());
                if(apiGatewayFacade.getSquadraByIdFantagazzetta(item.getIdTeam()) != null) return null;
        }
        return SquadraConverter.toEntity(squadraDto);
    }
}
