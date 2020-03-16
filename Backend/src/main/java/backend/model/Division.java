package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

public class Division {

    @Id @GeneratedValue
    Long divisionID;

    @Property("name")
    String divisionName;

    @Relationship(type="RUNS", direction = OUTGOING)
    List<BusinessUnit> businessUnits = new ArrayList<>();

   /* @PersistenceConstructor
    public Division(Long divisionID, String divisionName, List<BusinessUnit> businessUnits) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.businessUnits = businessUnits;
    }*/

   public Division(){};

   public Division(Division division)
   {
       this.divisionID = null;
       this.divisionName = division.getDivisionName();
       this.businessUnits = division.getBusinessUnits();
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

    public Division addBusinessUnit(BusinessUnit businessUnit)
    {
        businessUnits.add(createBusinessUnit(businessUnit));
        return this;
    }

    private static BusinessUnit createBusinessUnit(BusinessUnit businessUnit){

        Assert.notNull(businessUnit, "Business unit must not be null");
        Assert.notNull(businessUnit.getBuName(), "BU Name, must not be null");

        BusinessUnit newBusinessUnit = new BusinessUnit(businessUnit);
        return newBusinessUnit;
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
