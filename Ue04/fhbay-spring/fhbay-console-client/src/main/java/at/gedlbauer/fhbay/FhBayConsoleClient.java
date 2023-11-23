package at.gedlbauer.fhbay;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class FhBayConsoleClient {
    public static void main(String[] args) {
        SpringApplication.run(FhBayConsoleClient.class, args);
    }

    @Bean("promptProvider")
    public PromptProvider getPrompt() {
        return () ->
                new AttributedString("fhbay> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN));
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        var restTemplate = builder.build();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(converters);
        return restTemplate;
    }
}
