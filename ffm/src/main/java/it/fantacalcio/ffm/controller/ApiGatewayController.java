package it.fantacalcio.ffm.controller;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.service.GiocatoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
public class ApiGatewayController {
    private GiocatoreService giocatoreService;

    public ApiGatewayController(GiocatoreService giocatoreService) {
        this.giocatoreService = giocatoreService;
    }

    @GetMapping(value = "/giocatori")
    @ResponseBody
    public List<GiocatoreDto> getGiocatori() {
        return giocatoreService.findAll();
    }

    @GetMapping(value = "/giocatore/{idFantagazzetta}")
    @ResponseBody
    public GiocatoreDto getGiocatore(@PathVariable Integer idFantagazzetta) {
        return giocatoreService.findByIdFantagazzetta(idFantagazzetta).orElseThrow();
    }
}
