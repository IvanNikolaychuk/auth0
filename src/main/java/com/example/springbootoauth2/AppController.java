package com.example.springbootoauth2;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppController {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @RequestMapping(value = "/api/public", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String publicEndpoint() {
        extractEmailFromToken();

        return new JSONObject()
                .put("message", "Hello from a public endpoint! You don\'t need to be authenticated to see this.")
                .toString();
    }

    @RequestMapping(value = "/api/admin", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String privateEndpoint() {
        extractEmailFromToken();

        return new JSONObject()
                .put("message", "You have admin role.")
                .toString();
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String privateScopedEndpoint() {
        extractEmailFromToken();
        return new JSONObject()
                .put("message", "You have user role")
                .toString();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getAppConfigs() {
        return new JSONObject()
                .put("domain", domain)
                .put("clientID", clientId)
                .put("audience", resourceId)
                .toString();
    }

    public String extractEmailFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object details = authentication.getDetails();
        if ( details instanceof OAuth2AuthenticationDetails){
            Jwt decodedToken = JwtHelper.decode(((OAuth2AuthenticationDetails) details).getTokenValue());
            decodedToken.getClaims();
            System.out.println("");
        }

        return "";
    }
}
