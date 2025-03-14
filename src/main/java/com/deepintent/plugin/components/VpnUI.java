package com.deepintent.plugin.components;

import com.deepintent.plugin.services.vpn.VpnStatusChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VpnUI extends JPanel {
  private final JLabel vpnStatusLabel;
  private final JButton openVpnButton;

  public VpnUI() {
    setLayout(new FlowLayout());

    vpnStatusLabel = new JLabel("Checking VPN status...");
    openVpnButton = new JButton("Open Pritunl VPN");

    openVpnButton.addActionListener(this::openPritunl);
    openVpnButton.setVisible(false);

    add(vpnStatusLabel);
    add(openVpnButton);

    startVpnMonitor();
  }

  private void startVpnMonitor() {
    new Timer(5000, e -> updateVpnStatus()).start();
  }

  private void updateVpnStatus() {
    boolean isVpnOn = VpnStatusChecker.isPritunlRunning();
    vpnStatusLabel.setText(isVpnOn ? "VPN Status: ✅ Connected" : "VPN Status: ❌ Disconnected");
    openVpnButton.setVisible(!isVpnOn);
  }

  private void openPritunl(ActionEvent e) {
    VpnStatusChecker.openPritunlApp();
  }
}