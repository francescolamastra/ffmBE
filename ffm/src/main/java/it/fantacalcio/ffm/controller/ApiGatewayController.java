package it.fantacalcio.ffm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.domain.dto.*;
import it.fantacalcio.ffm.proxy.ApiGatewayProxy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
@Tag(name = "ApiGatewayController", description = "Gestione delle API di FFM")
public class ApiGatewayController {
    private ApiGatewayProxy apiGatewayProxy;

    public ApiGatewayController(ApiGatewayProxy apiGatewayProxy) {
        this.apiGatewayProxy = apiGatewayProxy;
    }

    @GetMapping(value = "/giocatori")
    @ResponseBody
    @Operation(summary = "Recupera tutti i giocatori")
    public List<GiocatoreDto> getGiocatori() {
        return apiGatewayProxy.getGiocatori();
    }

    @GetMapping(value = "/giocatore/{idFantagazzetta}")
    @ResponseBody
    @Operation(summary = "Recupera un giocatore per ID Fantagazzetta")
    public GiocatoreDto getGiocatore(@PathVariable Integer idFantagazzetta) {
        return apiGatewayProxy.getGiocatore(idFantagazzetta);
    }

    @GetMapping(value = "/utenti")
    @ResponseBody
    @Operation(summary = "Recupera tutti gli utenti")
    public List<UtenteDto> getUtenti() {
        return apiGatewayProxy.getUtenti();
    }

    @GetMapping(value = "/squadre")
    @ResponseBody
    @Operation(summary = "Recupera tutte le squadre")
    public List<SquadraDto> getSquadre() {
        return apiGatewayProxy.getSquadre();
    }

    @GetMapping(value = "/nazioni")
    @ResponseBody
    @Operation(summary = "Recupera tutte le nazioni")
    public List<NazioneDto> getNazioni() {
        return apiGatewayProxy.getNazioni();
    }

    @GetMapping(value = "/stagioni")
    @ResponseBody
    @Operation(summary = "Recupera tutte le stagioni")
    public List<StagioneDto> getStagioni() {
        return apiGatewayProxy.getStagioni();
    }

    @PostMapping(value = "/utente")
    @ResponseBody
    @Operation(summary = "Crea un nuovo utente")
    public UtenteDto createUtente(@RequestBody UtenteDto utenteDto) {
        return apiGatewayProxy.createUtente(utenteDto);
    }

    @PostMapping(value = "/squadra")
    @ResponseBody
    @Operation(summary = "Crea una nuova squadra associandola ad un utente se passato il relativo ID")
    public SquadraDto createSquadra(@RequestBody SquadraDto squadraDto, @RequestParam(required = false) Integer utenteId) {
        return apiGatewayProxy.createSquadra(squadraDto,utenteId);
    }

    @PostMapping(value = "/stagione")
    @ResponseBody
    @Operation(summary = "Crea una nuova Stagione")
    public StagioneDto createStagione(@RequestBody StagioneDto stagioneDto) {
        return apiGatewayProxy.createStagione(stagioneDto);
    }

    @PostMapping(value = "/operazione")
    @ResponseBody
    @Operation(summary = "Crea una nuova Operazione per una specifica squadra e stagione")
    public OperazioneDto createOperazione(@RequestBody OperazioneDto operazioneDto) {
        return apiGatewayProxy.createOperazione(operazioneDto);
    }
}