package backend.repository;

import backend.model.BusinessUnit;
import org.neo4j.springframework.data.repository.Neo4jRepository;

public interface BusinessUnitRepository extends Neo4jRepository<BusinessUnit, Long> {
}
