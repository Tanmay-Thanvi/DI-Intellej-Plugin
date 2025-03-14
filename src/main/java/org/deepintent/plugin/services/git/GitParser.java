package org.deepintent.plugin.services.git;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

public class GitParser {
  private final String repoPath;
  private final Consumer<GitInfo> gitUpdateCallback;
  private String lastBranch = "";

  public GitParser(String repoPath, Consumer<GitInfo> gitUpdateCallback) {
    this.repoPath = repoPath;
    this.gitUpdateCallback = gitUpdateCallback;
    registerGitFileWatcher();  // 🚀 Detects branch changes in real-time
    updateGitInfo();  // Initial load
  }

  private void registerGitFileWatcher() {
    VirtualFile gitHeadFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(repoPath + "/.git/HEAD");
    if (gitHeadFile == null) return;

    VirtualFileManager.getInstance().addVirtualFileListener(new VirtualFileListener() {
      @Override
      public void contentsChanged(@NotNull VirtualFileEvent event) {
        if (event.getFile().getPath().endsWith("/.git/HEAD")) {
          ApplicationManager.getApplication().invokeLater(GitParser.this::updateGitInfo);
        }
      }
    });
  }

  public void updateGitInfo() {
    String branch = getCurrentBranch();
    if (!branch.equals(lastBranch)) {
      lastBranch = branch;
      gitUpdateCallback.accept(new GitInfo(
        Paths.get(repoPath).getFileName().toString(),  // Repo Name
        branch  // Branch Name
      ));
    }
  }

  private String getCurrentBranch() {
    try {
      List<String> lines = Files.readAllLines(Paths.get(repoPath + "/.git/HEAD"));
      if (!lines.isEmpty() && lines.get(0).startsWith("ref: refs/heads/")) {
        return lines.get(0).replace("ref: refs/heads/", "").trim();
      }
    } catch (IOException e) {
      return "Error";
    }
    return "Unknown";
  }
}