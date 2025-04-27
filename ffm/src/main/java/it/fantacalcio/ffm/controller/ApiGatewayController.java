package it.fantacalcio.ffm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.DettagliAggiuntiviTrattativaScambio;
import it.fantacalcio.ffm.domain.model.FinanzeIniziali;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.domain.model.fantaleghe.*;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
@Tag(name = "ApiGatewayController", description = "Gestione delle API di FFM")
@RequiredArgsConstructor
public class ApiGatewayController {
    private final ApiGatewayFacade apiGatewayFacade;

    /* SEZIONE COMUNICAZIONE API FANTALEGHE */
    @PostMapping(value = "/fantalegheLogin")
    @ResponseBody
    @Operation(summary = "Effettua il login all'applicazione Fantaleghe")
    public List<TokenCredenzialiProjectionDto> login(String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.fantalegheLogin(nickname);
    }

    @GetMapping(value = "/mercati")
    @ResponseBody
    @Operation(summary = "Recupera tutti i mercati chiusi fantaleghe per una determinata nazione e categoria")
    public FantalegheMercato getMercati(@Schema(description = "Sigla Nazione",
                                                    allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                        @RequestParam String siglaNazione,
                                        @Schema(description = "Sigla Categoria",
                                                allowableValues = {"A", "B", "C"})
                                        @RequestParam String siglaCategoria,
                                        @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getMercatiByNazioneAndCategoria(siglaNazione, siglaCategoria, nickname);
    }

    @GetMapping(value = "/operazioniMercato")
    @ResponseBody
    @Operation(summary = "Recupera tutte le operazioni fantaleghe di un mercato per una determinata nazione e categoria")
    public FantalegheOperazioneMercato getOperazioniMercato(@Schema(description = "Sigla Nazione",
                                                                        allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                                                @RequestParam String siglaNazione,
                                                            @Schema(description = "Sigla Categoria",
                                                                    allowableValues = {"A", "B", "C"})
                                                            @RequestParam String siglaCategoria,
                                                            @RequestParam String idMercato,
                                                            @Schema(description = "Tipologia Mercato",
                                                                    allowableValues = {"SVINCOLI", "SVINCOLI_ASTA", "ASTA", "BUSTE"})
                                                                @RequestParam String tipoMercato,
                                                            @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getOperazioniMercatoByNazioneAndCategoria(siglaNazione, siglaCategoria, idMercato, tipoMercato, nickname);
    }

    @GetMapping(value = "/trattativeScambio")
    @ResponseBody
    @Operation(summary = "Recupera tutte le trattative scambio fantaleghe di un mercato per una determinata nazione e categoria")
    public FantalegheTrattativeScambio getTrattativeScambio(@Schema(description = "Sigla Nazione",
                                                                        allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                                                @RequestParam String siglaNazione,
                                                            @Schema(description = "Sigla Categoria",
                                                                    allowableValues = {"A", "B", "C"})
                                                            @RequestParam String siglaCategoria,
                                                            @RequestParam String idMercato,
                                                            @Schema(description = "Tipologia Mercato",
                                                                    allowableValues = {"SCAMBI"})
                                                                @RequestParam String tipoMercato,
                                                            @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getTrattativeScambioByNazioneAndCategoria(siglaNazione, siglaCategoria, idMercato, tipoMercato, nickname);
    }

    @GetMapping(value = "/teams")
    @ResponseBody
    @Operation(summary = "Recupera tutti i teams fantaleghe per una determinata nazione")
    public List<FantalegheTeam> getTeams(@Schema(description = "Sigla Nazione",
                                                     allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                             @RequestParam String siglaNazione,
                                         @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getTeamsByNazione(siglaNazione, nickname);
    }

    /* METODI GET DOMINIO */
    @GetMapping(value = "/giocatori")
    @ResponseBody
    @Operation(summary = "Recupera tutti i giocatori")
    public List<GiocatoreDto> getGiocatori() {
        return apiGatewayFacade.getGiocatori();
    }

    @GetMapping(value = "/giocatore/{idFantagazzetta}")
    @ResponseBody
    @Operation(summary = "Recupera un giocatore per ID Fantagazzetta")
    public GiocatoreDto getGiocatore(@PathVariable Integer idFantagazzetta) {
        return apiGatewayFacade.getGiocatoreByIdFantagazzetta(idFantagazzetta);
    }

    @GetMapping(value = "/utenti")
    @ResponseBody
    @Operation(summary = "Recupera tutti gli utenti")
    public List<UtenteDto> getUtenti() {
        return apiGatewayFacade.getUtenti();
    }

    @GetMapping(value = "/squadre")
    @ResponseBody
    @Operation(summary = "Recupera tutte le squadre")
    public List<SquadraDto> getSquadre() {
        return apiGatewayFacade.getSquadre();
    }

    @GetMapping(value = "/nazioni")
    @ResponseBody
    @Operation(summary = "Recupera tutte le nazioni")
    public List<NazioneDto> getNazioni() {
        return apiGatewayFacade.getNazioni();
    }

    @GetMapping(value = "/stagioni")
    @ResponseBody
    @Operation(summary = "Recupera tutte le stagioni")
    public List<StagioneDto> getStagioni() {
        return apiGatewayFacade.getStagioni();
    }

    @GetMapping(value = "/categorie")
    @ResponseBody
    @Operation(summary = "Recupera tutte le categorie")
    public List<CategoriaDto> getCategorie() {
        return apiGatewayFacade.getCategorie();
    }

    /* METODI POST DOMINIO */
    @PostMapping(value = "/utente")
    @ResponseBody
    @Operation(summary = "Crea un nuovo utente")
    public UtenteDto createUtente(@RequestBody UtenteDto utenteDto) {
        return apiGatewayFacade.saveUtente(utenteDto);
    }

    @PostMapping(value = "/squadra")
    @ResponseBody
    @Operation(summary = "Crea una nuova squadra associandola ad un utente se passato il relativo ID")
    public SquadraDto createSquadra(@RequestBody SquadraDto squadraDto, @RequestParam(required = false) Integer utenteId) {
        return apiGatewayFacade.saveSquadra(squadraDto,utenteId);
    }

    @PostMapping(value = "/stagione")
    @ResponseBody
    @Operation(summary = "Crea una nuova Stagione")
    public StagioneDto createStagione(@RequestBody StagioneDto stagioneDto) {
        return apiGatewayFacade.saveStagione(stagioneDto);
    }

    @PostMapping(value = "/operazione")
    @ResponseBody
    @Operation(summary = "Crea una nuova Operazione per una specifica squadra e stagione")
    public OperazioneDto createOperazione(@RequestBody OperazioneDto operazioneDto) {
        return apiGatewayFacade.saveOperazione(operazioneDto);
    }

    @PostMapping(value = "/trattativaScambio")
    @ResponseBody
    @Operation(summary = "Crea una nuova trattativa di scambio tra 2 squadre")
    public TrattativaDto createTrattativaScambio(@RequestBody TrattativaScambio trattativaScambio) {
        return apiGatewayFacade.createTrattativaScambio(trattativaScambio);
    }

    @PostMapping(value = "/aggiungiDettagliTrattativaScambio")
    @ResponseBody
    @Operation(summary = "Aggiunge i dettagli (Bonus, Fido, Gettoni) per una specifica trattativa scambio")
    public TrattativaDto createDettagliAggiuntiviTrattativaScambio(@RequestBody DettagliAggiuntiviTrattativaScambio dettagliAggiuntiviTrattativaScambio) {
        return apiGatewayFacade.createDettagliAggiuntiviTrattativaScambio(dettagliAggiuntiviTrattativaScambio);
    }

    @PostMapping(value = "/inizializzaCompetizioni")
    @ResponseBody
    @Operation(summary = "Crea per la stagione in corso tutte le competizioni previste")
    public void inizializzaCompetizioni() {
        apiGatewayFacade.inizializzaCompetizioni();
    }

    @PostMapping(value = "/acquistoPacchettoGettoni")
    @ResponseBody
    @Operation(summary = "Registra l'acquisto di N gettoni per una squadra")
    public GettoneDto acquistoPacchettoGettoni(@RequestBody AcquistoGettoni acquistoGettoni) {
        return apiGatewayFacade.acquistoPacchettoGettoni(acquistoGettoni);
    }

    @PostMapping(value = "/impostaFinanzeIniziali")
    @ResponseBody
    @Operation(summary = "Imposta la situzione economica iniziale di una squadra (stadio, crediti, gettoni)")
    public SituazioneEconomicaInizialeDto impostaFinanzeIniziali(@RequestBody FinanzeIniziali finanzeIniziali) {
        return apiGatewayFacade.impostaFinanzeIniziali(finanzeIniziali);
    }
}