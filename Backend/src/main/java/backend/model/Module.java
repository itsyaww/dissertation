package backend.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Module {

    @Id
    private String moduleCode;

    @Property("name")
    private String moduleName;

    @Relationship(type="INCLUDES", direction=OUTGOING)
    private List<Regulation> regulations;

    @Property("regulator")
    private String supervisoryBody;

    @Property("country")
    private String supervisoryCountry;

    @PersistenceConstructor
    public Module(String moduleCode, String moduleName, List<Regulation> regulations, String supervisoryBody, String supervisoryCountry) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.regulations = regulations;
        this.supervisoryBody = supervisoryBody;
        this.supervisoryCountry = supervisoryCountry;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<Regulation> getRegulations() {
        return regulations;
    }

    public void setRegulations(List<Regulation> regulations) {
        this.regulations = regulations;
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
}
