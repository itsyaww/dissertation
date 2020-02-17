package backend.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Firm {

    @Id
    private String firmName;

    @Relationship(type ="HAS",direction =OUTGOING)
    private List<BusinessUnit> businessUnits;

    @PersistenceConstructor
    public Firm(String firmName, List<BusinessUnit> businessUnits) {
        this.firmName = firmName;
        this.businessUnits = businessUnits;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public List<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }
}
