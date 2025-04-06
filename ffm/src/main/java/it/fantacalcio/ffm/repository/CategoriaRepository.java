package it.fantacalcio.ffm.repository;

import it.fantacalcio.ffm.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findBySiglaIgnoreCase(String sigla);
}