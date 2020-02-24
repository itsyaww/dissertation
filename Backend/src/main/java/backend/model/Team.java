package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

public class Team {

    @Id @GeneratedValue
    private Long teamID;

    @Property("name")
    private String teamName;

    @Property("primaryManager")
    private String teamPrimaryManager;

    @Property("secondaryManager")
    private String teamSecondaryManager;

    @Property("riskLevel")
    private Double riskLevel;

    @Relationship(type="SUBSCRIBES_TO", direction=OUTGOING)
    private List<Module> modules;

    @PersistenceConstructor
    public Team(Long teamID, String teamName, String teamPrimaryManager, String teamSecondaryManager, Double riskLevel, List<Module> modules) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.teamPrimaryManager = teamPrimaryManager;
        this.teamSecondaryManager = teamSecondaryManager;
        this.riskLevel = riskLevel;
        this.modules = modules;
    }

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamPrimaryManager() {
        return teamPrimaryManager;
    }

    public void setTeamPrimaryManager(String teamPrimaryManager) {
        this.teamPrimaryManager = teamPrimaryManager;
    }

    public String getTeamSecondaryManager() {
        return teamSecondaryManager;
    }

    public void setTeamSecondaryManager(String teamSecondaryManager) {
        this.teamSecondaryManager = teamSecondaryManager;
    }

    public Double getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(Double riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Boolean addModule(Module module)
    {
        return modules.add(module);
    }

    public void removeModule(String moduleCode)
    {
        modules.forEach(module -> {
            if(module.getModuleCode().equals(moduleCode))
            {
                System.out.println("Removing Module" + moduleCode + " from list");
                modules.remove(module);
            }
        });
    }
}
