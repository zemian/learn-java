package zemian.spring.boot.datarest.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zemian.spring.boot.datarest.model.Issue;

import javax.persistence.EntityManager;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by zemian on 10/30/16.
 */
@Repository
public interface IssueService extends JpaRepository<Issue, Long> {
}
