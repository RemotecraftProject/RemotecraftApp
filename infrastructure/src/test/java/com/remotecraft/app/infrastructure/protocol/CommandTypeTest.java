package com.remotecraft.app.infrastructure.protocol;

import com.remotecraft.app.infrastructure.protocol.enumeration.CommandType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class) public class CommandTypeTest {

  @Test public void shouldGetValidCommandType() throws Exception {
    String getWorldInfoString = CommandType.GET_SERVER_INFO.toString();

    assertThat(getWorldInfoString).isNotNull();
    assertThat(getWorldInfoString).isInstanceOf(String.class);
    assertThat(getWorldInfoString).isEqualTo("get_server_info");
  }
}