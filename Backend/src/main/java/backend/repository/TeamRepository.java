package backend.repository;

import backend.model.Team;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface TeamRepository extends Neo4jRepository<Team, Long> {
}
