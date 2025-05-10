package it.fantacalcio.ffm.facade;

import it.fantacalcio.ffm.batch.model.TrattativaScambioBatch;
import it.fantacalcio.ffm.builder.SituazioneEconomicaInizialeDtoBuilder;
import it.fantacalcio.ffm.builder.SquadraDtoBuilder;
import it.fantacalcio.ffm.converter.CompetizioneConverter;
import it.fantacalcio.ffm.converter.StagioneConverter;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.entity.Competizione;
import it.fantacalcio.ffm.domain.entity.Stagione;
import it.fantacalcio.ffm.domain.entity.StagioneCompetizione;
import it.fantacalcio.ffm.domain.model.*;
import it.fantacalcio.ffm.domain.model.fantaleghe.*;
import it.fantacalcio.ffm.service.*;
import it.fantacalcio.ffm.utility.CollectionUtility;
import it.fantacalcio.ffm.utility.Constants;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static it.fantacalcio.ffm.utility.Constants.PATTERN_NAZIONE_LOGIN_FANTALEGHE;
import static it.fantacalcio.ffm.utility.Constants.PATTERN_SQUADRA_JOINED_STRING;

@Component
@RequiredArgsConstructor
public class ApiGatewayFacade {

        private static final Logger logger = LoggerFactory.getLogger(ApiGatewayFacade.class);

        private final EntityManager entityManager;
        private final GiocatoreService giocatoreService;
        private final NazioneService nazioneService;
        private final UtenteService utenteService;
        private final SquadraService squadraService;
        private final StagioneService stagioneService;
        private final OperazioneService operazioneService;
        private final CategoriaService categoriaService;
        private final TipoOperazioneService tipoOperazioneService;
        private final TrattativaService trattativaService;
        private final TransazioneTrattativaService transazioneTrattativaService;
        private final FidoTrattativaService fidoTrattativaService;
        private final TipoDettTrattativaService tipoDettTrattativaService;
        private final DettaglioTrattativaService dettaglioTrattativaService;
        private final PrestitoService prestitoService;
        private final BonusTrattativaService bonusTrattativaService;
        private final CompetizioneService competizioneService;
        private final StagioneCompetizioneService stagioneCompetizioneService;
        private final RisultatoCompetizioneService risultatoCompetizioneService;
        private final FaseCompetizioneService faseCompetizioneService;
        private final FantalegheService fantalegheService;
        private final TokenCredenzialiService tokenCredenzialiService;
        private final CredenzialiService credenzialiService;
        private final GiocatoreRosaService giocatoreRosaService;
        private final GiocatoreListoneService giocatoreListoneService;
        private final GettoneService gettoneService;
        private final StadioService stadioService;
        private final SituazioneEconomicaInizialeService situazioneEconomicaInizialeService;
        private final AmpliamentoStadioService ampliamentoStadioService;

        public List<GiocatoreRosaDto> getAllGiocatoreRosaByIdStagioneAndIdSquadraAndTipologiaRosa(StagioneDto idStagione, SquadraDto idSquadra, Constants.TipologiaRosaEnum tipologiaRosa){
                return giocatoreRosaService.findAllByIdStagioneAndIdSquadraAndTipologiaRosa(idStagione, idSquadra, tipologiaRosa);
        }

        public boolean existsGiocatoreRosaByStagioneAndSquadraAndTipologiaRosa(StagioneDto idStagione, SquadraDto idSquadra, Constants.TipologiaRosaEnum tipologiaRosa){
                return giocatoreRosaService.existsByStagioneAndSquadraAndTipologiaRosa(idStagione, idSquadra, tipologiaRosa);
        }

        public Optional<GiocatoreRosaDto> findByIdStagioneAndTipologiaRosa(StagioneDto idStagione, Constants.TipologiaRosaEnum tipologiaRosa){
                return giocatoreRosaService.findByIdStagioneAndTipologiaRosa(idStagione, tipologiaRosa);
        }

        public GiocatoreListoneDto getGiocatoreListoneByIdStagioneAndIdFantagazzettaAndTipologiaListone(StagioneDto idStagione, Integer idFantagazzetta, Constants.TipologiaListoneEnum tipologiaListoneEnum){
                return giocatoreListoneService.findByIdStagioneAndIdFantagazzettaAndTipologiaListone(idStagione, idFantagazzetta, tipologiaListoneEnum).orElseThrow();
        }

        public SituazioneEconomicaInizialeDto getSituazioneEconomicaInizialeBySquadraAndStagione(StagioneDto stagioneDto, SquadraDto squadraDto){
                return situazioneEconomicaInizialeService.getSituazioneEconomicaInizialeBySquadraAndStagione(stagioneDto, squadraDto);
        }

        public List<TokenCredenzialiProjectionDto> fantalegheLogin(CredenzialiDto credenzialiDto) {
                List<TokenCredenzialiProjectionDto> tokenCredenzialiProjectionDtoList = tokenCredenzialiService.getAllTokenCredenzialiInfo(credenzialiDto.getIdUtente());

                if (tokenCredenzialiProjectionDtoList.isEmpty() || tokenCredenzialiProjectionDtoList.stream().anyMatch(token -> !token.getIsValid())) {
                        FantalegheLoginResponse fantalegheLoginResponse = fantalegheService.login(new FantalegheLoginRequest(credenzialiDto.getUserName(), credenzialiDto.getPassword()));
                        return salvaTokenCredenziali(fantalegheLoginResponse, credenzialiDto);
                }

                return tokenCredenzialiProjectionDtoList;
        }

        private List<TokenCredenzialiProjectionDto> salvaTokenCredenziali(FantalegheLoginResponse fantalegheLoginResponse, CredenzialiDto credenzialiDto) {
                List<TokenCredenzialiProjectionDto> tokenCredenzialiProjectionDtoListNew = new ArrayList<>();

                fantalegheLoginResponse.getData().getLeghe().forEach(lega -> {
                        try {
                                NazioneDto nazioneDto = nazioneFromNomeLega(lega.getNome());
                                TokenCredenzialiDto tokenCredenzialiDto = new TokenCredenzialiDto(
                                        null, lega.getJwt(), credenzialiDto.getIdUtente(), nazioneDto, true, LocalDateTime.now()
                                );
                                tokenCredenzialiProjectionDtoListNew.add(tokenCredenzialiService.save(tokenCredenzialiDto));
                        } catch (Exception e) {
                                logger.error("Errore durante l'elaborazione della lega {}: {}", lega.getNome(), e.getMessage());
                        }
                });

                return tokenCredenzialiProjectionDtoListNew;
        }

        /* METODI DI DOMINIO */
        public List<GiocatoreDto> getGiocatori() {
                return giocatoreService.findAll();
        }

        public GiocatoreDto getGiocatoreByIdFantagazzetta(Integer idFantagazzetta) {
                return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
        }

        public Optional<GiocatoreDto> getGiocatoreByNome(String nome) {
                return giocatoreService.findByNome(nome);
        }

        public StadioDto getStadioByLivello(Integer livello) {
                return stadioService.findByLivello(livello).orElseThrow();
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
        public Optional<StagioneDto> getStagioneByAnnoFine(Integer annoFine) { return stagioneService.getStagioneByAnnoFine(annoFine); }
        public StagioneDto getStagioneByAnnoInizio(Integer annoInizio) { return stagioneService.getStagioneByAnnoInizio(annoInizio).orElseThrow(); }

        public List<CompetizioneDto> getCompetizioni() {
                return competizioneService.findAll();
        }

        public CompetizioneDto getCompetizioneBySigla(String sigla) { return competizioneService.findBySigla(sigla).orElseThrow(); }

        public StagioneCompetizioneDto getStagioneCompetizioneByStagioneAndCompetizione(StagioneDto stagioneDto, CompetizioneDto competizioneDto) { return stagioneCompetizioneService.findByStagioneAndCompetizione(stagioneDto, competizioneDto).orElseThrow(); }

        public boolean existsRisultatoCompetizioneByStagioneCompetizioneAndGiornataSerieA(StagioneCompetizioneDto stagioneCompetizioneDto, Integer giornataSerieA) { return risultatoCompetizioneService.existsByStagioneCompetizioneAndGiornataSerieA(stagioneCompetizioneDto, giornataSerieA); }

        public boolean existsOperazioneByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(StagioneDto stagione, SquadraDto squadra, GiocatoreDto giocatore, TipoOperazioneDto tipoOperazione, List<Constants.SessioneMercatoOpAcquistoEnum> sessioneMercato){
                return operazioneService.existsByIdStagioneAndIdSquadraAndIdGiocatoreAndIdTipoOperazioneAndSessioneMercatoIn(stagione, squadra, giocatore, tipoOperazione, sessioneMercato);
        }
        public StagioneDto saveStagione(StagioneDto stagioneDto) {
                return stagioneService.save(stagioneDto);
        }

        public AmpliamentoStadioDto saveAmpliamentoStadio(AmpliamentoStadioDto ampliamentoStadioDto) {
                return ampliamentoStadioService.save(ampliamentoStadioDto);
        }

        public SituazioneEconomicaInizialeDto saveSituazioneEconomicaIniziale(SituazioneEconomicaInizialeDto situazioneEconomicaInizialeDto) {
                return situazioneEconomicaInizialeService.save(situazioneEconomicaInizialeDto);
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

        public GettoneDto saveGettone(GettoneDto gettoneDto) {
                return gettoneService.save(gettoneDto);
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
                SquadraDto squadraDtoA = getSquadraById(trattativaScambio.getIdSquadraA());
                SquadraDto squadraDtoB = getSquadraById(trattativaScambio.getIdSquadraB());
                TrattativaDto trattativaDto = createTrattativa(trattativaScambio.getClausole(), trattativaScambio.getDataTrattativa(), trattativaScambio.getSessioneMercatoTrattiveScambioEnum());

                creaTransazioneTrattativa(trattativaDto, trattativaScambio.getCreditiPagatiSquadraA(), trattativaScambio.getCreditiPagatiSquadraB(), squadraDtoA, squadraDtoB, trattativaScambio.getGettoniSquadraA(), trattativaScambio.getGettoniSquadraB());
                createFidoTrattativa(trattativaScambio.getCreditiPostSquadraA(), trattativaScambio.getCreditiPostSquadraB(),trattativaDto,squadraDtoA,squadraDtoB);
                createBonusTrattativa(trattativaScambio.getBonusPostSquadraA(),trattativaScambio.getBonusPostSquadraB(),trattativaDto,squadraDtoA,squadraDtoB);

                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraA(), giocatoreTrattativaScambio -> {
                        creaDettaglioTrattativa(trattativaDto, squadraDtoA, squadraDtoB, giocatoreTrattativaScambio);
                });
                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriCedutiSquadraB(), giocatoreTrattativaScambio -> {
                        creaDettaglioTrattativa(trattativaDto, squadraDtoB, squadraDtoA, giocatoreTrattativaScambio);
                });
                return trattativaDto;
        }

        private void createBonusTrattativa(Integer creditiBonusSquadraA, Integer creditiBonusSquadraB, TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB) {
                if (creditiBonusSquadraA != null) {
                        createAndSaveBonus(creditiBonusSquadraA, trattativaDto, squadraDtoA, squadraDtoB, Constants.SegnoEnum.DEBITO.getSigla(), Constants.SegnoEnum.CREDITO.getSigla());
                }
                if (creditiBonusSquadraB != null) {
                        createAndSaveBonus(creditiBonusSquadraB, trattativaDto, squadraDtoA, squadraDtoB, Constants.SegnoEnum.CREDITO.getSigla(), Constants.SegnoEnum.DEBITO.getSigla());
                }
        }

        private void createAndSaveBonus(Integer bonus, TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB, String segnoSquadraA, String segnoSquadraB) {
                int creditiMassimaleBonus = bonus;
                BonusTrattativaDto bonusTrattativaDtoSquadraA = new BonusTrattativaDto(null, trattativaDto, squadraDtoA, creditiMassimaleBonus, segnoSquadraA);
                BonusTrattativaDto bonusTrattativaDtoSquadraB = new BonusTrattativaDto(null, trattativaDto, squadraDtoB, creditiMassimaleBonus, segnoSquadraB);
                saveBonusTrattativa(bonusTrattativaDtoSquadraA);
                saveBonusTrattativa(bonusTrattativaDtoSquadraB);
        }

        private TrattativaDto createTrattativa(String clausole, LocalDateTime dataCreazione, Constants.SessioneMercatoTrattiveScambioEnum sessioneMercatoTrattiveScambioEnum){
                StagioneDto stagioneDto = getLastStagione();
                TrattativaDto trattativaDto = new TrattativaDto(null, stagioneDto, clausole, sessioneMercatoTrattiveScambioEnum, dataCreazione);
                return saveTrattativa(trattativaDto);
        }

        private void creaTransazioneTrattativa(TrattativaDto trattativaDto, Integer creditiPagatiSquadraA, Integer creditiPagatiSquadraB, SquadraDto squadraDtoA, SquadraDto squadraDtoB, Integer gettoniPagatiSquadraA, Integer gettoniPagatiSquadraB){
                if(creditiPagatiSquadraA != null && creditiPagatiSquadraA > 0){
                        createTransazioneTrattativa(squadraDtoA, Constants.SegnoEnum.DEBITO, creditiPagatiSquadraA, trattativaDto, gettoniPagatiSquadraA);
                        createTransazioneTrattativa(squadraDtoB, Constants.SegnoEnum.CREDITO, creditiPagatiSquadraA, trattativaDto, gettoniPagatiSquadraB);
                }else if(creditiPagatiSquadraB != null && creditiPagatiSquadraB > 0){
                        createTransazioneTrattativa(squadraDtoB, Constants.SegnoEnum.DEBITO, creditiPagatiSquadraB, trattativaDto, gettoniPagatiSquadraB);
                        createTransazioneTrattativa(squadraDtoA, Constants.SegnoEnum.CREDITO, creditiPagatiSquadraB, trattativaDto, gettoniPagatiSquadraA);
                }
        }
        private void createTransazioneTrattativa(SquadraDto squadraDto, Constants.SegnoEnum segno, Integer crediti, TrattativaDto trattativaDto, Integer gettoniPagati){
                TransazioneTrattativaDto transazioneTrattativaDto = new TransazioneTrattativaDto(null, trattativaDto, squadraDto, crediti, segno.getSigla(), gettoniPagati);
                saveTransazioneTrattativa(transazioneTrattativaDto);
        }

        private void createFidoTrattativa(Integer creditiPostSquadraA, Integer creditiPostSquadraB, TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB){
                if(creditiPostSquadraA > 0 || creditiPostSquadraB > 0) {
                        int creditiFido = creditiPostSquadraA > 0 ? creditiPostSquadraA : creditiPostSquadraB;
                        String segnoFidoSquadraA = Constants.SegnoEnum.DEBITO.getSigla();
                        String segnoFidoSquadraB = Constants.SegnoEnum.CREDITO.getSigla();
                        if (creditiPostSquadraB > 0) {
                                segnoFidoSquadraA = Constants.SegnoEnum.CREDITO.getSigla();
                                segnoFidoSquadraB = Constants.SegnoEnum.DEBITO.getSigla();
                        }
                        FidoDto fidoDtoSquadraA = new FidoDto(null, trattativaDto, squadraDtoA, creditiFido, segnoFidoSquadraA);
                        FidoDto fidoDtoSquadraB = new FidoDto(null, trattativaDto, squadraDtoB, creditiFido, segnoFidoSquadraB);
                        saveFido(fidoDtoSquadraA);
                        saveFido(fidoDtoSquadraB);
                }
        }

        private void creaDettaglioTrattativa(TrattativaDto trattativaDto, SquadraDto squadraDtoCedente, SquadraDto squadraDtoAcquirente, GiocatoreTrattativaScambio giocatoreTrattativaScambio){
                GiocatoreDto giocatoreDto = getGiocatoreByIdFantagazzetta(giocatoreTrattativaScambio.getIdFantagazzetta());
                TipoDettTrattativaDto tipoDettTrattativaDto = getTipoDettTrattativaBySigla(giocatoreTrattativaScambio.getTipoCessione());
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraCedente = creaDettaglioTrattativa(trattativaDto, tipoDettTrattativaDto, giocatoreDto, squadraDtoCedente, Constants.TipoOperazioneEnum.CESSIONE);
                DettaglioTrattativaDto dettaglioTrattativaDtoSquadraAcquirente = creaDettaglioTrattativa(trattativaDto, tipoDettTrattativaDto, giocatoreDto, squadraDtoAcquirente, Constants.TipoOperazioneEnum.ACQUISTO);
                if(tipoDettTrattativaDto.isPrestito()){
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraCedente, giocatoreTrattativaScambio.getPrestito().isRiscatto(), giocatoreTrattativaScambio.getPrestito().isObbligo(), giocatoreTrattativaScambio.getPrestito().getCostoRiscatto());
                        setPrestitoDettTrattativa(dettaglioTrattativaDtoSquadraAcquirente, giocatoreTrattativaScambio.getPrestito().isRiscatto(), giocatoreTrattativaScambio.getPrestito().isObbligo(), giocatoreTrattativaScambio.getPrestito().getCostoRiscatto());
                }
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraCedente);
                saveDettaglioTrattativa(dettaglioTrattativaDtoSquadraAcquirente);
        }

        private void creaDettaglioTrattativaBatch(TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB, GiocatoreTrattativaScambioComposite giocatoreTrattativaScambio){
                giocatoreTrattativaScambio.getSquadraTipoOperazione().forEach((squadraOwnerEnum, tipoOperazioneDto) -> {
                        SquadraDto squadraDto = squadraOwnerEnum.equals(Constants.SquadraOwnerEnum.SQUADRA_A)?squadraDtoA:squadraDtoB;
                        DettaglioTrattativaDto dettaglioTrattativaDto = new DettaglioTrattativaDto(null,trattativaDto,giocatoreTrattativaScambio.getTipoDettTrattativaDto(),squadraDto,giocatoreTrattativaScambio.getGiocatoreDto(), tipoOperazioneDto, null);
                        saveDettaglioTrattativa(dettaglioTrattativaDto);
                });
        }

        private DettaglioTrattativaDto creaDettaglioTrattativa(TrattativaDto trattativaDto, TipoDettTrattativaDto tipoDettTrattativaDto, GiocatoreDto giocatoreDto, SquadraDto squadraDto, Constants.TipoOperazioneEnum tipoOperazione){
                TipoOperazioneDto tipoOperazioneDto = getTipoOperazioneBySigla(tipoOperazione.getSigla());
                return new DettaglioTrattativaDto(null,trattativaDto,tipoDettTrattativaDto,squadraDto,giocatoreDto, tipoOperazioneDto, null);
        }

        private void setPrestitoDettTrattativa(DettaglioTrattativaDto dettaglioTrattativaDto, boolean isRiscatto, boolean isObbligo, Integer costoRiccatto){
                PrestitoDto prestitoDto = new PrestitoDto();
                prestitoDto.setIdDettTrattativa(dettaglioTrattativaDto);
                prestitoDto.setRiscatto(isRiscatto);
                prestitoDto.setObbligo(isObbligo);
                prestitoDto.setCostoRiscatto(costoRiccatto);
                dettaglioTrattativaDto.setIdPrestito(prestitoDto);
        }

        @Transactional
        public void createTrattativaScambioBatch(TrattativaScambioBatch trattativaScambio) {
                SquadraDto squadraDtoA = trattativaScambio.getSquadraDtoA();
                SquadraDto squadraDtoB = trattativaScambio.getSquadraDtoB();
                TrattativaDto trattativaDto = createTrattativa(null, LocalDateTime.now(), trattativaScambio.getSessioneMercatoTrattiveScambioEnum());

                creaTransazioneTrattativa(trattativaDto, trattativaScambio.getMapSquadraCreditiPagati().get(Constants.SquadraOwnerEnum.SQUADRA_A), trattativaScambio.getMapSquadraCreditiPagati().get(Constants.SquadraOwnerEnum.SQUADRA_B), squadraDtoA, squadraDtoB, 0, 0);

                CollectionUtility.safeForEach(trattativaScambio.getListGiocatoriScambiati(), giocatoreTrattativaScambioComposite -> {
                        creaDettaglioTrattativaBatch(trattativaDto, squadraDtoA, squadraDtoB, giocatoreTrattativaScambioComposite);
                });
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
                Constants.TipologiaMercatoFantalegheEnum tipologiaMercato = Constants.TipologiaMercatoFantalegheEnum.valueOf(tipoMercato);
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
                Constants.TipologiaMercatoFantalegheEnum tipologiaMercato = Constants.TipologiaMercatoFantalegheEnum.valueOf(tipoMercato);
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

        public boolean giocatoreExistsByIdFantagazzetta(Integer integer) {
                return giocatoreService.existsByIdFantagazzetta(integer);
        }

        public boolean giocatoreListoneExistsByStagioneAndTipologiaListoneAndIdFantagazzetta(Integer idFantagazzetta, StagioneDto stagioneDto, Constants.TipologiaListoneEnum tipologiaListone){
                return giocatoreListoneService.existsByStagioneAndTipologiaListoneAndIdFantagazzetta(idFantagazzetta,stagioneDto,tipologiaListone);
        }

        @Transactional
        public TrattativaDto createDettagliAggiuntiviTrattativaScambio(DettagliAggiuntiviTrattativaScambio dettagliAggiuntiviTrattativaScambio) {
                SquadraDto squadraDtoA = getSquadraById(dettagliAggiuntiviTrattativaScambio.getIdSquadraA());
                SquadraDto squadraDtoB = getSquadraById(dettagliAggiuntiviTrattativaScambio.getIdSquadraB());
                TrattativaDto trattativaDto = findTrattativaById(dettagliAggiuntiviTrattativaScambio.getIdTrattativa());

                createFidoTrattativa(dettagliAggiuntiviTrattativaScambio.getCreditiPostSquadraA(), dettagliAggiuntiviTrattativaScambio.getCreditiPostSquadraB(),trattativaDto,squadraDtoA,squadraDtoB);
                createBonusTrattativa(dettagliAggiuntiviTrattativaScambio.getBonusPostSquadraA(),dettagliAggiuntiviTrattativaScambio.getBonusPostSquadraB(),trattativaDto,squadraDtoA,squadraDtoB);
                updateGettoniTrattativa(trattativaDto, squadraDtoA,squadraDtoB, dettagliAggiuntiviTrattativaScambio.getGettoniSquadraA(), dettagliAggiuntiviTrattativaScambio.getGettoniSquadraB());
                trattativaDto.setClausole(dettagliAggiuntiviTrattativaScambio.getClausole());
                return saveTrattativa(trattativaDto);
        }

        private void updateGettoniTrattativa(TrattativaDto trattativaDto, SquadraDto squadraDtoA, SquadraDto squadraDtoB, Integer gettoniSquadraA, Integer gettoniSquadraB) {
                updateGettoniTrattativaSquadra(trattativaDto, squadraDtoA, gettoniSquadraA);
                updateGettoniTrattativaSquadra(trattativaDto, squadraDtoB, gettoniSquadraB);
        }

        private void updateGettoniTrattativaSquadra(TrattativaDto trattativaDto, SquadraDto squadraDto, Integer gettoniSpesi){
                if(gettoniSpesi > 0){
                        TransazioneTrattativaDto transazioneTrattativaDto;
                        Optional<TransazioneTrattativaDto> transazioneTrattativaDtoOptional = findTransazioneTrattativaByTrattativaAndSquadra(trattativaDto, squadraDto);
                        if(transazioneTrattativaDtoOptional.isPresent()){
                                transazioneTrattativaDto = transazioneTrattativaDtoOptional.get();
                                transazioneTrattativaDto.setGettoniSpesi(gettoniSpesi);
                        }else{
                             transazioneTrattativaDto = new TransazioneTrattativaDto(null, trattativaDto, squadraDto, 0, Constants.SegnoEnum.DEBITO.getSigla(), gettoniSpesi);
                        }
                        saveTransazioneTrattativa(transazioneTrattativaDto);
                }
        }

        private GettoneDto createGettoni(SquadraDto squadraDto, Integer gettoniAcquistati, LocalDateTime dataAcquisto) {
                GettoneDto gettoneDto = new GettoneDto(null, squadraDto, gettoniAcquistati, dataAcquisto);
                return saveGettone(gettoneDto);
        }

        private TrattativaDto findTrattativaById(Integer idTrattativa) {
                return trattativaService.findTrattativaById(idTrattativa).orElseThrow();
        }

        private Optional<TransazioneTrattativaDto> findTransazioneTrattativaByTrattativaAndSquadra(TrattativaDto trattativaDto, SquadraDto squadraDto) {
                return transazioneTrattativaService.findByTrattativaAndSquadra(trattativaDto, squadraDto);
        }

        @Transactional
        public GettoneDto acquistoPacchettoGettoni(AcquistoGettoni acquistoGettoni) {
                SquadraDto squadraDto = getSquadraById(acquistoGettoni.getIdSquadra());
                return createGettoni(squadraDto, acquistoGettoni.getGettoni(), acquistoGettoni.getDataAcquisto());
        }

        public SituazioneEconomicaInizialeDto impostaFinanzeIniziali(FinanzeIniziali finanzeIniziali) {
                StagioneDto stagioneDto = getStagioneByAnnoInizio(finanzeIniziali.getAnnoInizioStagione());
                SquadraDto squadraDto = getSquadraById(finanzeIniziali.getIdSquadra());
                StadioDto stadioDto = getStadioByLivello(finanzeIniziali.getLivelloStadio());
                return saveSituazioneEconomicaIniziale(new SituazioneEconomicaInizialeDtoBuilder()
                        .setStagione(stagioneDto)
                        .setSquadra(squadraDto)
                        .setStadio(stadioDto)
                        .setCrediti(finanzeIniziali.getCrediti())
                        .setGettoni(finanzeIniziali.getGettoni())
                        .build());
        }

        public AmpliamentoStadioDto createAmpliamentoStadio(DettagliAmpliamentoStadio dettagliAmpliamentoStadio) {
                SquadraDto squadraDto = getSquadraById(dettagliAmpliamentoStadio.getIdSquadra());
                StagioneDto stagioneDto = getLastStagione();
                StadioDto stadioDtoNew = getStadioByLivello(dettagliAmpliamentoStadio.getLivelloStadio());
                SituazioneEconomicaInizialeDto situazioneEconomicaInizialeDto = getSituazioneEconomicaInizialeBySquadraAndStagione(stagioneDto, squadraDto);
                StadioDto stadioDtoOld = situazioneEconomicaInizialeDto.getStadio();
                validateAmpliamentoStadio(stadioDtoNew, stadioDtoOld);
                Integer costo = calcolaCostoAmpliamento(stadioDtoNew, stadioDtoOld);
                return  buildAmpliamentoStadioDto(squadraDto, stagioneDto, stadioDtoNew, costo);
        }

        private Integer calcolaCostoAmpliamento(StadioDto stadioDtoNew, StadioDto stadioDtoOld) {
                if (stadioDtoOld == null) {
                        return stadioDtoNew.getLivello() * 60;
                }
                int differenzaLivello = stadioDtoNew.getLivello() - stadioDtoOld.getLivello();

                if (differenzaLivello <= 0) {
                        return 0; // Nessun costo se il livello non aumenta
                }
                if (differenzaLivello == 1) {
                        return stadioDtoNew.getLivello() > 4 ? 80 : 60;
                }
                if (differenzaLivello == 2) {
                        if (stadioDtoNew.getLivello() < 5) {
                                return differenzaLivello * 60;
                        } else if (stadioDtoNew.getLivello() == 5) {
                                return 140;
                        } else {
                                return differenzaLivello * 80;
                        }
                }
                throw new IllegalArgumentException("Differenza di livello non valida: " + differenzaLivello);
        }

        private void validateAmpliamentoStadio(StadioDto stadioNew, StadioDto stadioDtoOld) {
                if (stadioDtoOld == null) {
                        if (stadioNew.getLivello() > 2) {
                                throw new IllegalStateException("Dal livello 0 è possibile aumentare lo stadio al livello 2 massimo.");
                        }
                }else if (stadioNew.getLivello() - stadioDtoOld.getLivello() > 2) {
                        throw new IllegalStateException("È possibile aumentare di soli 2 livelli alla volta.");
                }
        }

        private AmpliamentoStadioDto buildAmpliamentoStadioDto(SquadraDto squadraDto, StagioneDto stagioneDto, StadioDto stadioDtoNew, Integer costo) {
                AmpliamentoStadioDto ampliamentoStadioDto = new AmpliamentoStadioDto();
                ampliamentoStadioDto.setStadio(stadioDtoNew);
                ampliamentoStadioDto.setSquadra(squadraDto);
                ampliamentoStadioDto.setStagione(stagioneDto);
                ampliamentoStadioDto.setCosto(costo);
                return ampliamentoStadioDto;
        }

        public boolean existsGiocatoreRosaByStagioneAndSquadraAndTipologiaRosaAndGiocatore(StagioneDto stagioneDto, SquadraDto squadraDto, Constants.TipologiaRosaEnum tipologiaRosaEnum, GiocatoreDto giocatoreDto) {
                return giocatoreRosaService.existsByStagioneAndSquadraAndTipologiaRosaAndGiocatore(stagioneDto, squadraDto, tipologiaRosaEnum, giocatoreDto);
        }
}
