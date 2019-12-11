package backend.repository;

import backend.model.Regulation;
import org.springframework.data.repository.CrudRepository;

public interface RegulationRepository extends CrudRepository<Regulation, Long> {
    //Put custom methods for data access
}
