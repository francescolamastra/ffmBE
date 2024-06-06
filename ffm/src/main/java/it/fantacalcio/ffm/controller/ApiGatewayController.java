package it.fantacalcio.ffm.controller;

import it.fantacalcio.ffm.domain.dto.GiocatoreDto;
import it.fantacalcio.ffm.domain.entity.Giocatore;
import it.fantacalcio.ffm.service.GiocatoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/ffm/api")
public class ApiGatewayController {
    private GiocatoreService giocatoreService;

    public ApiGatewayController(GiocatoreService giocatoreService) {
        this.giocatoreService = giocatoreService;
    }

    @GetMapping(value = "/giocatori")
    public List<GiocatoreDto> getGiocatori() {
        return giocatoreService.findAll();
    }
}
