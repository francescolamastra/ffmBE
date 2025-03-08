package it.fantacalcio.ffm.proxy;

import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiGatewayProxy {

        private GiocatoreService giocatoreService;
        private NazioneService nazioneService;
        private UtenteService utenteService;
        private SquadraService squadraService;
        private StagioneService stagioneService;
        private OperazioneService operazioneService;

        /* INIZIO METODI SETTER PER INJECTION */
        @Autowired
        public void setGiocatoreService(GiocatoreService giocatoreService) {
                this.giocatoreService = giocatoreService;
        }

        @Autowired
        public void setNazioneService(NazioneService nazioneService) {
                this.nazioneService = nazioneService;
        }

        @Autowired
        public void setUtenteService(UtenteService utenteService) {
                this.utenteService = utenteService;
        }

        @Autowired
        public void setSquadraService(SquadraService squadraService) {
                this.squadraService = squadraService;
        }

        @Autowired
        public void setStagioneService(StagioneService stagioneService) {
                this.stagioneService = stagioneService;
        }

        @Autowired
        public void setStagioneService(OperazioneService operazioneService) {
                this.operazioneService = operazioneService;
        }
        /* FINE METODI SETTER PER INJECTION */

        /* METODI DI DOMINIO */
        public List<GiocatoreDto> getGiocatori() {
                return giocatoreService.findAll();
        }

        public GiocatoreDto getGiocatore(Integer idFantagazzetta) {
                return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
        }

        public UtenteDto createUtente(UtenteDto utenteDto) {
                return utenteService.save(utenteDto);
        }

        public SquadraDto createSquadra(SquadraDto squadraDto, Integer utenteId) {
                if (utenteId != null) {
                        return squadraService.save(squadraDto, utenteId);
                } else {
                        return squadraService.save(squadraDto);
                }
        }

        public List<StagioneDto> getStagioni() {
                return stagioneService.findAll();
        }

        public StagioneDto createStagione(StagioneDto stagioneDto) {
                return stagioneService.save(stagioneDto);
        }

        public OperazioneDto createOperazione(OperazioneDto operazioneDto) {
                return operazioneService.save(operazioneDto);
        }
}
