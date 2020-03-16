package backend.repository;

import backend.model.Division;
import org.neo4j.springframework.data.repository.Neo4jRepository;

import java.util.Optional;

public interface DivisionRepository extends Neo4jRepository<Division, Long> {
Optional<Division> findByDivisionName(String divisionName);

}
