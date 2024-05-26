package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}