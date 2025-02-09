package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import it.fantacalcio.ffm.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaConverter {
    private CategoriaConverter() {}

    public static CategoriaDto toDto(Categoria categoria){
        return new CategoriaDto(categoria.getId(), categoria.getDescrizione(), categoria.getSigla());
    }

    public static Categoria toEntity(CategoriaDto categoria) {
        Categoria categoriaEntity = new Categoria();
        categoriaEntity.setId(categoria.getId());
        categoriaEntity.setDescrizione(categoria.getDescrizione());
        categoriaEntity.setSigla(categoria.getSigla());
        return categoriaEntity;
    }
}
