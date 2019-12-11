package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;

import org.springframework.data.annotation.PersistenceConstructor;

@Node
public class Regulation {

    @Id @GeneratedValue
    private Long regulationID;

    @Property("regulator")
    private String supervisoryBody;

    @Property("country")
    private String supervisoryCountry;

    @PersistenceConstructor
    public Regulation(String supervisoryBody, String supervisoryCountry){
        this.regulationID = null;
        this.supervisoryBody = supervisoryBody;
        this.supervisoryCountry = supervisoryCountry;

    }
    public String getSupervisoryBody() {
        return supervisoryBody;
    }
    public void setSupervisoryBody(String supervisoryBody) {
        this.supervisoryBody = supervisoryBody;
    }
    public String getSupervisoryCountry() {
        return supervisoryCountry;
    }
    public void setSupervisoryCountry(String supervisoryCountry) {
        this.supervisoryCountry = supervisoryCountry;
    }
    public Long getRegulationID() {
        return regulationID;
    }
    public void setRegulationID(Long regulationID) {
        this.regulationID = regulationID;
    }
}
