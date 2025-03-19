package it.fantacalcio.ffm.facade;

import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.GiocatoreTrattativa;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.service.*;
import it.fantacalcio.ffm.utility.CollectionUtility;
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
        private DettaglioTrattativaService dettaglioTrattativaService;
        private PrestitoService prestitoService;

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

        @Autowired
        public void setDettaglioTrattativaService(DettaglioTrattativaService dettaglioTrattativaService) { this.dettaglioTrattativaService = dettaglioTrattativaService; }

        @Autowired
        public void setPrestitoService(PrestitoService prestitoService) { this.prestitoService = prestitoService; }
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

        public StagioneDto saveStagione(StagioneDto stagioneDto) {
                return stagioneService.save(stagioneDto);
        }

        public OperazioneDto saveOperazione(OperazioneDto operazioneDto) {
                return operazioneService.save(operazioneDto);
        }

        public UtenteDto saveUtente(UtenteDto utenteDto) {
                return utenteService.save(utenteDto);
        }

        public TrattativaDto saveTrattativa(TrattativaDto TrattativaDto) {
                return trattativaService.save(TrattativaDto);
        }

        public FidoDto saveFido(FidoDto fidoDto) {
                return fidoTrattativaService.save(fidoDto);
        }

        public TransazioneTrattativaDto saveTransazioneTrattativa(TransazioneTrattativaDto transazioneTrattativaDto) {
                return transazioneTrattativaService.save(transazioneTrattativaDto);
        }

        public PrestitoDto savePrestitoTrattativa(PrestitoDto prestitoDto) {
                return prestitoService.save(prestitoDto);
        }

        public SquadraDto saveSquadra(SquadraDto squadraDto, Integer utenteId) {
                if (utenteId != null) {
                        return squadraService.save(squadraDto, utenteId);
                } else {
                        return squadraService.save(squadraDto);
                }
        }

        public DettaglioTrattativaDto saveDettaglioTrattativa(DettaglioTrattativaDto dettaglioTrattativaDto) {
                return dettaglioTrattativaService.save(dettaglioTrattativaDto);
        }

        @Transactional
        public TrattativaDto createTrattativaScambio(TrattativaScambio trattativaScambio) {
                TrattativaDto trattativaDto = createTrattativa();

                SquadraDto squadraDtoA = getSquadraById(trattativaScambio.getIdSquadraA());
                SquadraDto squadraDtoB = getSquadraById(trattativaScambio.getIdSquadraB());

                createTransazioneTrattativa(trattativaScambio,trattativaDto, squadraDtoA, squadraDtoB);
                createFidoTrattativa(trattativaScambio,trattativaDto,squadraDtoA,squadraDtoB);

                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraA(), giocatoreTrattativa -> {
                        creaDettaglioTrattativa(trattativaDto, giocatoreTrattativa, squadraDtoA, squadraDtoB, Constants.SquadraCedente.SQUADRA_A);
                });
                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraB(), giocatoreTrattativa -> {
                        creaDettaglioTrattativa(trattativaDto, giocatoreTrattativa, squadraDtoA, squadraDtoB, Constants.SquadraCedente.SQUADRA_B);
                });
                return trattativaDto;
        }

        private TrattativaDto createTrattativa(){
                StagioneDto stagioneDto = getLastStagione();
                TrattativaDto trattativaDto = new TrattativaDto(null, stagioneDto, LocalDateTime.now());
                return saveTrattativa(trattativaDto);
        }

        private void createTransazioneTrattativa(TrattativaScambio trattativaScambio,TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB){
                int creditiPagati = trattativaScambio.getCreditiPagatiSquadraA() > 0 ? trattativaScambio.getCreditiPagatiSquadraA() : trattativaScambio.getCreditiPagatiSquadraB();
                String segnoSquadraA = Constants.Segno.DEBITO.getSigla();
                String segnoSquadraB = Constants.Segno.CREDITO.getSigla();
                if(trattativaScambio.getCreditiPagatiSquadraB() > 0){
                        segnoSquadraA = Constants.Segno.CREDITO.getSigla();
                        segnoSquadraB = Constants.Segno.DEBITO.getSigla();
                }
                TransazioneTrattativaDto transazioneTrattativaDtoSquadraA = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoA, creditiPagati, segnoSquadraA, trattativaScambio.getGettoniSquadraA());
                TransazioneTrattativaDto transazioneTrattativaDtoSquadraB = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoB, creditiPagati, segnoSquadraB, trattativaScambio.getGettoniSquadraB());
                saveTransazioneTrattativa(transazioneTrattativaDtoSquadraA);
                saveTransazioneTrattativa(transazioneTrattativaDtoSquadraB);
        }

        private void createFidoTrattativa(TrattativaScambio trattativaScambio,TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB){
                int creditiFido = trattativaScambio.getCreditiPostSquadraA() > 0 ? trattativaScambio.getCreditiPostSquadraA() : trattativaScambio.getCreditiPostSquadraB();
                String segnoFidoSquadraA = Constants.Segno.DEBITO.getSigla();
                String segnoFidoSquadraB = Constants.Segno.CREDITO.getSigla();
                if(trattativaScambio.getCreditiPostSquadraB() > 0){
                        segnoFidoSquadraA = Constants.Segno.CREDITO.getSigla();
                        segnoFidoSquadraB = Constants.Segno.DEBITO.getSigla();
                }
                FidoDto fidoDtoSquadraA = new FidoDto(null, trattativaDto, squadraDtoA, creditiFido, segnoFidoSquadraA);
                FidoDto fidoDtoSquadraB = new FidoDto(null, trattativaDto, squadraDtoB, creditiFido, segnoFidoSquadraB);
                saveFido(fidoDtoSquadraA);
                saveFido(fidoDtoSquadraB);
        }

        private void creaDettaglioTrattativa(TrattativaDto trattativaDto, GiocatoreTrattativa giocatoreTrattativa, SquadraDto squadraDtoA, SquadraDto squadraDtoB, Constants.SquadraCedente squadraCedente){
                GiocatoreDto giocatoreDto = getGiocatoreByIdFantagazzetta(giocatoreTrattativa.getIdFantagazzetta());
                TipoDettTrattativaDto tipoDettTrattativaDto = getTipoDettTrattativaBySigla(giocatoreTrattativa.getTipoCessione());
                TipoOperazioneDto tipoOperazioneDtoSquadraA = squadraCedente.equals(Constants.SquadraCedente.SQUADRA_A) ? getTipoOperazioneBySigla(Constants.TipoOperazione.CESSIONE.getSigla()) : getTipoOperazioneBySigla(Constants.TipoOperazione.ACQUISTO.getSigla());
                TipoOperazioneDto tipoOperazioneDtoSquadraB = squadraCedente.equals(Constants.SquadraCedente.SQUADRA_B) ? getTipoOperazioneBySigla(Constants.TipoOperazione.CESSIONE.getSigla()) : getTipoOperazioneBySigla(Constants.TipoOperazione.ACQUISTO.getSigla());
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraA = new DettaglioTrattativaDto(null,trattativaDto,tipoDettTrattativaDto,squadraDtoA,giocatoreDto,tipoOperazioneDtoSquadraA, null);
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraB = new DettaglioTrattativaDto(null,trattativaDto,tipoDettTrattativaDto,squadraDtoB,giocatoreDto,tipoOperazioneDtoSquadraB, null);
                if(tipoDettTrattativaDto.isPrestito()){
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraA, giocatoreTrattativa);
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraB, giocatoreTrattativa);
                }
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraA);
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraB);
        }

        private void setPrestitoDettTrattativa(DettaglioTrattativaDto dettaglioTrattativaDto, GiocatoreTrattativa giocatoreTrattativa){
                PrestitoDto prestitoDto = new PrestitoDto();
                prestitoDto.setIdDettTrattativa(dettaglioTrattativaDto);
                prestitoDto.setRiscatto(giocatoreTrattativa.getPrestito().isRiscatto());
                prestitoDto.setObbligo(giocatoreTrattativa.getPrestito().isObbligo());
                prestitoDto.setCostoRiscatto(giocatoreTrattativa.getPrestito().getCostoRiscatto());
                dettaglioTrattativaDto.setIdPrestito(prestitoDto);
        }
}
