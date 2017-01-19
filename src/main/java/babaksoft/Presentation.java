package babaksoft;

import javax.persistence.*;

@Entity
public class Presentation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "group_id")
    ProjectGroup projectGroup;

    private String topic;

    public Presentation(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public int getId() {
        return id;
    }

    public ProjectGroup getProjectGroup() {
        return projectGroup;
    }

    public void setProjectGroup(ProjectGroup projectGroup) {
        this.projectGroup = projectGroup;
    }

    protected Presentation() {} //to make this a bean, although I don't want this to be used

}
