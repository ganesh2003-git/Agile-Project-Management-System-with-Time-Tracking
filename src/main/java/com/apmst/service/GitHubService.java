package com.apmst.service;

public interface GitHubService {

	 // Push file to GitHub
    String pushFileToGitHub(String fileName,
                             String content,
                             String commitMessage);

    // Check if repo exists
    boolean repoExists();
}
