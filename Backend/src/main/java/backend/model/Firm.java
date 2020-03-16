package backend.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Firm {

    @Id
    private String firmName;

    @Relationship(type ="HAS",direction =OUTGOING)
    private List<Division> divisions = new ArrayList<>();

    @PersistenceConstructor
    public Firm(String firmName, List<Division> divisions) {
        this.firmName = firmName;
        this.divisions = divisions;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    public Firm addDivision(Division regulation)
    {
        divisions.add(createRegulation(regulation));
        return this;
    }

    private static Division createRegulation(Division division){

        Assert.notNull(division, "Division must not be null");
        Assert.notNull(division.getDivisionName(), "Regulation code, must not be null");

        Division newDivision = new Division(division);
        return newDivision;
    }

    public void removeDivision(Long divisionId)
    {
        divisions.forEach(division -> {
            if(division.getDivisionID().equals(divisionId))
            {
                System.out.println("Removing division " + divisionId.toString() + " from list");
                divisions.remove(division);
            }
        });
    }
}
