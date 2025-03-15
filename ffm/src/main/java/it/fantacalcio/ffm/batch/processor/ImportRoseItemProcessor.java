package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImportRoseItemProcessor implements ItemProcessor<RosaBatchRecord, RosaBatchRecord> {

    private final ApiGatewayFacade apiGatewayFacade;
    private final Pattern pattern = Pattern.compile("^(.*) (\\S+) (\\S+)$");

    public ImportRoseItemProcessor(ApiGatewayFacade apiGatewayFacade){
        this.apiGatewayFacade = apiGatewayFacade;
    }

    @Override
    public RosaBatchRecord process(RosaBatchRecord item) throws Exception {
        if ("$".equals(item.getSquadra())) {
            return null; // Filtra le righe di separazione
        }
        processString(item.getSquadra());
        return item;
    }

    private void processString(String input) {
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String nomeSquadra = matcher.group(1);
            String siglaNazione = matcher.group(2);
            String siglaCategoria = matcher.group(3);

        } else {
            System.out.println("Input non valido: " + input);
        }
    }
}
