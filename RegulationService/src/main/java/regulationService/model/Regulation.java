package regulationService.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Regulation {
    
    private Long regulationID;

    private String regulationCode;
    
    private String regulationTitle;
    
    private String dateIssued;
    
    private String goLive;

    /*private LocalDate dateIssued;

    private LocalDate goLive;*/
    
    private Boolean atRisk;
    
    public Regulation(){};
    
    /*public Regulation(Long regulationID, LocalDate dateIssued, LocalDate goLive, Boolean atRisk) {
        this.regulationID = regulationID;
        this.dateIssued = dateIssued;
        this.goLive = goLive;
        this.atRisk = atRisk;
    }*/

    public Regulation(Long regulationID, String regulationCode, String regulationTitle, String dateIssued, String goLive, Boolean atRisk) {
        this.regulationID = regulationID;
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

    public String getRegulationTitle() {
        return regulationTitle;
    }

    public void setRegulationTitle(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }

    /*public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        LocalDate date = getDate(dateIssued);
        setDateIssued(date);
    }

    private LocalDate getDate(String dateIssued) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d M yyyy");

        return LocalDate.parse(dateIssued,dateTimeFormatter);
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public LocalDate getGoLive() {
        return goLive;
    }*/

    /*public void setGoLive(String goLive) {
        LocalDate date = getDate(goLive);
        setGoLive(date);
    }

    public void setGoLive(LocalDate goLive) {
        this.goLive = goLive;
    }*/

    public Boolean getAtRisk() {
        return atRisk;
    }

    public void setAtRisk(Boolean atRisk) {
        this.atRisk = atRisk;
    }

    public String getRegulationCode() {
        return regulationCode;
    }

    public void setRegulationCode(String regulationCode) {
        this.regulationCode = regulationCode;
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

    @Override
    public String toString() {
        return "Regulation{" +
                "regulationID=" + regulationID +
                ", regulationCode='" + regulationCode + '\'' +
                ", regulationTitle='" + regulationTitle + '\'' +
                ", dateIssued=" + dateIssued +
                ", goLive=" + goLive +
                ", atRisk=" + atRisk +
                '}';
    }
}
