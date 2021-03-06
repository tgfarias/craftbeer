package br.com.fctecno.craftbeer.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fctecno.craftbeer.domain.model.Beer;


@Repository
public interface BeerRepository extends JpaRepository<Beer, Long>{

}
