/*
package ma.tna.ebanking.userservice;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.api.firstApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.util.Assert;

import java.util.Map;

@SpringBootTest
@Log4j2
class UserserviceApplicationTests {

    @Autowired
    firstApi firstApi;
    @LoadBalanced
	@Test
	void contextLoads() {
        Map<String, Object> response = firstApi.getChequeParams();
        Assert.notNull(response,"Response must not be null");
        log.info(response);
	}

}

 */
