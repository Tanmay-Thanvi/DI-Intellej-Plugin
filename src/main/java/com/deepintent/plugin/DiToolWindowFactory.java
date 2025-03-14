package com.deepintent.plugin;

import com.deepintent.plugin.components.GitUI;
import com.deepintent.plugin.services.git.GitParser;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.deepintent.plugin.components.VpnUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Objects;

public class DiToolWindowFactory implements ToolWindowFactory {

  @Override
  public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    GitUI gitUI = new GitUI(project.getBasePath());
    VpnUI vpnUI = new VpnUI();

    panel.add(gitUI);
    panel.add(vpnUI);

    String repoPath = Paths.get(Objects.requireNonNull(project.getBasePath())).toString();
    new GitParser(repoPath, gitUI::updateGitInfo);

    toolWindow.getComponent().add(panel);
  }
}