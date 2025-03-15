package it.fantacalcio.ffm.service;

import it.fantacalcio.ffm.cache.CategoriaCache;
import it.fantacalcio.ffm.converter.CategoriaConverter;
import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import it.fantacalcio.ffm.domain.entity.Categoria;
import it.fantacalcio.ffm.repository.CategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaCache categoriaCache;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaCache categoriaCache) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaCache = categoriaCache;
    }

    @PostConstruct
    public void init() {
        List<CategoriaDto> categorie = findAll();
        categoriaCache.setCategorie(categorie);
    }

    public Optional<CategoriaDto> findById(Integer id){
        return categoriaCache.getCategorie().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .or(() -> categoriaRepository.findById(id).map(categoria -> {
                    CategoriaDto categoriaDto = CategoriaConverter.toDto(categoria);
                    categoriaCache.addCategoria(categoriaDto);
                    return categoriaDto;
                }));
    }

    public Optional<CategoriaDto> findBySigla(String sigla){
        return categoriaCache.getCategorie().stream()
                .filter(c -> c.getSigla().equals(sigla))
                .findFirst()
                .or(() -> categoriaRepository.findBySigla(sigla).map(categoria -> {
                    CategoriaDto categoriaDto = CategoriaConverter.toDto(categoria);
                    categoriaCache.addCategoria(categoriaDto);
                    return categoriaDto;
                }));
    }

    public List<CategoriaDto> findAll(){
        if (categoriaCache.isEmpty()) {
            List<CategoriaDto> categorie = categoriaRepository.findAll().stream()
                    .map(CategoriaConverter::toDto)
                    .toList();
            categoriaCache.setCategorie(categorie);
        }
        return categoriaCache.getCategorie();
    }

    public CategoriaDto save(CategoriaDto categoriaDto){
        Categoria categoriaInserita = categoriaRepository.save(CategoriaConverter.toEntity(categoriaDto));
        return CategoriaConverter.toDto(categoriaInserita);
    }
}
