package backend.repository;

import backend.model.Firm;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface FirmRepository extends Neo4jRepository<Firm, String> {
}
