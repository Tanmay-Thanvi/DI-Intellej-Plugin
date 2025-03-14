package org.deepintent.plugin.services.git;

public class GitInfo {
  public final String repoName;
  public final String branch;
  public String latestCommitHash;
  public String latestCommitMessage;
  public String latestCommitAuthor;

  public GitInfo(String repoName, String branch, String latestCommitHash, String latestCommitMessage, String latestCommitAuthor) {
    this.repoName = repoName;
    this.branch = branch;
    this.latestCommitHash = latestCommitHash;
    this.latestCommitMessage = latestCommitMessage;
    this.latestCommitAuthor = latestCommitAuthor;
  }

  public GitInfo(String repoName, String branchName) {
    this.repoName = repoName;
    this.branch = branchName;
  }

  public String getBranchName() {
    return branch;
  }

  public String getRepoName() {
    return repoName;
  }
}