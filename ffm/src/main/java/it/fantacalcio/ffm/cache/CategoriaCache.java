package it.fantacalcio.ffm.cache;

import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class CategoriaCache {
    private final List<CategoriaDto> categorie = new CopyOnWriteArrayList<>();

    public void setCategorie(List<CategoriaDto> categorie) {
        this.categorie.clear();
        this.categorie.addAll(categorie);
    }

    public void addCategoria(CategoriaDto categoriaDto) {
        this.categorie.add(categoriaDto);
    }

    public boolean isEmpty(){
        return this.categorie.isEmpty();
    }
}
