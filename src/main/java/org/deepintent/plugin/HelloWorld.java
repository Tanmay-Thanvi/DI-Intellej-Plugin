package org.deepintent.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class HelloWorld extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    Messages.showInfoMessage("Message : hola amigos!", "Hello World Message Title");
  }
}
