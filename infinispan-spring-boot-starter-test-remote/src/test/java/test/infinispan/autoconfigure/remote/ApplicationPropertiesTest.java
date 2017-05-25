package test.infinispan.autoconfigure.remote;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        InfinispanRemoteAutoConfiguration.class,
        InfinispanRemoteCacheManagerAutoConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off"
    })
@TestPropertySource(locations = "classpath:test-application.properties")
public class ApplicationPropertiesTest {

   @Autowired
   private RemoteCacheManager remoteCacheManager;

   @Test
   public void testDefaultClient() {
      //when
      int portObtainedFromPropertiesFile = remoteCacheManager.getConfiguration().servers().get(0).port();
      int connectTimeout = remoteCacheManager.getConfiguration().connectionTimeout();
      int socketTimeout = remoteCacheManager.getConfiguration().socketTimeout();
      int maxRetries = remoteCacheManager.getConfiguration().maxRetries();

      //then
      assertThat(portObtainedFromPropertiesFile).isEqualTo(6667);
      assertThat(connectTimeout).isEqualTo(10);
      assertThat(socketTimeout).isEqualTo(10);
      assertThat(maxRetries).isEqualTo(10);
   }
}
