package backend.repository;

import backend.model.Team;
import org.neo4j.springframework.data.repository.Neo4jRepository;

import java.util.Optional;

public interface TeamRepository extends Neo4jRepository<Team, Long> {
Optional<Team> findByTeamName(String teamName);
}
