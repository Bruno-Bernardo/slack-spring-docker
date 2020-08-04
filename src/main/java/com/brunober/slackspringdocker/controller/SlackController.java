package com.brunober.slackspringdocker.controller;

import com.brunober.slackspringdocker.SlackSpringDockerApplication;
import com.brunober.slackspringdocker.conf.JiraProperties;
import com.brunober.slackspringdocker.exception.IssueNotFoundException;
import com.brunober.slackspringdocker.model.jira.Issue;
import com.brunober.slackspringdocker.model.jira.Jira;
import com.brunober.slackspringdocker.model.slack.*;
import com.brunober.slackspringdocker.repository.IssueRepository;
import com.brunober.slackspringdocker.utils.JiraUtils;
import com.brunober.slackspringdocker.utils.SlackUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SlackController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private JiraProperties JiraProperties;

    private static final Logger log = LoggerFactory.getLogger(SlackSpringDockerApplication.class);

    @GetMapping("/issue/{key}")
    public Issue getIssue(@PathVariable String key) {
        Optional<Issue> issue = issueRepository.findByKey(key);

        if (!issue.isPresent()) {
            throw new IssueNotFoundException();
        }

        return issue.get();
    }

    @PostMapping(value = "/issues",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getIssues(@ModelAttribute SlackSlashCommand slackSlashCommand) throws RuntimeException {
        RestTemplate restTemplate = new RestTemplate();
        new Thread(() -> {
            if (slackSlashCommand.getText() != null && !slackSlashCommand.getText().isEmpty()) {
                issueRepository.save(new Issue(JiraUtils.getValidIssueKey(slackSlashCommand.getText().toUpperCase())));
            }

            restTemplate.postForEntity(slackSlashCommand.getResponseUrl(), getIssues(), JSONObject.class);
        }).start();

        return SlackUtils.getWaitingMessage();
    }

    @GetMapping("/issues")
    public Message getIssues() {
        List<Issue> issues = issueRepository.findByWeek(new SimpleDateFormat("w").format(new Date()));

        this.updateStatus(issues);

        if (issues == null) {
            throw new IssueNotFoundException();
        }

        return new Message().MessageFromIssues(issues, "in_channel");
    }

    @GetMapping("/issues/{week}")
    public Message getIssuesByWeek(@PathVariable String week) {
        List<Issue> issues = issueRepository.findByWeek(week);

        if (issues == null) {
            throw new IssueNotFoundException();
        }

        return new Message().MessageFromIssues(issues, "in_channel");
    }

    @PostMapping(value = "/issues-remove",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Message removeIssue(@ModelAttribute SlackSlashCommand slackSlashCommand) throws RuntimeException {
        log.info(slackSlashCommand.toString());

        RestTemplate restTemplate = new RestTemplate();
        new Thread(() -> {
            if (slackSlashCommand.getText() != null && !slackSlashCommand.getText().isEmpty()) {
                issueRepository.deleteByKey(JiraUtils.getValidIssueKey(slackSlashCommand.getText().toUpperCase()));
            }

            restTemplate.postForEntity(slackSlashCommand.getResponseUrl(), getIssues(), JSONObject.class);
        }).start();

        return SlackUtils.getWaitingMessage();
    }

    @PostMapping(value = "/week-report",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Message getWeekReport(@ModelAttribute SlackSlashCommand slackSlashCommand) {
        RestTemplate restTemplate = new RestTemplate();
        new Thread(() -> {
            restTemplate.postForEntity(slackSlashCommand.getResponseUrl(), getWeekReport(), JSONObject.class);
        }).start();

        return SlackUtils.getWaitingMessage();
    }

    @GetMapping(value = "/week-report", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Message getWeekReport() {


        Jira json = JiraProperties.getJiraRestTemplate().getForEntity(
                "https://" + JiraProperties.getUrl() + "/rest/api/2/search?jql=project=" + JiraProperties.getProject() + " AND fixVersion = earliestUnreleasedVersion()&fields=issuetype,status",
                Jira.class).getBody();

        List<String> types = new ArrayList<>();
        List<String> status = new ArrayList<>();

        if (json != null
                && json.getIssues() != null) {
            types = json.getIssues().stream().filter(i -> i != null).map(i -> i.getFields()).filter(f -> f != null).map(f -> f.getIssuetype()).filter(i -> i != null).map(i -> i.getName()).collect(Collectors.toList());
            status = json.getIssues().stream().filter(i -> i != null).map(i -> i.getFields()).filter(f -> f != null).map(f -> f.getStatus()).filter(i -> i != null).map(i -> i.getName()).collect(Collectors.toList());
        }

        long total = json.getTotal().longValue();
        long totalHotfix = types.stream().filter(t -> t.equalsIgnoreCase("Hotfix")).count();
        long totalBug = types.stream().filter(t -> t.equalsIgnoreCase("Bug")).count();
        long totalTask = total - totalBug - totalHotfix;

        Block blockTitulo = new Block(new Text("mrkdwn", "*Issues of the week - " + new SimpleDateFormat("w").format(new Date()) + "*"));
        Block block = new Block(getQuantityAndPercentage(total, total, "Total"));

        List<Field> fields1 = new ArrayList<>();
        fields1.addAll(getQuantityAndPercentage(total, totalTask, "Task"));
        fields1.addAll(getQuantityAndPercentage(total, totalBug, "Bug"));
        fields1.addAll(getQuantityAndPercentage(total, totalHotfix, "Hotfix"));
        Block block1 = new Block(fields1);

        List<Field> fields2 = new ArrayList<>();
        fields2.addAll(getQuantityAndPercentage(total, status, "Backlog", "Backlog"));
        fields2.addAll(getQuantityAndPercentage(total, status, "Selected for development", "Selecionada para Desenvolvimento"));
        fields2.addAll(getQuantityAndPercentage(total, status, "In Progress", "Em andamento"));
        Block block2 = new Block(fields2);

        List<Field> fields3 = new ArrayList<>();
        fields3.addAll(getQuantityAndPercentage(total, status, "Developed", "Desenvolvido"));
        fields3.addAll(getQuantityAndPercentage(total, status, "Done", "Done"));
        fields3.addAll(getQuantityAndPercentage(total, status, "Rejected", "Rejeitado"));
        Block block3 = new Block(fields3);

        Block blockDivider = SlackUtils.getBlockDivider();

        Message message = new Message(Arrays.asList(blockTitulo, blockDivider, block, blockDivider, block1, blockDivider, block2, block3), "in_channel");

        return message;
    }

    private List<Field> getQuantityAndPercentage(long total, List<String> names, String filter, String title) {
        long quantity = names.stream().filter(n -> n.equalsIgnoreCase(filter)).count();
        return getQuantityAndPercentage(total, quantity, title);
    }

    private List<Field> getQuantityAndPercentage(long total, long quantity, String title) {
        double percentage = (double) quantity / (double) total * 100;
        return Arrays.asList(new Field("*" + title + "*"),
                new Field(String.format("%-" + 4 + "s", "*" + quantity) + "* (" + String.format("%.2f", percentage) + "%)"));
    }

    private void updateStatus(List<Issue> issues) {
        String keys = StringUtils.arrayToCommaDelimitedString(issues.stream().map(Issue::getKey).toArray());

        if (keys != null && !keys.isEmpty()) {
            Jira jira = JiraProperties.getJiraRestTemplate().getForEntity(
                    "https://" + JiraProperties.getUrl() + "/rest/api/2/search?jql=project=" + JiraProperties.getProject() + " AND issuekey in (" + keys + ")&fields=status",
                    Jira.class).getBody();

            if (jira != null && jira.getIssues() != null) {
                jira.getIssues().forEach(i -> issues.stream().filter(e -> e.getKey() != null && e.getKey().equals(i.getKey())).findAny().ifPresent(e -> e.setFields(i.getFields())));
            }
        }
    }
}
