package com.zireck.remotecraft.server.mock;

import com.zireck.remotecraft.server.mock.network.MockNetworkDiscovery;

public class MockServer {

  public static void main(String [] args) {
    System.out.println("Mock Server running!");
    MockNetworkDiscovery.getInstance();
  }
}
