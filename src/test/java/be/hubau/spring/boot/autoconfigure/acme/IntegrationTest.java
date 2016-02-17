package be.hubau.spring.boot.autoconfigure.acme;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableApplicationContext context;

    private static int serverPort;

    @BeforeClass
    public static void start() throws Exception {
        Future<ConfigurableApplicationContext> future = Executors.newSingleThreadExecutor().submit(
                () -> SpringApplication.run(TestApplication.class)
        );

        context = future.get(60, TimeUnit.SECONDS);
        serverPort = Integer.parseInt(context.getEnvironment().getProperty("server.port"));
    }

    @AfterClass
    public static void stop() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    public void correctCallShouldReturnOK() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity("http://localhost:" + serverPort + "/.well-known/acme-challenge/123ABC", String.class);
        assertEquals("Request should return HTTP 200 OK", HttpStatus.OK, entity.getStatusCode());
        assertEquals("Request should return correct response token", "123-RESPONSE-789", entity.getBody());
    }

    @Test
    public void incorrectCallShouldReturnBadRequest() throws Exception {
        ResponseEntity<String> entity = getRestTemplate().getForEntity("http://localhost:" + serverPort + "/.well-known/acme-challenge/INCORRECT_TOKEN", String.class);
        assertEquals("Request should return BAD REQUEST", HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });

        return restTemplate;
    }
}
