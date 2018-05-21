package com.example.demo;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        SomeController.class,
        GlobalExceptionHandler.class,
})
public class SomeControllerTest {
    private final static String baseUrl = "http://localhost:9999/";

    public @Rule WireMockRule wireMockRule = new WireMockRule(9999);

    private @MockBean Config config;

    private WebTestClient webClient;

    private @Autowired SomeController controller;
    private @Autowired GlobalExceptionHandler exceptionHandler;

    @Before
    public void setUp() {
        webClient = WebTestClient
                .bindToController(controller)
                .controllerAdvice(exceptionHandler)
                .build();

        when(config.getUrl()).thenReturn(baseUrl);
    }

    @Test
    public void test() {
        wireMockRule.stubFor(get(urlEqualTo("/200"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("200 MOCK")));

        webClient
                .get()
                .uri("/test")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("200 MOCK");

        wireMockRule.verify(getRequestedFor(urlMatching("/200")));
    }

    @Test
    public void test2() {
        wireMockRule.stubFor(get(urlEqualTo("/200"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("404 MOCK")));

        webClient
                .get()
                .uri("/test")
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody(String.class)
                .isEqualTo("http://localhost:8080/test 404 MOCK");

        wireMockRule.verify(getRequestedFor(urlMatching("/200")));
    }
}
