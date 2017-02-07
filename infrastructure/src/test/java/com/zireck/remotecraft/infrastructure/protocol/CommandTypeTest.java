package com.zireck.remotecraft.infrastructure.protocol;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class) public class CommandTypeTest {

  @Test public void shouldName() throws Exception {
    String getWorldInfoString = CommandType.GET_WORLD_INFO.toString();

    assertThat(getWorldInfoString, notNullValue());
    assertThat(getWorldInfoString, is(instanceOf(String.class)));
    assertThat(getWorldInfoString, is("get_world_info"));
  }
}