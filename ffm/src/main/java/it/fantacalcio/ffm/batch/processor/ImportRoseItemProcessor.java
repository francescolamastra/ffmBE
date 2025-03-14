package it.fantacalcio.ffm.batch.processor;

import it.fantacalcio.ffm.batch.model.RosaBatchRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImportRoseItemProcessor implements ItemProcessor<RosaBatchRecord, RosaBatchRecord> {

    @Override
    public RosaBatchRecord process(RosaBatchRecord item) throws Exception {
        if ("$".equals(item.getSquadra())) {
            return null; // Filtra le righe di separazione
        }
        processString(item.getSquadra());
        return item;
    }

    private void processString(String input) {
        Pattern pattern = Pattern.compile("^(.*) (\\S+) (\\S+)$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            String nomeSquadra = matcher.group(1);
            String siglaNazione = matcher.group(2);
            String siglaCategoria = matcher.group(3);

            System.out.println("nomeSquadra: " + nomeSquadra);
            System.out.println("siglaNazione: " + siglaNazione);
            System.out.println("siglaCategoria: " + siglaCategoria);
        } else {
            System.out.println("Input non valido: " + input);
        }
    }
}
