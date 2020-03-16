package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;

import org.springframework.data.annotation.PersistenceConstructor;

@Node
public class Regulation {

    /*@Id @GeneratedValue
    private Long regulationID;

   @Property("title")
   private String regulationTitle;

    @Property("date_issued")
    private LocalDateTime dateIssued;

    @Property("date_goLive")
    private LocalDateTime goLive;

    @Property("atRisk")
    private Boolean atRisk;

    @PersistenceConstructor
    public Regulation(Long regulationID, LocalDateTime dateIssued, LocalDateTime goLive, Boolean atRisk) {
        this.regulationID = regulationID;
        this.dateIssued = dateIssued;
        this.goLive = goLive;
        this.atRisk = atRisk;
    }

    public Long getRegulationID() {
        return regulationID;
    }

    public void setRegulationID(Long regulationID) {
        this.regulationID = regulationID;
    }

    public String getRegulationTitle() {
        return regulationTitle;
    }

    public void setRegulationTitle(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }

    public LocalDateTime getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDateTime dateIssued) {
        this.dateIssued = dateIssued;
    }

    public LocalDateTime getGoLive() {
        return goLive;
    }

    public void setGoLive(LocalDateTime goLive) {
        this.goLive = goLive;
    }

    public Boolean getAtRisk() {
        return atRisk;
    }

    public void setAtRisk(Boolean atRisk) {
        this.atRisk = atRisk;
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "regulationID=" + regulationID +
                ", regulationTitle='" + regulationTitle + '\'' +
                ", dateIssued=" + dateIssued +
                ", goLive=" + goLive +
                ", atRisk=" + atRisk +
                '}';
    }*/

    @Id @GeneratedValue
    private Long regulationID;

    @Property
    private String regulationCode;

    @Property
    private String regulationTitle;

    @Property
    private String dateIssued;

    @Property
    private String goLive;

    /*private LocalDate dateIssued;

    private LocalDate goLive;*/

    @Property
    private Boolean atRisk;

    public Regulation(Regulation regulation) {
        this.regulationID = null;
        this.regulationCode = regulation.regulationCode;
        this.regulationTitle = regulation.regulationTitle;
        this.dateIssued = regulation.dateIssued;
        this.goLive = regulation.goLive;
        this.atRisk = regulation.atRisk;
    }

    public Regulation(){}

    @PersistenceConstructor
    public Regulation(String regulationCode, String regulationTitle, String dateIssued, String goLive, Boolean atRisk) {
        this.regulationID = null;
        this.regulationCode = regulationCode;
        this.regulationTitle = regulationTitle;
        this.dateIssued = dateIssued;
        this.goLive = goLive;
        this.atRisk = atRisk;
    }

    public Long getRegulationID() {
        return regulationID;
    }

    public void setRegulationID(Long regulationID) {
        this.regulationID = regulationID;
    }

    public String getRegulationCode() {
        return regulationCode;
    }

    public void setRegulationCode(String regulationCode) {
        this.regulationCode = regulationCode;
    }

    public String getRegulationTitle() {
        return regulationTitle;
    }

    public void setRegulationTitle(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getGoLive() {
        return goLive;
    }

    public void setGoLive(String goLive) {
        this.goLive = goLive;
    }

    public Boolean getAtRisk() {
        return atRisk;
    }

    public void setAtRisk(Boolean atRisk) {
        this.atRisk = atRisk;
    }

    @Override
    public String toString() {
        return "Regulation{" +
                "regulationID=" + regulationID +
                ", regulationCode='" + regulationCode + '\'' +
                ", regulationTitle='" + regulationTitle + '\'' +
                ", dateIssued='" + dateIssued + '\'' +
                ", goLive='" + goLive + '\'' +
                ", atRisk=" + atRisk +
                '}';
    }
}
