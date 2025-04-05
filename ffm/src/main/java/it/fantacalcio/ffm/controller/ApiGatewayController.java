package it.fantacalcio.ffm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.domain.model.TrattativaScambio;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheMercato;
import it.fantacalcio.ffm.domain.model.fantaleghe.FantalegheTeam;
import it.fantacalcio.ffm.facade.ApiGatewayFacade;
import it.fantacalcio.ffm.utility.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
@Tag(name = "ApiGatewayController", description = "Gestione delle API di FFM")
public class ApiGatewayController {
    private ApiGatewayFacade apiGatewayFacade;

    public ApiGatewayController(ApiGatewayFacade apiGatewayFacade) {
        this.apiGatewayFacade = apiGatewayFacade;
    }

    @PostMapping(value = "/fantalegheLogin")
    @ResponseBody
    @Operation(summary = "Effettua il login all'applicazione Fantaleghe")
    public List<TokenCredenzialiProjectionDto> login(String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.fantalegheLogin(nickname);
    }

    /* METODI GET */
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

    @GetMapping(value = "/mercati")
    @ResponseBody
    @Operation(summary = "Recupera tutti i mercati chiusi fantaleghe per una determinata nazione e categoria")
    public FantalegheMercato getMercati(@RequestParam String siglaNazione,
                                        @RequestParam String siglaCategoria,
                                        @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getMercatiByNazioneAndCategoria(siglaNazione, siglaCategoria, nickname);
    }

    @GetMapping(value = "/teams")
    @ResponseBody
    @Operation(summary = "Recupera tutti i teams fantagaleghe per una determinata nazione")
    public List<FantalegheTeam> getTeams(@RequestParam String siglaNazione,
                                         @RequestParam(required = false) String nickname) {
        if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
        return apiGatewayFacade.getTeamsByNazione(siglaNazione, nickname);
    }

    /* METODI POST */
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

    @PostMapping(value = "/inizializzaCompetizioni")
    @ResponseBody
    @Operation(summary = "Crea per la stagione in corso tutte le competizioni previste")
    public void inizializzaCompetizioni() {
        apiGatewayFacade.inizializzaCompetizioni();
    }
}