package it.fantacalcio.ffm.proxy;

import it.fantacalcio.ffm.service.GiocatoreService;
import it.fantacalcio.ffm.service.NazioneService;
import org.springframework.stereotype.Component;

@Component
public class ApiGatewayProxy {

        private GiocatoreService giocatoreService;
        private NazioneService nazioneService;
}
