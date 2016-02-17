package be.hubau.spring.boot.autoconfigure.acme;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestApplication.class, loader = SpringApplicationContextLoader.class)
public class AcmeConfigTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AcmeProperties acmeProperties;

    @Test
    public void beansShouldBeRegistered() {
        assertNotNull("AcmeProperties class should be registered in the application context", applicationContext.getBean(AcmeProperties.class));
        assertNotNull("Acme Configuration should be registered in the application context", applicationContext.getBean(AcmeConfig.class));
        assertNotNull("Acme Controller should be registered", applicationContext.getBean(AcmeConfig.AcmeController.class));
    }

    @Test
    public void propertiesShouldBeCorrect() {
        assertEquals("ACME challenge token should be correct", "123ABC", acmeProperties.getChallengeToken());
        assertEquals("ACME challenge response should be correct", "123-RESPONSE-789", acmeProperties.getChallengeResponse());
    }
}
