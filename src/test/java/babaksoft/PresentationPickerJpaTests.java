package babaksoft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PresentationPickerJpaTests {

    @Autowired
    private ProjectGroupRepository pgrepo;

    private ProjectGroup pg;

    @Before
    public void setUp() {
        pg = new ProjectGroup("bestgroupeva");
        pgrepo.save(pg);
    }

    @Test
    public void testProjectGroupCreation() {
        Iterable<ProjectGroup> pglist = pgrepo.findAll();
        assertEquals (1, pglist.spliterator().getExactSizeIfKnown());
        assertEquals(pglist.iterator().next().getName(), "bestgroupeva");
    }

    @Test
    public void testAddingMemberToProjectGroup() {
        pg.addMember("toto");
        pgrepo.save(pg);
        ProjectGroup retrievedPg = pgrepo.findAll().iterator().next();
        assertEquals(1, retrievedPg.getMembers().size());
        assertEquals("toto", retrievedPg.getMembers().get(0));
    }
}
