package backend.repository;

import backend.model.Handbook;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface HandbookRepository extends Neo4jRepository<Handbook, String> {
}
