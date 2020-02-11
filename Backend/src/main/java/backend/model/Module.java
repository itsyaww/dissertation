package backend.model;

import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;

import java.util.List;

@Node
public class Module {

    private String moduleCode;
    private String moduleName;
    private List<Regulation> regulations;

    @Property("regulator")
    private String supervisoryBody;

    @Property("country")
    private String supervisoryCountry;
}
