package backend.model;

import org.neo4j.springframework.data.core.schema.*;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class BusinessUnit {

    @Id @GeneratedValue
    private Long buID;

    @Property("name")
    private String buName;

    @Property("seniorLead")
    private String buSeniorLead;

    @Property("complianceOfficer")
    private String buComplianceOfficer;

    @Relationship(type="CONTAINS", direction = OUTGOING)
    private List<Team> teams = new ArrayList<>();;

    /*@PersistenceConstructor
    public BusinessUnit(Long buID, String buName, Division buDivision, String buSeniorLead, String buComplianceOfficer, List<Team> teams) {
        this.buID = buID;
        this.buName = buName;
        this.buDivision = buDivision;
        this.buSeniorLead = buSeniorLead;
        this.buComplianceOfficer = buComplianceOfficer;
        this.teams = teams;
    }*/

    public BusinessUnit(BusinessUnit businessUnit){
        this.buID = null;
        this.buName = businessUnit.getBuName();
        this.buSeniorLead = businessUnit.getBuSeniorLead();
        this.buComplianceOfficer = businessUnit.getBuComplianceOfficer();
        this.teams = businessUnit.getTeams();
    }

    public BusinessUnit(){};

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

    public BusinessUnit addTeam(Team team)
    {
        teams.add(createTeam(team));
        return this;
    }

    private static Team createTeam(Team team){

        Assert.notNull(team, "Team must not be null");
        Assert.notNull(team.getTeamName(), "Team Name, must not be null");

        Team newTeam = new Team(team);
        return newTeam;
    }

    public void removeTeam(Long teamId)
    {
        teams.forEach(team -> {
            if(team.getTeamID().equals(teamId))
            {
                System.out.println("Removing Team " + teamId.toString() + " from list");
                teams.remove(team);
            }
        });
    }
}

//FIRM -> DIVISION -> BUSINESS UNIT -> TEAM -> MODULES -> REGULATIONS
