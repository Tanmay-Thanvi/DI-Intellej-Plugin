package com.deepintent.plugin.components;

import com.deepintent.plugin.services.git.GitInfo;

import javax.swing.*;

import java.awt.*;

public class GitUI extends JPanel {
  private JLabel repoLabel;
  private JLabel branchLabel;

  public GitUI(String basePath) {
    setLayout(new GridLayout(2, 1));

    repoLabel = new JLabel(basePath);
    branchLabel = new JLabel("Loading...");

    add(repoLabel);
    add(branchLabel);
  }

  public void updateGitInfo(GitInfo gitInfo) {
    repoLabel.setText("Repository: " + gitInfo.getRepoName());
    branchLabel.setText("Branch: " + gitInfo.getBranchName());
    revalidate();
    repaint();
  }
}