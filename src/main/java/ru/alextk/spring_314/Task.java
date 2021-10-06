package ru.alextk.spring_314;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class Task {
    @PostConstruct
    public void task() {
        String url = "http://91.241.64.178:7081/api/users";
        String answer = "";
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> response = rest.getForEntity(url, String.class);
        String sessionID = response.getHeaders().get("Set-Cookie").get(0).split(";")[0];

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionID);

        HttpEntity<User> request = new HttpEntity<>(new User(3L, "James", "Brown", (byte)50), headers);
        ResponseEntity<String> u = rest.postForEntity(url, request, String.class);
        answer += u.getBody();

        request = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte)50), headers);
        u = rest.exchange(url, HttpMethod.PUT, request, String.class);
        answer += u.getBody();

        request = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte)50), headers);
        u = rest.exchange(url + "/3", HttpMethod.DELETE, request, String.class);
        answer += u.getBody();


        System.out.println(answer);
    }
}
