package it.fantacalcio.ffm.batch.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.fantacalcio.ffm.batch.service.JobService;
import it.fantacalcio.ffm.batch.utility.FileManager;
import it.fantacalcio.ffm.utility.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

import static it.fantacalcio.ffm.utility.Constants.UPLOADS_DIR;

@RestController
@RequestMapping(value = "/ffm/batch")
@Tag(name = "JobLauncherController", description = "Gestione dei batch di FFM")
@RequiredArgsConstructor
public class JobLauncherController{

    private final JobService jobService;
    private final Environment env;
    private final FileManager fileManager;

    @PostMapping(value = "/importListone",  consumes = "multipart/form-data")
    @Operation(summary = "Import del listone tramite excel fantagazzetta, per la stagione(default stagione in corso) in base alla Tipologia Listone(default INIZIALE)")
    public String importListone(@RequestParam("file") MultipartFile file,
                                @RequestParam(value = "skipRows", required = false) Long skipRows,
                                @RequestParam(value = "sheetName", required = false) String sheetName,
                                @RequestParam(value = "annoInizioStagione") Long annoInizioStagione,
                                @Schema(description = "Tipologia Listone",
                                        allowableValues = {"INIZIALE", "STIPENDI", "FINALE"})
                                @RequestParam(value = "tipologiaListone", required = false) String tipologiaListone) {
        try {
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null || skipRows.toString().isEmpty()) {
                skipRows = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.excel-giocatori.skip-rows")));
            }
            if (sheetName == null || sheetName.isEmpty()) {
                sheetName = env.getProperty("ffm.batch.excel-giocatori.sheet-name");
            }
            if(tipologiaListone == null) tipologiaListone = Constants.TipologiaListoneEnum.INIZIALE.getSigla();
            // Salva il file in una directory specifica interna all'applicazione
            String filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);

            // Esegui il job in modo asyncrono
            jobService.runImportListoneJob(filePath, skipRows, sheetName, tipologiaListone, annoInizioStagione);
            return "Job importListone started";
        } catch (FileAlreadyExistsException e) {
            return "Job importListone failed: " + e.getMessage();
        } catch (Exception e) {
            return "Job importListone failed:"+e;
        }
    }

    @PostMapping(value = "/importRoseAndOperazioneInit",  consumes = "multipart/form-data")
    @Operation(summary = "Import delle rose tramite csv fantagazzetta, in base alla sessione di Mercato(default INIZIALE)")
    public String importRoseAndOperazione(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "skipRows", required = false) Long skipRows,
                             @RequestParam(required = false) String sessioneMercato) {
        try {
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null || skipRows.toString().isEmpty()) {
                skipRows = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.csv-rose.skip-rows")));
            }
            if(sessioneMercato == null) sessioneMercato = Constants.SessioneMercatoOpAcquistoEnum.INIZIALE.getSigla();
            // Salva il file in una directory specifica interna all'applicazione
            String filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);

            // Esegui il job in modo asyncrono
            jobService.runImportRoseAndOperazioneJob(filePath, skipRows, sessioneMercato);
            return "Job importRoseAndOperazione started!";
        } catch (FileAlreadyExistsException e) {
            return "Job importRoseAndOperazione failed: " + e.getMessage();
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importRoseAndOperazioneWebApi")
    @Operation(summary = "Import delle rose tramite api fantagazzetta, in base alla sessione di Mercato(default INIZIALE) della stagione con settaggio ID Fantaleghe")
    public String importRoseAndOperazioneWebApi(@Schema(description = "Sigla Nazione",
                                                            allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                                    @RequestParam String siglaNazione,
                                                @Schema(description = "Sessione acquisto",
                                                        allowableValues = {"PREASTA", "INIZIALE", "AGOSTO", "SETTEMBRE", "FEBBRAIO", "FINALE"})
                                                @RequestParam(required = false) String sessioneMercato,
                                   @RequestParam(required = false) String nickname) {
        try{
             if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
             if(sessioneMercato == null) sessioneMercato = Constants.SessioneMercatoOpAcquistoEnum.INIZIALE.getSigla();
            // Esegui il job in modo asyncrono
            jobService.runImportRoseAndOperazioneWebApiJob(siglaNazione, sessioneMercato, nickname);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importRoseWebApiJob")
    @Operation(summary = "Import delle rose tramite api fantagazzetta, in base alla tipologiaRosa")
    public String importRoseWebApiJob(@Schema(description = "Sigla Nazione",
                                                  allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                          @RequestParam String siglaNazione,
                                      @RequestParam
                                      @Schema(description = "Tipologia della rosa",
                                              allowableValues = {"PREASTA", "INIZIALE", "POST_LISTONE", "STIPENDI_SETTEMBRE", "STIPENDI_FEBBRAIO", "FINALE", "MANAGERIALE"})
                                      String tipologiaRosa,
                                      @RequestParam(required = false) String nickname) {
        try{
            if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;

            // Esegui il job in modo asyncrono
            jobService.runImportRoseWebApiJob(siglaNazione, tipologiaRosa, nickname);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importSquadreWebApi")
    @Operation(summary = "Import delle squadre tramite api fantagazzetta per nazione")
    public String importSquadreWebApi(@Schema(description = "Sigla Nazione",
                                                  allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                          @RequestParam String siglaNazione,
                                      @RequestParam(required = false) String nickname) {
        try{
            if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
            // Esegui il job in modo asyncrono
            jobService.runImportSquadreWebApiJob(siglaNazione, nickname);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importOperazioniMercatoWebApi")
    @Operation(summary = "Import delle operazioni fantaleghe di un mercato per una determinata nazione e categoria")
    public String importOperazioniMercatoWebApi(@Schema(description = "Sigla Nazione",
                                                            allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                                    @RequestParam String siglaNazione,
                                                @Schema(description = "Sigla Categoria",
                                                        allowableValues = {"A", "B", "C"})
                                                @RequestParam String siglaCategoria,
                                                @RequestParam String idMercato,
                                                @Schema(description = "Tipologia Mercato",
                                                        allowableValues = {"SVINCOLI", "SVINCOLI_ASTA", "ASTA", "BUSTE"})
                                                    @RequestParam String tipoMercato,
                                                @Schema(description = "Sessione Mercato",
                                                        allowableValues = {"PREASTA", "INIZIALE", "AGOSTO", "SETTEMBRE","FEBBRAIO", "FINALE"})
                                                    @RequestParam String sessioneMercato,
                                                @RequestParam(required = false) String nickname) {
        try{
            if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
            // Esegui il job in modo asyncrono
            jobService.runImportOperazioniMercatoWebApiJob(siglaNazione, siglaCategoria, idMercato, tipoMercato, sessioneMercato, nickname);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importTrattativeScambioWebApi")
    @Operation(summary = "Import delle trattative scambio fantaleghe di un mercato per una determinata nazione e categoria")
    public String importTrattativeScambioWebApi(@Schema(description = "Sigla Nazione",
                                                            allowableValues = {"ING", "FRA", "ITA", "GER", "SPA", "EN", "EC", "EE", "ES", "BEN", "EXU", "EXJ", "ROU", "SCA"})
                                                    @RequestParam String siglaNazione,
                                                @Schema(description = "Sigla Categoria",
                                                        allowableValues = {"A", "B", "C"})
                                                @RequestParam String siglaCategoria,
                                                @RequestParam String idMercato,
                                                @Schema(description = "Tipologia Mercato",
                                                        allowableValues = {"SCAMBI"})
                                                    @RequestParam String tipoMercato,
                                                @Schema(description = "Sessione Mercato",
                                                        allowableValues = {"ESTIVA", "SETTEMBRE", "OTTOBRE", "NOVEMBRE","DICEMBRE", "GENNAIO", "FEBBRAIO"})
                                                    @RequestParam String sessioneMercato,
                                                @RequestParam(required = false) String nickname) {
        try{
            if(nickname == null) nickname = Constants.ADMIN_A_NICKNAME;
            // Esegui il job in modo asyncrono
            jobService.runImportTrattativeScambioWebApiJob(siglaNazione, siglaCategoria, idMercato, tipoMercato, sessioneMercato, nickname);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }

    @PostMapping(value = "/importRisultatiCompetizione",  consumes = "multipart/form-data")
    @Operation(summary = "Import dei risultati di una competizione tramite excel fantagazzetta")
    public String importRisultatiCompetizione(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "skipRows", required = false) Long skipRows,
                                              @RequestParam(value = "sheetName", required = false) String sheetName,
                                              @RequestParam(value = "giornata", required = false) Long giornata,
                                              @RequestParam(value = "competizione") String competizione,
                                              @RequestParam(value = "faseCompetizione") String faseCompetizione) {
        // Salva il file in una directory specifica interna all'applicazione
        String filePath = null;
        try {
            filePath = fileManager.copyToInDirectory(file, UPLOADS_DIR);
            // Recupera i valori predefiniti da application.yml se i parametri non sono forniti o sono vuoti
            if (skipRows == null) {
                skipRows = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.excel-risultati-competizione.skip-rows")));
            }
            if (sheetName == null || sheetName.isEmpty()) {
                sheetName = env.getProperty("ffm.batch.excel-risultati-competizione.sheet-name");
            }
            if (giornata == null) {
                giornata = Long.parseLong(Objects.requireNonNull(env.getProperty("ffm.batch.excel-risultati-competizione.giornata")));
            }

            // Esegui il job in modo asyncrono
            jobService.runImportRisultatiCompetizione(filePath, skipRows, sheetName, competizione, faseCompetizione, giornata);
            return "Job importRisultatiCompetizione started";
        } catch (Exception e) {
            fileManager.deleteFile(filePath);
            return "Job importRisultatiCompetizione failed:"+e;
        }
    }

    @PostMapping(value = "/importDriveCsvJob")
    @Operation(summary = "Import del drive csv")
    public String importDriveCsvJob(@RequestParam
                                    @Schema(description = "Tipologia sheet",
                                          allowableValues = {"SQUADRA", "LISTONE"})
                                    String tipologiaSheet,
                                    @RequestParam
                                    @Schema(description = "Tipologia Rosa",
                                            allowableValues = {"PREASTA", "INIZIALE", "POST_LISTONE"})
                                    String tipologiaRosa,
                                    @RequestParam String idFile,
                                    @RequestParam String idSheet,
                                    @RequestParam(required = false, defaultValue = "0") Long idSquadra,
                                    @RequestParam(required = false, defaultValue = "0") Long linesToSkip,
                                    @RequestParam(required = false, defaultValue = "0") Long linesToRead) {
        try{
            // Esegui il job in modo asyncrono
            jobService.runImportDriveCsvJob(idFile, tipologiaSheet, tipologiaRosa, idSheet, idSquadra, linesToSkip, linesToRead);
            return "Job importRoseAndOperazioneApi started!";
        } catch (Exception e) {
            return "Job importRoseAndOperazione failed:"+e;
        }
    }
}
