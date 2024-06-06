package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.domain.entity.Nazione;
import it.fantacalcio.ffm.repository.NazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NazioneService {

    NazioneRepository nazioneRepository;

    @Autowired
    public NazioneService(NazioneRepository nazioneRepository){
        this.nazioneRepository = nazioneRepository;
    }

    public Nazione findById(Integer id){
        return nazioneRepository.findById(id).orElseThrow();
    }

    public List<Nazione> findAll(Integer id){
        return nazioneRepository.findAll();
    }
}
