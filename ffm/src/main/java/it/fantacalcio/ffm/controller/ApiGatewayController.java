package it.fantacalcio.ffm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.dto.SquadraDto;
import it.fantacalcio.ffm.domain.dto.UtenteDto;
import it.fantacalcio.ffm.service.GiocatoreService;
import it.fantacalcio.ffm.service.SquadraService;
import it.fantacalcio.ffm.service.UtenteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
@Tag(name = "ApiGatewayController", description = "Gestione delle API di FFM")
public class ApiGatewayController {
    private GiocatoreService giocatoreService;
    private UtenteService utenteService;
    private SquadraService squadraService;

    public ApiGatewayController(GiocatoreService giocatoreService, UtenteService utenteService, SquadraService squadraService) {
        this.giocatoreService = giocatoreService;
        this.utenteService = utenteService;
        this.squadraService = squadraService;
    }

    @GetMapping(value = "/giocatori")
    @ResponseBody
    @Operation(summary = "Recupera tutti i giocatori")
    public List<GiocatoreDto> getGiocatori() {
        return giocatoreService.findAll();
    }

    @GetMapping(value = "/giocatore/{idFantagazzetta}")
    @ResponseBody
    @Operation(summary = "Recupera un giocatore per ID Fantagazzetta")
    public GiocatoreDto getGiocatore(@PathVariable Integer idFantagazzetta) {
        return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
    }

    @PostMapping(value = "/utente")
    @ResponseBody
    @Operation(summary = "Crea un nuovo utente")
    public UtenteDto createUtente(@RequestBody UtenteDto utenteDto) {
        return utenteService.save(utenteDto);
    }

    @PostMapping(value = "/squadra")
    @ResponseBody
    @Operation(summary = "Crea una nuova squadra associandola ad un utente se passato il relativo ID")
    public SquadraDto createSquadra(@RequestBody SquadraDto squadraDto, @RequestParam(required = false) Integer utenteId) {
        if (utenteId != null) {
            return squadraService.save(squadraDto, utenteId);
        } else {
            return squadraService.save(squadraDto);
        }
    }
}