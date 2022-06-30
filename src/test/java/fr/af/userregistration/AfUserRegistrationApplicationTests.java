package fr.af.userregistration;

import fr.af.userregistration.controller.UserControllerTest;
import fr.af.userregistration.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UserControllerTest.class,
		UserServiceTest.class
})
@SpringBootTest
class AfUserRegistrationApplicationTests {
	
}
