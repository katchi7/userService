package com.tna.userservice;

import com.tna.userservice.Services.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Log4j2
class UserserviceApplicationTests {
	@Autowired
	CustomerService customerService;
	@Test
	void contextLoads() {

	}

}
