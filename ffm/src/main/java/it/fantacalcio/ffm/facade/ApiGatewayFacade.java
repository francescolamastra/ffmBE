package it.fantacalcio.ffm.facade;

import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.converter.CompetizioneConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Competizione;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import it.fantacalcio.ffm.domain.model.BonusTrattativaScambio;
import it.fantacalcio.ffm.domain.model.GiocatoreTrattativaScambio;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.domain.model.fantaleghe.*;
import it.fantacalcio.ffm.service.*;
import it.fantacalcio.ffm.utility.CollectionUtility;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static it.fantacalcio.ffm.utility.Constants.PATTERN_NAZIONE_LOGIN_FANTALEGHE;
import static it.fantacalcio.ffm.utility.Constants.PATTERN_SQUADRA_JOINED_STRING;

@Component
public class ApiGatewayFacade {

        private static final Logger logger = LoggerFactory.getLogger(ApiGatewayFacade.class);

        private final EntityManager entityManager;

        public ApiGatewayFacade(EntityManager entityManager){
                this.entityManager = entityManager;
        }

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
        private BonusTrattativaService bonusTrattativaService;
        private CompetizioneService competizioneService;
        private StagioneCompetizioneService stagioneCompetizioneService;
        private RisultatoCompetizioneService risultatoCompetizioneService;
        private FaseCompetizioneService faseCompetizioneService;
        private FantalegheService fantalegheService;
        private TokenCredenzialiService tokenCredenzialiService;
        private CredenzialiService credenzialiService;

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

        @Autowired
        public void setBonusTrattativaService(BonusTrattativaService bonusTrattativaService) { this.bonusTrattativaService = bonusTrattativaService; }

        @Autowired
        public void setCompetizioneService(CompetizioneService competizioneService) { this.competizioneService = competizioneService; }

        @Autowired
        public void setStagioneCompetizioneService(StagioneCompetizioneService stagioneCompetizioneService) { this.stagioneCompetizioneService = stagioneCompetizioneService; }

        @Autowired
        public void setRisultatoCompetizioneService(RisultatoCompetizioneService risultatoCompetizioneService) { this.risultatoCompetizioneService = risultatoCompetizioneService; }

        @Autowired
        public void setFaseCompetizioneService(FaseCompetizioneService faseCompetizioneService) { this.faseCompetizioneService = faseCompetizioneService; }

        @Autowired
        public void setFantalegheLoginService(FantalegheService fantalegheService) { this.fantalegheService = fantalegheService; }

        @Autowired
        public void setTokenCredenzialiService(TokenCredenzialiService tokenCredenzialiService) { this.tokenCredenzialiService = tokenCredenzialiService; }

        @Autowired
        public void setCredenzialiService(CredenzialiService credenzialiService) { this.credenzialiService = credenzialiService; }
        /* FINE METODI SETTER PER INJECTION */

        public List<TokenCredenzialiProjectionDto> fantalegheLogin(CredenzialiDto credenzialiDto) {
                List<TokenCredenzialiProjectionDto> tokenCredenzialiProjectionDtoList = tokenCredenzialiService.getAllTokenCredenzialiInfo(credenzialiDto.getIdUtente());
                if(tokenCredenzialiProjectionDtoList.isEmpty() || tokenCredenzialiProjectionDtoList.stream().anyMatch(token -> !token.getIsValid())) {
                        FantalegheLoginResponse fantalegheLoginResponse = fantalegheService.login(new FantalegheLoginRequest(credenzialiDto.getUserName(), credenzialiDto.getPassword()));
                        tokenCredenzialiProjectionDtoList.clear();
                        fantalegheLoginResponse.getData().getLeghe()
                                .forEach(lega -> {
                                        try {
                                                NazioneDto nazioneDto = nazioneFromNomeLega(lega.getNome());
                                                TokenCredenzialiDto tokenCredenzialiDto = new TokenCredenzialiDto(null, lega.getJwt(), credenzialiDto.getIdUtente(), nazioneDto, true, LocalDateTime.now());
                                                tokenCredenzialiProjectionDtoList.add(tokenCredenzialiService.save(tokenCredenzialiDto));
                                        } catch (Exception e) {
                                                // Log dell'errore e continuazione del ciclo
                                                logger.error("Errore durante l'elaborazione della lega {}: {}", lega.getNome(), e.getMessage());
                                        }
                                });
                }
                return tokenCredenzialiProjectionDtoList;
        }

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

        public SquadraDto getSquadraByNomeOrSave(SquadraDto squadraDto) { return squadraService.findByNomeOrSave(squadraDto); }

        public SquadraDto getSquadraByIdFantagazzetta(Integer idFantagazzetta) { return squadraService.findByIdFantagazzetta(idFantagazzetta).orElse(null); }

        public SquadraDto getSquadraById(Integer id) { return squadraService.findById(id).orElseThrow(); }

        public List<StagioneDto> getStagioni() {
                return stagioneService.findAll();
        }

        public List<NazioneDto> getNazioni() {
                return nazioneService.findAll();
        }

        public List<FaseCompetizioneDto> getFasiCompetizione() {
                return faseCompetizioneService.findAll();
        }

        public NazioneDto getNazioneBySigla(String sigla) { return nazioneService.findBySigla(sigla).orElseThrow(); }

        public List<UtenteDto> getUtenti() {
                return utenteService.findAll();
        }

        public UtenteDto getUtenteByNickname(String nickname){
                return utenteService.findByNickname(nickname).orElseThrow();
        }

        public CredenzialiDto getCredenzialiByUtente(UtenteDto utenteDto){
                return credenzialiService.findByIdUtente(utenteDto).orElseThrow();
        }

        public List<CategoriaDto> getCategorie() {
                return categoriaService.findAll();
        }

        public CategoriaDto getCategoriaBySigla(String sigla) { return categoriaService.findBySigla(sigla).orElseThrow(); }

        public TipoOperazioneDto getTipoOperazioneBySigla(String sigla) { return tipoOperazioneService.findBySigla(sigla).orElseThrow(); }

        public TipoDettTrattativaDto getTipoDettTrattativaBySigla(String sigla) { return tipoDettTrattativaService.findBySigla(sigla).orElseThrow(); }

        public StagioneDto getLastStagione() { return stagioneService.getLastStagione().orElseThrow(); }

        public List<CompetizioneDto> getCompetizioni() {
                return competizioneService.findAll();
        }

        public CompetizioneDto getCompetizioneBySigla(String sigla) { return competizioneService.findBySigla(sigla).orElseThrow(); }

        public StagioneCompetizioneDto getStagioneCompetizioneByStagioneAndCompetizione(StagioneDto stagioneDto, CompetizioneDto competizioneDto) { return stagioneCompetizioneService.findByStagioneAndCompetizione(stagioneDto, competizioneDto).orElseThrow(); }

        public boolean existsRisultatoCompetizioneByStagioneCompetizioneAndGiornataSerieA(StagioneCompetizioneDto stagioneCompetizioneDto, Integer giornataSerieA) { return risultatoCompetizioneService.existsByStagioneCompetizioneAndGiornataSerieA(stagioneCompetizioneDto, giornataSerieA); }

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

        public BonusTrattativaDto saveBonusTrattativa(BonusTrattativaDto bonusTrattativaDto) {
                return bonusTrattativaService.save(bonusTrattativaDto);
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

        public StagioneCompetizioneDto saveStagioneCompetizione(StagioneCompetizioneDto stagioneCompetizioneDto) {
                return stagioneCompetizioneService.save(stagioneCompetizioneDto);
        }

        public StagioneCompetizioneDto saveStagioneCompetizione(StagioneCompetizione stagioneCompetizione) {
                return stagioneCompetizioneService.save(stagioneCompetizione);
        }

        public SquadraDto squadraFromJoinedString(String squadraJoinedString) {
                Matcher matcher = PATTERN_SQUADRA_JOINED_STRING.matcher(squadraJoinedString);

                if (matcher.matches()) {
                        String nomeSquadra = matcher.group(1);
                        String siglaNazione = matcher.group(2);
                        String siglaCategoria = matcher.group(3);
                        CategoriaDto categoriaDto = getCategoriaBySigla(siglaCategoria);
                        NazioneDto nazioneDto = getNazioneBySigla(siglaNazione);
                        return new SquadraDtoBuilder()
                                .setNome(nomeSquadra)
                                .setIdNazione(nazioneDto)
                                .setIdCategoria(categoriaDto)
                                .build();
                } else {
                        System.out.println("squadraFromJoinedString Input non valido: " + squadraJoinedString);
                        return null;
                }
        }

        public NazioneDto nazioneFromNomeLega(String nomeLega) {
                Matcher matcher = PATTERN_NAZIONE_LOGIN_FANTALEGHE.matcher(nomeLega);

                if (matcher.matches()) {
                        String nomeNazione = matcher.group(1);
                        return getNazioni().stream()
                                .filter(nazioneDto -> nazioneDto.getDescrizione().equalsIgnoreCase(nomeNazione))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Nazione non trovata per il nome lega: " + nomeLega));
                } else {
                        throw new IllegalArgumentException("Input non valido: " + nomeLega);
                }
        }

        @Transactional
        public void inizializzaCompetizioni(){
                StagioneDto stagioneDto = getLastStagione();
                List<CompetizioneDto> competizioneDtoList = getCompetizioni();
                competizioneDtoList.forEach(
                        competizioneDto -> {
                                createAndSaveStagioneCompetizione(stagioneDto, competizioneDto);
                        }
                );
        }

        @Transactional
        public TrattativaDto createTrattativaScambio(TrattativaScambio trattativaScambio) {
                TrattativaDto trattativaDto = createTrattativa(trattativaScambio);

                SquadraDto squadraDtoA = getSquadraById(trattativaScambio.getIdSquadraA());
                SquadraDto squadraDtoB = getSquadraById(trattativaScambio.getIdSquadraB());

                createTransazioneTrattativa(trattativaScambio,trattativaDto, squadraDtoA, squadraDtoB);
                createFidoTrattativa(trattativaScambio,trattativaDto,squadraDtoA,squadraDtoB);
                createBonusTrattativa(trattativaScambio,trattativaDto,squadraDtoA,squadraDtoB);

                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraA(), giocatoreTrattativaScambio -> {
                        creaDettaglioTrattativa(trattativaDto, giocatoreTrattativaScambio, squadraDtoA, squadraDtoB, Constants.SquadraOwnerEnum.SQUADRA_A);
                });
                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraB(), giocatoreTrattativaScambio -> {
                        creaDettaglioTrattativa(trattativaDto, giocatoreTrattativaScambio, squadraDtoA, squadraDtoB, Constants.SquadraOwnerEnum.SQUADRA_B);
                });
                return trattativaDto;
        }

        private void createBonusTrattativa(TrattativaScambio trattativaScambio, TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB) {
                if (trattativaScambio.getBonusPostSquadraA() != null) {
                        createAndSaveBonus(trattativaScambio.getBonusPostSquadraA(), trattativaDto, squadraDtoA, squadraDtoB, Constants.SegnoEnum.DEBITO.getSigla(), Constants.SegnoEnum.CREDITO.getSigla());
                }
                if (trattativaScambio.getBonusPostSquadraB() != null) {
                        createAndSaveBonus(trattativaScambio.getBonusPostSquadraB(), trattativaDto, squadraDtoA, squadraDtoB, Constants.SegnoEnum.CREDITO.getSigla(), Constants.SegnoEnum.DEBITO.getSigla());
                }
        }

        private void createAndSaveBonus(BonusTrattativaScambio bonus, TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB, String segnoSquadraA, String segnoSquadraB) {
                int creditiMassimaleBonus = bonus.getMassimale();
                BonusTrattativaDto bonusTrattativaDtoSquadraA = new BonusTrattativaDto(null, trattativaDto, squadraDtoA, creditiMassimaleBonus, segnoSquadraA);
                BonusTrattativaDto bonusTrattativaDtoSquadraB = new BonusTrattativaDto(null, trattativaDto, squadraDtoB, creditiMassimaleBonus, segnoSquadraB);
                saveBonusTrattativa(bonusTrattativaDtoSquadraA);
                saveBonusTrattativa(bonusTrattativaDtoSquadraB);
        }

        private TrattativaDto createTrattativa(TrattativaScambio trattativaScambio){
                StagioneDto stagioneDto = getLastStagione();
                TrattativaDto trattativaDto = new TrattativaDto(null, stagioneDto, trattativaScambio.getClausole(), trattativaScambio.getDataTrattativa());
                return saveTrattativa(trattativaDto);
        }

        private void createTransazioneTrattativa(TrattativaScambio trattativaScambio,TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB){
                if(trattativaScambio.getCreditiPagatiSquadraA() > 0 || trattativaScambio.getCreditiPagatiSquadraB() > 0) {
                        int creditiPagati = trattativaScambio.getCreditiPagatiSquadraA() > 0 ? trattativaScambio.getCreditiPagatiSquadraA() : trattativaScambio.getCreditiPagatiSquadraB();
                        String segnoSquadraA = Constants.SegnoEnum.DEBITO.getSigla();
                        String segnoSquadraB = Constants.SegnoEnum.CREDITO.getSigla();
                        if (trattativaScambio.getCreditiPagatiSquadraB() > 0) {
                                segnoSquadraA = Constants.SegnoEnum.CREDITO.getSigla();
                                segnoSquadraB = Constants.SegnoEnum.DEBITO.getSigla();
                        }
                        TransazioneTrattativaDto transazioneTrattativaDtoSquadraA = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoA, creditiPagati, segnoSquadraA, trattativaScambio.getGettoniSquadraA());
                        TransazioneTrattativaDto transazioneTrattativaDtoSquadraB = new TransazioneTrattativaDto(null, trattativaDto, squadraDtoB, creditiPagati, segnoSquadraB, trattativaScambio.getGettoniSquadraB());
                        saveTransazioneTrattativa(transazioneTrattativaDtoSquadraA);
                        saveTransazioneTrattativa(transazioneTrattativaDtoSquadraB);
                }
        }

        private void createFidoTrattativa(TrattativaScambio trattativaScambio,TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB){
                if(trattativaScambio.getCreditiPostSquadraA() > 0 || trattativaScambio.getCreditiPostSquadraB() > 0) {
                        int creditiFido = trattativaScambio.getCreditiPostSquadraA() > 0 ? trattativaScambio.getCreditiPostSquadraA() : trattativaScambio.getCreditiPostSquadraB();
                        String segnoFidoSquadraA = Constants.SegnoEnum.DEBITO.getSigla();
                        String segnoFidoSquadraB = Constants.SegnoEnum.CREDITO.getSigla();
                        if (trattativaScambio.getCreditiPostSquadraB() > 0) {
                                segnoFidoSquadraA = Constants.SegnoEnum.CREDITO.getSigla();
                                segnoFidoSquadraB = Constants.SegnoEnum.DEBITO.getSigla();
                        }
                        FidoDto fidoDtoSquadraA = new FidoDto(null, trattativaDto, squadraDtoA, creditiFido, segnoFidoSquadraA);
                        FidoDto fidoDtoSquadraB = new FidoDto(null, trattativaDto, squadraDtoB, creditiFido, segnoFidoSquadraB);
                        saveFido(fidoDtoSquadraA);
                        saveFido(fidoDtoSquadraB);
                }
        }

        private void creaDettaglioTrattativa(TrattativaDto trattativaDto, GiocatoreTrattativaScambio giocatoreTrattativaScambio, SquadraDto squadraDtoA, SquadraDto squadraDtoB, Constants.SquadraOwnerEnum squadraOwner){
                GiocatoreDto giocatoreDto = getGiocatoreByIdFantagazzetta(giocatoreTrattativaScambio.getIdFantagazzetta());
                TipoDettTrattativaDto tipoDettTrattativaDto = getTipoDettTrattativaBySigla(giocatoreTrattativaScambio.getTipoCessione());
                TipoOperazioneDto tipoOperazioneDtoSquadraA = squadraOwner.equals(Constants.SquadraOwnerEnum.SQUADRA_A) ? getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.CESSIONE.getSigla()) : getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.ACQUISTO.getSigla());
                TipoOperazioneDto tipoOperazioneDtoSquadraB = squadraOwner.equals(Constants.SquadraOwnerEnum.SQUADRA_B) ? getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.CESSIONE.getSigla()) : getTipoOperazioneBySigla(Constants.TipoOperazioneEnum.ACQUISTO.getSigla());
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraA = new DettaglioTrattativaDto(null,trattativaDto,tipoDettTrattativaDto,squadraDtoA,giocatoreDto,tipoOperazioneDtoSquadraA, null);
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraB = new DettaglioTrattativaDto(null,trattativaDto,tipoDettTrattativaDto,squadraDtoB,giocatoreDto,tipoOperazioneDtoSquadraB, null);
                if(tipoDettTrattativaDto.isPrestito()){
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraA, giocatoreTrattativaScambio);
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraB, giocatoreTrattativaScambio);
                }
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraA);
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraB);
        }

        private void setPrestitoDettTrattativa(DettaglioTrattativaDto dettaglioTrattativaDto, GiocatoreTrattativaScambio giocatoreTrattativaScambio){
                PrestitoDto prestitoDto = new PrestitoDto();
                prestitoDto.setIdDettTrattativa(dettaglioTrattativaDto);
                prestitoDto.setRiscatto(giocatoreTrattativaScambio.getPrestito().isRiscatto());
                prestitoDto.setObbligo(giocatoreTrattativaScambio.getPrestito().isObbligo());
                prestitoDto.setCostoRiscatto(giocatoreTrattativaScambio.getPrestito().getCostoRiscatto());
                dettaglioTrattativaDto.setIdPrestito(prestitoDto);
        }

        private void createAndSaveStagioneCompetizione(StagioneDto stagioneDto, CompetizioneDto competizioneDto){
                Stagione stagione = entityManager.merge(StagioneConverter.toEntity(stagioneDto));
                Competizione competizione = entityManager.merge(CompetizioneConverter.toEntity(competizioneDto));
                StagioneCompetizione stagioneCompetizione = new StagioneCompetizione(null, stagione, competizione);
                saveStagioneCompetizione(stagioneCompetizione);
        }

        public FantalegheMercato getMercatiByNazioneAndCategoria(String siglaNazione, String siglaCategoria, String nickname) {
                UtenteDto utenteDto = getUtenteByNickname(nickname);
                CredenzialiDto credenzialiDto = getCredenzialiByUtente(utenteDto);
                NazioneDto nazioneDto = getNazioneBySigla(siglaNazione);
                getCategoriaBySigla(siglaCategoria);
                List<TokenCredenzialiProjectionDto> tokenCredenzialiDtoList = fantalegheLogin(credenzialiDto);
                Optional<TokenCredenzialiProjectionDto> optToken = tokenCredenzialiDtoList.stream().filter(t-> t.getNazione().equals(nazioneDto))
                        .findFirst();
            return optToken
                    .map(tokenCredenzialiProjectionDto -> getMercatiByNazioneAndCategoria(siglaCategoria, tokenCredenzialiProjectionDto.getJwt()))
                    .orElse(null);
        }

        private FantalegheMercato getMercatiByNazioneAndCategoria(String siglaCategoria, String tokenJwt){
                return fantalegheService.getMercatiCategoria(siglaCategoria, tokenJwt);
        }

        public List<FantalegheTeam> getTeamsByNazione(String siglaNazione, String nickname) {
                UtenteDto utenteDto = getUtenteByNickname(nickname);
                CredenzialiDto credenzialiDto = getCredenzialiByUtente(utenteDto);
                NazioneDto nazioneDto = getNazioneBySigla(siglaNazione);
                List<TokenCredenzialiProjectionDto> tokenCredenzialiDtoList = fantalegheLogin(credenzialiDto);
                Optional<TokenCredenzialiProjectionDto> optToken = tokenCredenzialiDtoList.stream().filter(t-> t.getNazione().equals(nazioneDto))
                        .findFirst();
                return optToken
                        .map(tokenCredenzialiProjectionDto -> getTeams(tokenCredenzialiProjectionDto.getJwt()))
                        .orElse(null);
        }

        private List<FantalegheTeam> getTeams(String tokenJwt){
                return fantalegheService.getTeams(tokenJwt);
        }

        public List<TokenCredenzialiProjectionDto> fantalegheLogin(String nickname) {
                UtenteDto utenteDto = getUtenteByNickname(nickname);
                CredenzialiDto credenzialiDto = getCredenzialiByUtente(utenteDto);
                return fantalegheLogin(credenzialiDto);
        }

        public FantalegheOperazioneMercato getOperazioniMercatoByNazioneAndCategoria(String siglaNazione, String siglaCategoria, String idMercato, String tipoMercato, String nickname) {
                Constants.TipologiaMercatoFantalegheEnum tipologiaMercato = Constants.TipologiaMercatoFantalegheEnum.fromValue(tipoMercato);
                UtenteDto utenteDto = getUtenteByNickname(nickname);
                CredenzialiDto credenzialiDto = getCredenzialiByUtente(utenteDto);
                NazioneDto nazioneDto = getNazioneBySigla(siglaNazione);
                getCategoriaBySigla(siglaCategoria);
                List<TokenCredenzialiProjectionDto> tokenCredenzialiDtoList = fantalegheLogin(credenzialiDto);
                Optional<TokenCredenzialiProjectionDto> optToken = tokenCredenzialiDtoList.stream().filter(t-> t.getNazione().equals(nazioneDto))
                        .findFirst();
                return optToken
                        .map(tokenCredenzialiProjectionDto -> getOperazioniMercatoByNazioneAndCategoria(siglaCategoria, idMercato, tipologiaMercato, tokenCredenzialiProjectionDto.getJwt()))
                        .orElse(null);
        }

        private FantalegheOperazioneMercato getOperazioniMercatoByNazioneAndCategoria(String siglaCategoria, String idMercato, Constants.TipologiaMercatoFantalegheEnum tipoMercato, String tokenJwt){
                return fantalegheService.getOperazioniMercato(siglaCategoria, idMercato, tipoMercato, tokenJwt);
        }

        public FantalegheTrattativeScambio getTrattativeScambioByNazioneAndCategoria(String siglaNazione, String siglaCategoria, String idMercato, String tipoMercato, String nickname) {
                Constants.TipologiaMercatoFantalegheEnum tipologiaMercato = Constants.TipologiaMercatoFantalegheEnum.fromValue(tipoMercato);
                UtenteDto utenteDto = getUtenteByNickname(nickname);
                CredenzialiDto credenzialiDto = getCredenzialiByUtente(utenteDto);
                NazioneDto nazioneDto = getNazioneBySigla(siglaNazione);
                getCategoriaBySigla(siglaCategoria);
                List<TokenCredenzialiProjectionDto> tokenCredenzialiDtoList = fantalegheLogin(credenzialiDto);
                Optional<TokenCredenzialiProjectionDto> optToken = tokenCredenzialiDtoList.stream().filter(t-> t.getNazione().equals(nazioneDto))
                        .findFirst();
                return optToken
                        .map(tokenCredenzialiProjectionDto -> getTrattativeScambioByNazioneAndCategoria(siglaCategoria, idMercato, tipologiaMercato, tokenCredenzialiProjectionDto.getJwt()))
                        .orElse(null);
        }

        private FantalegheTrattativeScambio getTrattativeScambioByNazioneAndCategoria(String siglaCategoria, String idMercato, Constants.TipologiaMercatoFantalegheEnum tipoMercato, String tokenJwt){
                return fantalegheService.getTrattativeScambio(siglaCategoria, idMercato, tipoMercato, tokenJwt);
        }
}
