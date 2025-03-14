package org.deepintent.plugin;

import javax.swing.*;

public class DiToolWindowOldCode {
  private final JPanel content;

  public DiToolWindowOldCode() {
    content = new JPanel();
    JLabel label = new JLabel("Hello from DI Plugin!");
    content.add(label);
  }

  public JPanel getContent() {
    return content;
  }
}