package it.fantacalcio.ffm.facade;

import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiGatewayFacade {

        private GiocatoreService giocatoreService;
        private NazioneService nazioneService;
        private UtenteService utenteService;
        private SquadraService squadraService;
        private StagioneService stagioneService;
        private OperazioneService operazioneService;
        private CategoriaService categoriaService;

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
        public void setStagioneService(OperazioneService operazioneService) { this.operazioneService = operazioneService; }

        @Autowired
        public void setCategoriaService(CategoriaService categoriaService) { this.categoriaService = categoriaService; }
        /* FINE METODI SETTER PER INJECTION */

        /* METODI DI DOMINIO */
        public List<GiocatoreDto> getGiocatori() {
                return giocatoreService.findAll();
        }

        public GiocatoreDto getGiocatore(Integer idFantagazzetta) {
                return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
        }

        public List<SquadraDto> getSquadre() {
                return squadraService.findAll();
        }

        public List<StagioneDto> getStagioni() {
                return stagioneService.findAll();
        }

        public List<NazioneDto> getNazioni() {
                return nazioneService.findAll();
        }

        public List<UtenteDto> getUtenti() {
                return utenteService.findAll();
        }

        public List<CategoriaDto> getCategorie() {
                return categoriaService.findAll();
        }

        public StagioneDto createStagione(StagioneDto stagioneDto) {
                return stagioneService.save(stagioneDto);
        }

        public OperazioneDto createOperazione(OperazioneDto operazioneDto) {
                return operazioneService.save(operazioneDto);
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
}
