package backend.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.springframework.data.core.schema.Relationship.Direction.OUTGOING;

@Node
public class Handbook {
    @Id
    private String regulator;

    @Relationship(type ="CONTAINS",direction =OUTGOING)
    private List<Module> modules = new ArrayList<>();

    /*@PersistenceConstructor
    public Handbook(String regulator, List<Module> modules) {
        this.regulator = regulator;
        this.modules = modules;
    }*/

    public Handbook() {}

    public String getRegulator() {
        return regulator;
    }

    public void setRegulator(String regulator) {
        this.regulator = regulator;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Handbook addModule(Module module)
    {
        modules.add(createModule(module));
        return this;
    }

    private static Module createModule(Module module){

        Assert.notNull(module, "Module must not be null");
        Assert.notNull(module.getModuleCode(), "Module code, must not be null");

        Module newModule = new Module(module);
        return newModule;
    }

    public void removeModule(Long moduleId)
    {
        modules.forEach(module -> {
            if(module.getModuleCode().equals(moduleId))
            {
                System.out.println("Removing module " + module.toString() + " from list");
                modules.remove(module);
            }
        });
    }

    @Override
    public String toString() {
        return "Handbook{" +
                "regulator='" + regulator + '\'' +
                ", modules=" + modules +
                '}';
    }
}
