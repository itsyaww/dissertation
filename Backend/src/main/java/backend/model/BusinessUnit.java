package backend.model;

import org.neo4j.springframework.data.core.schema.*;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class BusinessUnit {

    @Id @GeneratedValue
    private Long buID;

    @Property("name")
    private String buName;

    @Property("division")
    private Division buDivision;

    @Property("seniorLead")
    private String buSeniorLead;

    @Property("complianceOfficer")
    private String buComplianceOfficer;

    @Relationship(type="CONTAINS", direction = OUTGOING)
    private List<Team> teams;

    @PersistenceConstructor
    public BusinessUnit(Long buID, String buName, Division buDivision, String buSeniorLead, String buComplianceOfficer, List<Team> teams) {
        this.buID = buID;
        this.buName = buName;
        this.buDivision = buDivision;
        this.buSeniorLead = buSeniorLead;
        this.buComplianceOfficer = buComplianceOfficer;
        this.teams = teams;
    }

    public Long getBuID() {
        return buID;
    }

    public void setBuID(Long buID) {
        this.buID = buID;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public Division getBuDivision() {
        return buDivision;
    }

    public void setBuDivision(Division buDivision) {
        this.buDivision = buDivision;
    }

    public String getBuSeniorLead() {
        return buSeniorLead;
    }

    public void setBuSeniorLead(String buSeniorLead) {
        this.buSeniorLead = buSeniorLead;
    }

    public String getBuComplianceOfficer() {
        return buComplianceOfficer;
    }

    public void setBuComplianceOfficer(String buComplianceOfficer) {
        this.buComplianceOfficer = buComplianceOfficer;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}

//FIRM -> DIVISION -> BUSINESS UNIT -> TEAM -> MODULES -> REGULATIONS
