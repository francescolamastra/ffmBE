package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.converter.OperazioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.fantacalcio.ffm.utility.Constants.ANNI_CONTRATTO_DEFAULT;
import static it.fantacalcio.ffm.utility.Constants.SEMICOLON_SEPARATOR;
import static it.fantacalcio.ffm.utility.Constants.TipoOperazioneEnum.ACQUISTO;

@Component
public class ImportRoseApiItemProcessor implements ItemProcessor<FantalegheTeam, List<Operazione>> {

    private final ApiGatewayFacade apiGatewayFacade;

    public ImportRoseApiItemProcessor(ApiGatewayFacade apiGatewayFacade){
        this.apiGatewayFacade = apiGatewayFacade;
    }

    @Override
    public List<Operazione> process(FantalegheTeam item) throws Exception {
        if (item.getJoinedIdCalciatori().isBlank()) {
            return null; // Filtra le righe di squadre senza giocatori in rosa
        }
        List<Operazione> operazioni = new ArrayList<>();
        List<Integer> calciatoriIds = Arrays.stream(item.getJoinedIdCalciatori().split(SEMICOLON_SEPARATOR)).map(Integer::parseInt).toList();
        List<Integer> calciatoriCost = Arrays.stream(item.getJoinedCostoCalciatori().split(SEMICOLON_SEPARATOR)).map(Integer::parseInt).toList();
        if(calciatoriIds.size() != calciatoriCost.size()){
            throw new Exception("La lista calciatori e relativa lista costo acquisto non sono congruenti!");
        }
        SquadraDto squadraDto = apiGatewayFacade.squadraFromJoinedString(item.getNomeTeam());
        if(squadraDto != null) {
            int i = 0;
            for (Integer idCalciatore : calciatoriIds) {
                squadraDto.setIdFantagazzetta(item.getIdTeam());
                squadraDto = apiGatewayFacade.getSquadraByNomeOrSave(squadraDto);
                GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(idCalciatore);
                TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(ACQUISTO.getSigla());
                StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
                OperazioneDto operazioneDto = new OperazioneDto();
                operazioneDto.setIdSquadra(squadraDto);
                operazioneDto.setIdGiocatore(giocatoreDto);
                operazioneDto.setIdStagione(stagioneDto);
                operazioneDto.setIdTipoOperazione(tipoOperazioneDto);
                operazioneDto.setDataCreazione(LocalDateTime.now());
                operazioneDto.setSessioneMercato(Constants.SessioneMercatoOpAcquistoEnum.INIZIALE);
                TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto(
                        null,
                        operazioneDto,
                        calciatoriCost.get(i),
                        Constants.SegnoEnum.DEBITO.getSigla()
                );
                AcquistoDto acquistoDto = new AcquistoDto(null,
                        operazioneDto,
                        ANNI_CONTRATTO_DEFAULT);
                operazioneDto.setTransazione(transazioneOperazioneDto);
                operazioneDto.setAcquisto(acquistoDto);
                operazioni.add(OperazioneConverter.toEntity(operazioneDto));
                i++;
            }
        }
        return operazioni;
    }
}
