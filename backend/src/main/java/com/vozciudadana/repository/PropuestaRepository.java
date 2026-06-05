package com.vozciudadana.repository;

import com.vozciudadana.model.EstadoPropuesta;
import com.vozciudadana.model.Propuesta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropuestaRepository extends MongoRepository<Propuesta, String> {
    List<Propuesta> findByEstado(EstadoPropuesta estado);
}
