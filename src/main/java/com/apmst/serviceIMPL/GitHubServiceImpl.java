package com.apmst.serviceIMPL;

import com.apmst.service.GitHubService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class GitHubServiceImpl implements GitHubService {

	@Value("${github.token}")
    private String githubToken;

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.repo}")
    private String githubRepo;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String GITHUB_API =
            "https://api.github.com";

    @Override
    public String pushFileToGitHub(String fileName,
                                    String content,
                                    String commitMessage) {
        try {
            String url = GITHUB_API + "/repos/" +
                    githubUsername + "/" + githubRepo +
                    "/contents/tasks/" + fileName;

            // Encode content to Base64
            String encodedContent = Base64.getEncoder()
                    .encodeToString(content.getBytes());

            // Build request body
            Map<String, String> body = new HashMap<>();
            body.put("message", commitMessage);
            body.put("content", encodedContent);

            // Check if file exists to get SHA
            String sha = getFileSha(url);
            if (sha != null) {
                body.put("sha", sha);
            }

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + githubToken);
            headers.set("Accept",
                    "application/vnd.github.v3+json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> request =
                    new HttpEntity<>(body, headers);

            // Make API call
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                Map responseBody = response.getBody();
                if (responseBody != null &&
                        responseBody.containsKey("content")) {
                    Map contentMap = (Map) responseBody
                            .get("content");
                    return (String) contentMap.get("html_url");
                }
            }

        } catch (Exception e) {
            System.err.println("GitHub push failed: " +
                    e.getMessage());
        }
        return null;
    }

    @Override
    public boolean repoExists() {
        try {
            String url = GITHUB_API + "/repos/" +
                    githubUsername + "/" + githubRepo;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + githubToken);

            HttpEntity<String> request =
                    new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class);

            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            return false;
        }
    }

    // Helper method to get file SHA
    private String getFileSha(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "token " + githubToken);

            HttpEntity<String> request =
                    new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Map.class);

            if (response.getStatusCode().is2xxSuccessful() &&
                    response.getBody() != null) {
                return (String) response.getBody().get("sha");
            }
        } catch (Exception e) {
            // File doesn't exist yet
        }
        return null;
    }
}
