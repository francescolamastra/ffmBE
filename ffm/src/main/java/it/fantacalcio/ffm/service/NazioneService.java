package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.NazioneConverter;
import it.fantacalcio.ffm.domain.dto.NazioneDto;
import it.fantacalcio.ffm.domain.entity.Nazione;
import it.fantacalcio.ffm.repository.NazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NazioneService {

    NazioneRepository nazioneRepository;

    @Autowired
    public NazioneService(NazioneRepository nazioneRepository){
        this.nazioneRepository = nazioneRepository;
    }

    public Optional<NazioneDto> findById(Integer id){
        return nazioneRepository.findById(id).map(NazioneConverter::toDto);
        }

    public List<NazioneDto> findAll(){
        return nazioneRepository.findAll().stream().map(NazioneConverter::toDto).toList();
    }

    public NazioneDto save(NazioneDto nazioneDto){
        Nazione nazioneInserita = nazioneRepository.save(NazioneConverter.toEntity(nazioneDto));
        return NazioneConverter.toDto(nazioneInserita);
    }
}
