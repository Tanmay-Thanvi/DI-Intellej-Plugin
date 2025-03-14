package com.deepintent.plugin.services.vpn;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class VpnStatusChecker {
  private static final String PRITUNL_CHECK_URL = "https://sv.central.adm.lan.didevops.com/ui/vault/auth";

  public static boolean isPritunlRunning() {
    try {
      URL url = new URL(PRITUNL_CHECK_URL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(3000);
      connection.setReadTimeout(3000);
      connection.connect();
      return connection.getResponseCode() == 200; // VPN connected
    } catch (IOException e) {
      return false; // VPN disconnected
    }
  }

  public static void openPritunlApp() {
    try {
      if (System.getProperty("os.name").toLowerCase().contains("win")) {
        Runtime.getRuntime().exec("cmd /c start pritunl");
      } else {
        Runtime.getRuntime().exec("open -a /Applications/Pritunl.app");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}