package backend.repository;

import backend.model.Regulation;
import org.neo4j.springframework.data.repository.Neo4jRepository;

import java.util.Optional;

public interface RegulationRepository extends Neo4jRepository<Regulation, Long> {
    //Put custom methods for data access

    Optional<Regulation> countDistinctByRegulationID(Long regulationID);

    /*Optional<Regulation> countDistinctBySupervisoryBody(String supervisoryBody);

    Optional<Regulation> countDistinctBySupervisoryCountry(String supervisoryCountry);*/
}
