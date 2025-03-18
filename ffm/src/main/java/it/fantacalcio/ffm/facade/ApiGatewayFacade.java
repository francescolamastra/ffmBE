package it.fantacalcio.ffm.facade;

import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.service.*;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        private TipoOperazioneService tipoOperazioneService;
        private TrattativaService trattativaService;
        private TransazioneTrattativaService transazioneTrattativaService;
        private FidoTrattativaService fidoTrattativaService;
        private TipoDettTrattativaService tipoDettTrattativaService;

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

        @Autowired
        public void setTipoOperazioneService(TipoOperazioneService tipoOperazioneService) { this.tipoOperazioneService = tipoOperazioneService; }

        @Autowired
        public void setTrattativaService(TrattativaService trattativaService) { this.trattativaService = trattativaService; }

        @Autowired
        public void setTransazioneTrattativaService(TransazioneTrattativaService transazioneTrattativaService) { this.transazioneTrattativaService = transazioneTrattativaService; }

        @Autowired
        public void setFidoTrattativaService(FidoTrattativaService fidoTrattativaService) { this.fidoTrattativaService = fidoTrattativaService; }

        @Autowired
        public void setTipoDettTrattativaService(TipoDettTrattativaService tipoDettTrattativaService) { this.tipoDettTrattativaService = tipoDettTrattativaService; }
        /* FINE METODI SETTER PER INJECTION */

        /* METODI DI DOMINIO */
        public List<GiocatoreDto> getGiocatori() {
                return giocatoreService.findAll();
        }

        public GiocatoreDto getGiocatoreByIdFantagazzetta(Integer idFantagazzetta) {
                return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
        }

        public List<SquadraDto> getSquadre() {
                return squadraService.findAll();
        }

        public SquadraDto getSquadraByNome(SquadraDto squadraDto) { return squadraService.findByNome(squadraDto); }

        public SquadraDto getSquadraById(Integer id) { return squadraService.findById(id).orElseThrow(); }

        public List<StagioneDto> getStagioni() {
                return stagioneService.findAll();
        }

        public List<NazioneDto> getNazioni() {
                return nazioneService.findAll();
        }

        public NazioneDto getNazioneBySigla(String sigla) { return nazioneService.findBySigla(sigla).orElseThrow(); }

        public List<UtenteDto> getUtenti() {
                return utenteService.findAll();
        }

        public List<CategoriaDto> getCategorie() {
                return categoriaService.findAll();
        }

        public CategoriaDto getCategoriaBySigla(String sigla) { return categoriaService.findBySigla(sigla).orElseThrow(); }

        public TipoOperazioneDto getTipoOperazioneBySigla(String sigla) { return tipoOperazioneService.findBySigla(sigla).orElseThrow(); }

        public TipoDettTrattativaDto getTipoDettTrattativaBySigla(String sigla) { return tipoDettTrattativaService.findBySigla(sigla).orElseThrow(); }

        public StagioneDto getLastStagione() { return stagioneService.getLastStagione().orElseThrow(); }

        public StagioneDto createStagione(StagioneDto stagioneDto) {
                return stagioneService.save(stagioneDto);
        }

        public OperazioneDto createOperazione(OperazioneDto operazioneDto) {
                return operazioneService.save(operazioneDto);
        }

        public UtenteDto createUtente(UtenteDto utenteDto) {
                return utenteService.save(utenteDto);
        }

        public TrattativaDto createTrattativa(TrattativaDto TrattativaDto) {
                return trattativaService.save(TrattativaDto);
        }

        public FidoDto createFido(FidoDto fidoDto) {
                return fidoTrattativaService.save(fidoDto);
        }

        public TransazioneTrattativaDto createTransazioneTrattativa(TransazioneTrattativaDto transazioneTrattativaDto) {
                return transazioneTrattativaService.save(transazioneTrattativaDto);
        }

        public SquadraDto createSquadra(SquadraDto squadraDto, Integer utenteId) {
                if (utenteId != null) {
                        return squadraService.save(squadraDto, utenteId);
                } else {
                        return squadraService.save(squadraDto);
                }
        }

        @Transactional
        public TrattativaDto createTrattativaScambio(TrattativaScambio trattativaScambio) {
                StagioneDto stagioneDto = getLastStagione();
                TrattativaDto trattativaDto = new TrattativaDto(null, stagioneDto, LocalDateTime.now());
                trattativaDto = createTrattativa(trattativaDto);

                SquadraDto squadraDtoA = getSquadraById(trattativaScambio.getIdSquadraA());
                SquadraDto squadraDtoB = getSquadraById(trattativaScambio.getIdSquadraB());

                int creditiPagati = trattativaScambio.getCreditiPagatiSquadraA() > 0 ? trattativaScambio.getCreditiPagatiSquadraA() : trattativaScambio.getCreditiPagatiSquadraB();
                String segnoSquadraA = Constants.Segno.DEBITO.getSigla();
                String segnoSquadraB = Constants.Segno.CREDITO.getSigla();
                if(trattativaScambio.getCreditiPagatiSquadraB() > 0){
                        segnoSquadraA = Constants.Segno.CREDITO.getSigla();
                        segnoSquadraB = Constants.Segno.DEBITO.getSigla();
                }

                TransazioneTrattativaDto transazioneTrattativaDtoSquadraA = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoA, creditiPagati, segnoSquadraA, trattativaScambio.getGettoniSquadraA());
                TransazioneTrattativaDto transazioneTrattativaDtoSquadraB = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoB, creditiPagati, segnoSquadraB, trattativaScambio.getGettoniSquadraB());

                createTransazioneTrattativa(transazioneTrattativaDtoSquadraA);
                createTransazioneTrattativa(transazioneTrattativaDtoSquadraB);

                int creditiFido = trattativaScambio.getCreditiPostSquadraA() > 0 ? trattativaScambio.getCreditiPostSquadraA() : trattativaScambio.getCreditiPostSquadraB();
                String segnoFidoSquadraA = Constants.Segno.DEBITO.getSigla();
                String segnoFidoSquadraB = Constants.Segno.CREDITO.getSigla();
                if(trattativaScambio.getCreditiPostSquadraB() > 0){
                        segnoFidoSquadraA = Constants.Segno.CREDITO.getSigla();
                        segnoFidoSquadraB = Constants.Segno.DEBITO.getSigla();
                }

                FidoDto fidoDtoSquadraA = new FidoDto(null, trattativaDto, squadraDtoA, creditiFido, segnoFidoSquadraA);
                FidoDto fidoDtoSquadraB = new FidoDto(null, trattativaDto, squadraDtoB, creditiFido, segnoFidoSquadraB);
                createFido(fidoDtoSquadraA);
                createFido(fidoDtoSquadraB);
                trattativaScambio.getListGiocatoriCedutiSquadraA()
                        .forEach(giocatoreTrattativa -> {
                             TipoDettTrattativaDto tipoDettTrattativaDto = getTipoDettTrattativaBySigla(giocatoreTrattativa.getTipoCessione());
                        });
                return trattativaDto;
        }
}
