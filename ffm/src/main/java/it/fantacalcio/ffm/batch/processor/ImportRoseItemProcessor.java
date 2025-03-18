package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.converter.*;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Operazione;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static it.fantacalcio.ffm.utility.Constants.ANNI_CONTRATTO_DEFAULT;
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
            OperazioneDto operazioneDto = new OperazioneDto();
            operazioneDto.setIdSquadra(squadraDto);
            operazioneDto.setIdGiocatore(giocatoreDto);
            operazioneDto.setIdStagione(stagioneDto);
            operazioneDto.setIdTipoOperazione(tipoOperazioneDto);
            operazioneDto.setDataCreazione(LocalDateTime.now());
            TransazioneOperazioneDto transazioneOperazioneDto = new TransazioneOperazioneDto(
                    null,
                    operazioneDto,
                    Integer.valueOf(item.getCostoAcquisto()),
                    Constants.Segno.DEBITO.getSigla()
            );
            AcquistoDto acquistoDto = new AcquistoDto(null,
                    operazioneDto,
                    ANNI_CONTRATTO_DEFAULT);
            operazioneDto.setTransazione(transazioneOperazioneDto);
            operazioneDto.setAcquisto(acquistoDto);
            return OperazioneConverter.toEntity(operazioneDto);
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
