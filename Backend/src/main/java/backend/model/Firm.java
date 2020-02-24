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
    private List<Division> divisions;

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

    public Boolean addDivision(Division division)
    {
        return divisions.add(division);
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
