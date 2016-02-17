package be.hubau.spring.boot.autoconfigure.acme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Configuration
@EnableConfigurationProperties(AcmeProperties.class)
public class AcmeConfig {

    @RestController
    @RequestMapping("/.well-known/acme-challenge")
    public static class AcmeController {

        @Autowired
        private AcmeProperties acmeProperties;

        @RequestMapping(method = RequestMethod.GET, value = "/{requestToken}", produces = MediaType.TEXT_PLAIN_VALUE)
        public ResponseEntity<String> serveAcmeResponseToken(@NotNull @PathVariable("requestToken") String token) {
            if (token.equals("") || !token.equals(acmeProperties.getChallengeToken())) {
                return ResponseEntity.badRequest().body("Incorrect request token!");
            } else {
                return ResponseEntity.ok(acmeProperties.getChallengeResponse());
            }
        }
    }
}