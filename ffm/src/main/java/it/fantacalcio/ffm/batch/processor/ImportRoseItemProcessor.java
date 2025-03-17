package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.cache.TipoOperazioneCache;
import it.fantacalcio.ffm.converter.GiocatoreConverter;
import it.fantacalcio.ffm.converter.SquadraConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.converter.TipoOperazioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.fantacalcio.ffm.utility.Constants.TipoOperazione.ACQUISTO;

@Component
public class ImportRoseItemProcessor implements ItemProcessor<RosaBatchRecord, Operazione> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Pattern pattern = Pattern.compile("^(.*) (\\S+) (\\S+)$");

    public ImportRoseItemProcessor(ApiGatewayFacade apiGatewayFacade){
        this.apiGatewayFacade = apiGatewayFacade;
    }

    @Override
    public Operazione process(RosaBatchRecord item) throws Exception {
        if ("$".equals(item.getSquadraNazioneCategoria())) {
            return null; // Filtra le righe di separazione
        }
        SquadraDto squadraDto = squadraFromJoinedString(item.getSquadraNazioneCategoria());
        if(squadraDto != null) {
            GiocatoreDto giocatoreDto = apiGatewayFacade.getGiocatoreByIdFantagazzetta(Integer.valueOf(item.getIdFantagazzetta()));
            TipoOperazioneDto tipoOperazioneDto = apiGatewayFacade.getTipoOperazioneBySigla(ACQUISTO.getSigla());
            StagioneDto stagioneDto = apiGatewayFacade.getLastStagione();
            return new Operazione(null,
                    SquadraConverter.toEntity(squadraDto),
                    GiocatoreConverter.toEntity(giocatoreDto),
                    TipoOperazioneConverter.toEntity(tipoOperazioneDto),
                    StagioneConverter.toEntity(stagioneDto),
                    LocalDateTime.now());
        }else{
            return null;
        }
    }

    private SquadraDto squadraFromJoinedString(String squadraJoinedString) {
        Matcher matcher = pattern.matcher(squadraJoinedString);

        if (matcher.matches()) {
            String nomeSquadra = matcher.group(1);
            String siglaNazione = matcher.group(2);
            String siglaCategoria = matcher.group(3);
            CategoriaDto categoriaDto = apiGatewayFacade.getCategoriaBySigla(siglaCategoria);
            NazioneDto nazioneDto = apiGatewayFacade.getNazioneBySigla(siglaNazione);
            SquadraDto squadraDto =  new SquadraDtoBuilder()
                    .setNome(nomeSquadra)
                    .setIdNazione(nazioneDto)
                    .setIdCategoria(categoriaDto)
                    .build();
            return apiGatewayFacade.getSquadraByNome(squadraDto);
        } else {
            System.out.println("squadraFromJoinedString Input non valido: " + squadraJoinedString);
            return null;
        }
    }
}
