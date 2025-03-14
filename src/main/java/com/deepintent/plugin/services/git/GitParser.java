package com.deepintent.plugin.services.git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import java.nio.file.*;
import java.util.function.Consumer;

public class GitParser {
  private final String repoPath;
  private final Consumer<GitInfo> updateCallback;

  public GitParser(String repoPath, Consumer<GitInfo> updateCallback) {
    this.repoPath = repoPath;
    this.updateCallback = updateCallback;
    startFileWatcher();
  }

  private void startFileWatcher() {
    new Thread(() -> {
      try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
        Path gitPath = Paths.get(repoPath, ".git");
        gitPath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
          WatchKey key = watchService.take();
          for (WatchEvent<?> event : key.pollEvents()) {
            if (event.context().toString().equals("HEAD")) {
              GitInfo gitInfo = getGitInfo();
              updateCallback.accept(gitInfo);
            }
          }
          key.reset();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
  }

  public GitInfo getGitInfo() {
    try {
      List<String> lines = Files.readAllLines(Paths.get(repoPath + "/.git/HEAD"));
      String branchName = lines.get(0).replace("ref: refs/heads/", "").trim();
      String repoName = Paths.get(repoPath).getFileName().toString();
      return new GitInfo(repoName, branchName);
    } catch (IOException e) {
      return new GitInfo("Unknown", "Unknown");
    }
  }
}