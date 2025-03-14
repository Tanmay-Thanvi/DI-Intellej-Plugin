package org.deepintent.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import org.deepintent.plugin.services.vpn.VpnStatusChecker;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DiToolWindowFactory implements ToolWindowFactory {
  private JBLabel vpnStatusLabel;
  private JButton openVpnButton;

  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    JPanel panel = new JBPanel<>(new BorderLayout());
    vpnStatusLabel = new JBLabel("Checking VPN status...");
    openVpnButton = new JButton("Open Pritunl VPN");

    openVpnButton.addActionListener(this::openPritunl);
    JPanel vpnPanel = new JPanel();
    vpnPanel.setLayout(new FlowLayout());
    vpnPanel.add(vpnStatusLabel);
    vpnPanel.add(openVpnButton);

    panel.add(vpnPanel, BorderLayout.NORTH);
    toolWindow.getComponent().add(panel);

    openVpnButton.setVisible(false);
    startVpnMonitor();
  }

  private void startVpnMonitor() {
    Timer timer = new Timer(true);
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        SwingUtilities.invokeLater(() -> updateVpnStatus());
      }
    }, 0, 5000);  // Check VPN status every 5 seconds
  }

  private void updateVpnStatus() {
    boolean isVpnOn = VpnStatusChecker.isPritunlRunning();
    vpnStatusLabel.setText(isVpnOn ? "VPN Status: ✅ Connected" : "VPN Status: ❌ Disconnected");
    openVpnButton.setVisible(!isVpnOn);  // Show button if VPN is OFF
  }

  private void openPritunl(ActionEvent e) {
    VpnStatusChecker.openPritunlApp();
  }
}