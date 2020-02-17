package backend.repository;

import backend.model.Division;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface DivisionRepository extends Neo4jRepository<Division, Long> {
}
