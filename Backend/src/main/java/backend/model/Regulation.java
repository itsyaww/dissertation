package backend.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;

import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDateTime;

@Node
public class Regulation {

    @Id @GeneratedValue
    private Long regulationID;

   @Property("title")
   private String regulationTitle;

    @Property("date_issued")
    private LocalDateTime dateIssued;

    @Property("date_goLive")
    private LocalDateTime goLive;

    @Property("topic")
    private String topic;

    @Property("atRisk")
    private Boolean atRisk;

    @PersistenceConstructor
    public Regulation(Long regulationID, LocalDateTime dateIssued, LocalDateTime goLive, String topic, Boolean atRisk) {
        this.regulationID = regulationID;
        this.dateIssued = dateIssued;
        this.goLive = goLive;
        this.topic = topic;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getAtRisk() {
        return atRisk;
    }

    public void setAtRisk(Boolean atRisk) {
        this.atRisk = atRisk;
    }
}
