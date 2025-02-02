package it.fantacalcio.ffm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.service.GiocatoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
@Tag(name = "ApiGatewayController", description = "Gestione delle API di FFM")
public class ApiGatewayController {
    private GiocatoreService giocatoreService;

    public ApiGatewayController(GiocatoreService giocatoreService) {
        this.giocatoreService = giocatoreService;
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
}
