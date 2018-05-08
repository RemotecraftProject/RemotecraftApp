package com.remotecraft.app.infrastructure.entity.mapper;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.infrastructure.entity.NetworkAddressEntity;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NetworkAddressEntityDataMapperTest {

  private NetworkAddressEntityDataMapper networkAddressEntityDataMapper;

  @Before
  public void setUp() throws Exception {
    networkAddressEntityDataMapper = new NetworkAddressEntityDataMapper();
  }

  @Test
  public void shouldReturnNullNetworkAddressGivenNullNetworkAddressEntity() throws Exception {
    NetworkAddressEntity networkAddressEntity = null;

    NetworkAddress networkAddress = networkAddressEntityDataMapper.transform(networkAddressEntity);

    assertThat(networkAddress, nullValue());
  }

  @Test
  public void shouldProperlyMapNetworkAddressEntityIntoNetworkAddress() throws Exception {
    NetworkAddressEntity networkAddressEntity = NetworkAddressEntity.builder()
        .ip("192.168.1.1")
        .port(8889)
        .build();

    NetworkAddress networkAddress = networkAddressEntityDataMapper.transform(networkAddressEntity);

    assertThat(networkAddress, notNullValue());
    assertThat(networkAddress, instanceOf(NetworkAddress.class));
    assertThat(networkAddress.ip(), notNullValue());
    assertThat(networkAddress.ip(), is("192.168.1.1"));
    assertThat(networkAddress.port(), is(8889));
  }

  @Test
  public void shouldProperlyMapNetworkAddressEntityCollectionIntoNetworkAddressCollection()
      throws Exception {
    NetworkAddressEntity networkAddressEntity1 = NetworkAddressEntity.builder()
        .ip("192.168.1.1")
        .port(8889)
        .build();
    NetworkAddressEntity networkAddressEntity2 = NetworkAddressEntity.builder()
        .ip("192.168.1.2")
        .port(8890)
        .build();
    ArrayList<NetworkAddressEntity> networkAddressEntities = new ArrayList<>();
    networkAddressEntities.add(networkAddressEntity1);
    networkAddressEntities.add(networkAddressEntity2);

    Collection<NetworkAddress> networkAddresses =
        networkAddressEntityDataMapper.transform(networkAddressEntities);

    assertThat(networkAddresses, notNullValue());
    assertThat(networkAddressEntities.size(), is(2));
    NetworkAddress networkAddress1 = (NetworkAddress) networkAddresses.toArray()[0];
    assertThat(networkAddress1, notNullValue());
    assertThat(networkAddress1.ip(), is("192.168.1.1"));
    assertThat(networkAddress1.port(), is(8889));
    NetworkAddress networkAddress2 = (NetworkAddress) networkAddresses.toArray()[1];
    assertThat(networkAddress2, notNullValue());
    assertThat(networkAddress2.ip(), is("192.168.1.2"));
    assertThat(networkAddress2.port(), is(8890));
  }

  @Test
  public void shouldReturnNullNetworkAddressEntityGivenNullNetworkAddress() throws Exception {
    NetworkAddress networkAddress = null;

    NetworkAddressEntity networkAddressEntity =
        networkAddressEntityDataMapper.transformInverse(networkAddress);

    assertThat(networkAddressEntity, nullValue());
  }

  @Test
  public void shouldProperlyMapNetworkAddressIntoNetworkAddressEntity() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.24.33")
        .port(9991)
        .build();

    NetworkAddressEntity networkAddressEntity =
        networkAddressEntityDataMapper.transformInverse(networkAddress);

    assertThat(networkAddressEntity, notNullValue());
    assertThat(networkAddressEntity, instanceOf(NetworkAddressEntity.class));
    assertThat(networkAddressEntity.ip(), is("192.168.24.33"));
    assertThat(networkAddressEntity.port(), is(9991));
  }

  @Test
  public void shouldProperlyMapNetworkAddressCollectionIntoNetworkAddressEntityCollection()
      throws Exception {
    NetworkAddress networkAddress1 = NetworkAddress.builder()
        .ip("192.168.24.33")
        .port(9991)
        .build();
    NetworkAddress networkAddress2 = NetworkAddress.builder()
        .ip("192.168.53.88")
        .port(9944)
        .build();
    ArrayList<NetworkAddress> networkAddresses = new ArrayList<>();
    networkAddresses.add(networkAddress1);
    networkAddresses.add(networkAddress2);

    Collection<NetworkAddressEntity> networkAddressEntities =
        networkAddressEntityDataMapper.transformInverse(networkAddresses);

    assertThat(networkAddressEntities, notNullValue());
    assertThat(networkAddressEntities.size(), is(2));
    NetworkAddressEntity networkAddressEntity1 =
        (NetworkAddressEntity) networkAddressEntities.toArray()[0];
    assertThat(networkAddressEntity1, notNullValue());
    assertThat(networkAddressEntity1.ip(), is("192.168.24.33"));
    assertThat(networkAddressEntity1.port(), is(9991));
    NetworkAddressEntity networkAddressEntity2 =
        (NetworkAddressEntity) networkAddressEntities.toArray()[1];
    assertThat(networkAddressEntity2, notNullValue());
    assertThat(networkAddressEntity2.ip(), is("192.168.53.88"));
    assertThat(networkAddressEntity2.port(), is(9944));
  }
}