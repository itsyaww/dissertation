package backend.model;

import org.neo4j.springframework.data.core.schema.Node;

import java.util.List;

@Node
public class BusinessUnit {

    private Long buID;
    private String buName;
    private Division buDivision;
    private List<Module> buModules;
    private String buSeniorLead;
    private String buSomplianceOffficer;
    private List<Regulation> regulations;

}

//DIVISION -> BUSINESS UNIT -> TEAM -> MODULES -> REGULATIONS
