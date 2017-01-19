package babaksoft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    private String name;

    public ProjectGroup(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {return id;}

    protected ProjectGroup() {} //to make this a bean, although I don't want this to be used
}
