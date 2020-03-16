package backend.repository;

import backend.model.Module;
import org.neo4j.springframework.data.repository.Neo4jRepository;

import java.util.Optional;

public interface ModuleRepository extends Neo4jRepository<Module, String> {

    Optional<Module> findByModuleCode(String moduleCode);
   // boolean existsByModuleCode(String moduleCode);
}
