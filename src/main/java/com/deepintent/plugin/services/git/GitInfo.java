package com.deepintent.plugin.services.git;

public record GitInfo(String repoName, String branchName) {
  public String getRepoName(){
    return repoName;
  }
  public String getBranchName(){
    return branchName;
  }
}