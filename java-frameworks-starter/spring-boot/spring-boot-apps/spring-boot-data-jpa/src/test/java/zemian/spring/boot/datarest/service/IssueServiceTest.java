package zemian.spring.boot.datarest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zemian.spring.boot.datarest.model.Issue;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by zemian on 10/30/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueServiceTest {
    @Autowired
    private IssueService issueService;

    @Test
    public void findAllIssues() {
        assertThat(issueService, notNullValue());

        List<Issue> list = issueService.findAll();
        assertThat(list.size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void findOneIssue() {
        Issue issue = issueService.findOne(1L);
        assertThat(issue.getId(), is(1L));
        assertThat(issue.getSummary(), is("Create a spring-boot-data-jpa sample project"));
        assertThat(issue.getDescription().length(), is(182));
        assertThat(issue.getCreatedOn(), lessThan(new Date()));
    }

    @Test
    public void createUpdateAndDelete() {
        Date tstamp = new Date();
        long count = issueService.count();
        assertThat(count, greaterThanOrEqualTo(1L));

        Issue issue =new Issue();
        issue.setSummary("Test");
        issue.setDescription("Just a test");
        issue.setCreatedOn(new Date());

        Issue issueSaved = null;
        try {

            assertThat(issue.getId(), nullValue());
            issueSaved = issueService.save(issue);

            assertThat(issueService.count(), greaterThan(count));

            assertThat(issueSaved.getId(), notNullValue());
            assertThat(issueSaved.getCreatedOn(), greaterThanOrEqualTo(tstamp));
            assertThat(issueSaved.getSummary(), is(issue.getSummary()));
            assertThat(issueSaved.getDescription(), is(issue.getDescription()));

            issueSaved = issueService.findOne(issueSaved.getId());
            assertThat(issueSaved.getId(), notNullValue());
            assertThat(issueSaved.getCreatedOn(), greaterThanOrEqualTo(tstamp));
            assertThat(issueSaved.getSummary(), is(issue.getSummary()));
            assertThat(issueSaved.getDescription(), is(issue.getDescription()));

            assertThat(issueService.exists(issueSaved.getId()), is(true));
            assertThat(issueService.exists(0L), is(false));

            Long savedId = issueSaved.getId();
            issueSaved.setDescription("Updated issue.");
            issueService.save(issueSaved);

            issueSaved = issueService.findOne(issueSaved.getId());
            assertThat(issueSaved.getId(), is(savedId));
            assertThat(issueSaved.getCreatedOn(), greaterThanOrEqualTo(tstamp));
            assertThat(issueSaved.getSummary(), is(issue.getSummary()));
            assertThat(issueSaved.getDescription(), is("Updated issue."));

        } finally {
            if (issueSaved != null && issueSaved.getId() != null) {
                issueService.delete(issueSaved);
            }
        }
    }
}
