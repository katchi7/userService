/*
package ma.tna.ebanking.userservice;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.api.TRestApi;
import ma.tna.ebanking.userservice.dtos.AgencyInfo;
import ma.tna.ebanking.userservice.dtos.T24AgencyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Log4j2
class UserserviceApplicationTests {
    @Autowired
    TRestApi tRestApi;
    @LoadBalanced
	@Test
	void contextLoads() {
        Map<String,Object> body = new HashMap<>();
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setID("1");
        body.put("agence",agencyInfo);
        T24AgencyResponse response = tRestApi.getAgencyInfo(body);
        log.info(response);
        Assert.notNull(response.getAgence(),"Body is null");
        Assert.isTrue(response.getAgence().size()==1,"Size must be 1");
	}

}

 */

