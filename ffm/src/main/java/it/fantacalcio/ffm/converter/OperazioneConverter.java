package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.OperazioneDto;
import it.fantacalcio.ffm.domain.entity.Operazione;

import java.util.IdentityHashMap;
import java.util.Map;

public class OperazioneConverter {
    private OperazioneConverter() {}

    public static OperazioneDto toDto(Operazione operazione) {
        return toDto(operazione, new IdentityHashMap<>());
    }

    protected static OperazioneDto toDto(Operazione operazione, Map<Object, Object> context){
        if (operazione == null) {
            return null;
        }
        if (context.containsKey(operazione)) {
            return (OperazioneDto) context.get(operazione);
        }
        OperazioneDto operazioneDto = new OperazioneDto();
        context.put(operazione, operazioneDto);
        operazioneDto.setId(operazione.getId());
        operazioneDto.setIdSquadra(SquadraConverter.toDto(operazione.getIdSquadra()));
        operazioneDto.setIdGiocatore(GiocatoreConverter.toDto(operazione.getIdGiocatore()));
        operazioneDto.setIdTipoOperazione(TipoOperazioneConverter.toDto(operazione.getIdTipoOperazione()));
        operazioneDto.setIdStagione(StagioneConverter.toDto(operazione.getIdStagione()));
        operazioneDto.setTransazione(TransazioneOperazioneConverter.toDto(operazione.getTransazione(), context));
        operazioneDto.setAcquisto(operazione.getAcquisto() != null ? AcquistoConverter.toDto(operazione.getAcquisto(), context) : null);
        operazioneDto.setSvincolo(operazione.getSvincolo() != null ? SvincoloConverter.toDto(operazione.getSvincolo(), context) : null);
        operazioneDto.setDataCreazione(operazione.getDataCreazione());
        return operazioneDto;
    }

    public static Operazione toEntity(OperazioneDto operazioneDto) {
        return toEntity(operazioneDto, new IdentityHashMap<>());
    }

    protected static Operazione toEntity(OperazioneDto operazioneDto, Map<Object, Object> context) {
        if (operazioneDto == null) {
            return null;
        }
        if (context.containsKey(operazioneDto)) {
            return (Operazione) context.get(operazioneDto);
        }
        Operazione operazione = new Operazione();
        context.put(operazioneDto, operazione);
        operazione.setId(operazioneDto.getId());
        operazione.setIdTipoOperazione(TipoOperazioneConverter.toEntity(operazioneDto.getIdTipoOperazione()));
        operazione.setIdSquadra(SquadraConverter.toEntity(operazioneDto.getIdSquadra()));
        operazione.setIdStagione(StagioneConverter.toEntity(operazioneDto.getIdStagione()));
        operazione.setIdGiocatore(GiocatoreConverter.toEntity(operazioneDto.getIdGiocatore()));
        operazione.setTransazione(TransazioneOperazioneConverter.toEntity(operazioneDto.getTransazione(), context));
        if (operazioneDto.getAcquisto() != null) {
            operazione.setAcquisto(AcquistoConverter.toEntity(operazioneDto.getAcquisto(), context));
        }
        if (operazioneDto.getSvincolo() != null) {
            operazione.setSvincolo(SvincoloConverter.toEntity(operazioneDto.getSvincolo(), context));
        }
        operazione.setDataCreazione(operazioneDto.getDataCreazione());
        return operazione;
    }
}
