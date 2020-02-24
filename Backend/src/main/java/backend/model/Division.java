package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

public class Division {

    @Id @GeneratedValue
    Long divisionID;

    @Property("name")
    String divisionName;

    @Relationship(type="RUNS", direction = OUTGOING)
    List<BusinessUnit> businessUnits;

    @PersistenceConstructor
    public Division(Long divisionID, String divisionName, List<BusinessUnit> businessUnits) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.businessUnits = businessUnits;
    }

    public Long getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Long divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public List<BusinessUnit> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<BusinessUnit> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public Boolean addBusinessUnit(BusinessUnit division)
    {
        return businessUnits.add(division);
    }

    public void removeBusinessUnit(Long buId)
    {
        businessUnits.forEach(businessUnit -> {
            if(businessUnit.getBuID().equals(buId))
            {
                System.out.println("Removing Business Unit " + buId.toString() + " from list");
                businessUnits.remove(businessUnit);
            }
        });
    }
}
