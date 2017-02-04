package babaksoft;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProjectGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    private String name;

    @ElementCollection
    private List<String> members;

    public ProjectGroup(String name) {
        this.name = name;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {return id;}

    public void addMember(String member) {
        members.add(member);
    }

    public List<String> getMembers() {
        return members;
    }

    protected ProjectGroup() {} //to make this a bean, although I don't want this to be used
}
