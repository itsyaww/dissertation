package backend.repository;

import backend.model.Module;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface ModuleRepository extends Neo4jRepository<Module, String> {
}
