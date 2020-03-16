package backend.repository;

import backend.model.BusinessUnit;
import org.neo4j.springframework.data.repository.Neo4jRepository;

import java.util.Optional;

public interface BusinessUnitRepository extends Neo4jRepository<BusinessUnit, Long> {
    Optional<BusinessUnit> findByBuName(String buName);
}
