package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.converter.CategoriaConverter;
import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import it.fantacalcio.ffm.domain.entity.Categoria;
import it.fantacalcio.ffm.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public Optional<CategoriaDto> findById(Integer id){
        return categoriaRepository.findById(id).map(CategoriaConverter::toDto);
    }

    public List<CategoriaDto> findAll(){
        return categoriaRepository.findAll().stream().map(CategoriaConverter::toDto).toList();
    }

    public CategoriaDto save(CategoriaDto categoriaDto){
        Categoria categoriaInserita = categoriaRepository.save(CategoriaConverter.toEntity(categoriaDto));
        return CategoriaConverter.toDto(categoriaInserita);
    }
}
