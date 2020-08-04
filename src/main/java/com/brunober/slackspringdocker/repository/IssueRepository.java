package com.brunober.slackspringdocker.repository;

import com.brunober.slackspringdocker.model.jira.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByWeek(String week);
    Optional<Issue> findByKey(String key);
    void deleteByKey(String key);
}
