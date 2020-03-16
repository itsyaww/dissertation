package backend.model;

public class Message {

    String moduleCode;

    Regulation regulation;

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    @Override
    public String toString() {
        return "Message{" +
                "moduleCode='" + moduleCode + '\'' +
                ", regulation=" + regulation +
                '}';
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }
}
