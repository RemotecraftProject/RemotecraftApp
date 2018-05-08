package com.remotecraft.app.mapper;

import com.remotecraft.app.domain.NetworkAddress;
import com.remotecraft.app.model.NetworkAddressModel;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class NetworkAddressModelDataMapperTest {

  private NetworkAddressModelDataMapper networkAddressModelDataMapper;

  @Before
  public void setUp() throws Exception {
    networkAddressModelDataMapper = new NetworkAddressModelDataMapper();
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionGivenNullNetworkAddress() throws Exception {
    NetworkAddress networkAddress = null;

    networkAddressModelDataMapper.transform(networkAddress);
  }

  @Test
  public void shouldProperlyMapNetworkAddressIntoNetworkAddressModel() throws Exception {
    NetworkAddress networkAddress = NetworkAddress.builder()
        .ip("192.168.1.45")
        .port(9999)
        .build();

    NetworkAddressModel networkAddressModel =
        networkAddressModelDataMapper.transform(networkAddress);

    assertThat(networkAddressModel, notNullValue());
    assertThat(networkAddressModel, instanceOf(NetworkAddressModel.class));
    assertThat(networkAddressModel.ip(), notNullValue());
    assertThat(networkAddressModel.ip(), is("192.168.1.45"));
    assertThat(networkAddressModel.port(), is(9999));
  }

  @Test
  public void shouldProperlyMapNetworkAddressCollectionIntoNetworkAddressModelCollection()
      throws Exception {
    NetworkAddress networkAddress1 = NetworkAddress.builder()
        .ip("192.168.1.45")
        .port(9999)
        .build();
    NetworkAddress networkAddress2 = NetworkAddress.builder()
        .ip("192.168.1.80")
        .port(1111)
        .build();
    ArrayList<NetworkAddress> networkAddresses = new ArrayList<>();
    networkAddresses.add(networkAddress1);
    networkAddresses.add(networkAddress2);

    Collection<NetworkAddressModel> networkAddressModels =
        networkAddressModelDataMapper.transform(networkAddresses);

    assertThat(networkAddressModels, notNullValue());
    assertThat(networkAddressModels.size(), is(2));
    NetworkAddressModel networkAddressModel1 =
        (NetworkAddressModel) networkAddressModels.toArray()[0];
    assertThat(networkAddressModel1, notNullValue());
    assertThat(networkAddressModel1, instanceOf(NetworkAddressModel.class));
    assertThat(networkAddressModel1.ip(), notNullValue());
    assertThat(networkAddressModel1.ip(), is("192.168.1.45"));
    assertThat(networkAddressModel1.port(), is(9999));
    NetworkAddressModel networkAddressModel2 =
        (NetworkAddressModel) networkAddressModels.toArray()[1];
    assertThat(networkAddressModel2, notNullValue());
    assertThat(networkAddressModel2, instanceOf(NetworkAddressModel.class));
    assertThat(networkAddressModel2.ip(), notNullValue());
    assertThat(networkAddressModel2.ip(), is("192.168.1.80"));
    assertThat(networkAddressModel2.port(), is(1111));
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldThrowExceptionGivenNullNetworkAddressModel() throws Exception {
    NetworkAddressModel networkAddressModel = null;

    networkAddressModelDataMapper.transformInverse(networkAddressModel);
  }

  @Test
  public void shouldProperlyMapNetworkAddressModelIntoNetworkAddress() throws Exception {
    NetworkAddressModel networkAddressModel = NetworkAddressModel.builder()
        .ip("192.168.1.45")
        .port(9999)
        .build();

    NetworkAddress networkAddress =
        networkAddressModelDataMapper.transformInverse(networkAddressModel);

    assertThat(networkAddress, notNullValue());
    assertThat(networkAddress, instanceOf(NetworkAddress.class));
    assertThat(networkAddress.ip(), notNullValue());
    assertThat(networkAddress.ip(), is("192.168.1.45"));
    assertThat(networkAddress.port(), is(9999));
  }

  @Test
  public void shouldProperlyMapNetworkAddressModelCollectionIntoNetworkAddressCollection()
      throws Exception {
    NetworkAddressModel networkAddressModel1 = NetworkAddressModel.builder()
        .ip("192.168.1.45")
        .port(9999)
        .build();
    NetworkAddressModel networkAddressModel2 = NetworkAddressModel.builder()
        .ip("192.168.1.80")
        .port(1111)
        .build();
    ArrayList<NetworkAddressModel> networkAddressModels = new ArrayList<>();
    networkAddressModels.add(networkAddressModel1);
    networkAddressModels.add(networkAddressModel2);

    Collection<NetworkAddress> networkAddresses =
        networkAddressModelDataMapper.transformInverse(networkAddressModels);

    assertThat(networkAddresses, notNullValue());
    assertThat(networkAddresses.size(), is(2));
    NetworkAddress networkAddress1 =
        (NetworkAddress) networkAddresses.toArray()[0];
    assertThat(networkAddress1, notNullValue());
    assertThat(networkAddress1, instanceOf(NetworkAddress.class));
    assertThat(networkAddress1.ip(), notNullValue());
    assertThat(networkAddress1.ip(), is("192.168.1.45"));
    assertThat(networkAddress1.port(), is(9999));
    NetworkAddress networkAddress2 =
        (NetworkAddress) networkAddresses.toArray()[1];
    assertThat(networkAddress2, notNullValue());
    assertThat(networkAddress2, instanceOf(NetworkAddress.class));
    assertThat(networkAddress2.ip(), notNullValue());
    assertThat(networkAddress2.ip(), is("192.168.1.80"));
    assertThat(networkAddress2.port(), is(1111));
  }
}