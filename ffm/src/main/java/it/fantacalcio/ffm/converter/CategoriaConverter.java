package it.fantacalcio.ffm.converter;

import it.fantacalcio.ffm.domain.dto.CategoriaDto;
import it.fantacalcio.ffm.domain.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaConverter {
    private CategoriaConverter() {}

    public static CategoriaDto toDto(Categoria categoria){
        return new CategoriaDto(categoria.getId(), categoria.getIdFantagazzetta(), categoria.getNome(),categoria.getRuolo(), categoria.getQuotazione());
    }

//    public static Categoria toEntity(CategoriaDto categoria){
//        return new Categoria(categoria.getId(), categoria.getIdFantagazzetta(), categoria.getNome(),categoria.getRuolo(), categoria.getQuotazione());
//    }
}
