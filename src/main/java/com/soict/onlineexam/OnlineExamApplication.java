package com.ngovangiang.onlineexam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@EnableSwagger2
public class OnlineExamApplication {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder defaultPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @PostConstruct
    public void init() {
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnlineExamApplication.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        System.out.println("duyenpt    " + passwordEncoder.encode("duyenpt"));
        System.out.println("duypd    " + passwordEncoder.encode("duypd"));
        System.out.println("hieuht    " + passwordEncoder.encode("hieuht"));
        System.out.println("giangnv    " + passwordEncoder.encode("giangnv"));
        System.out.println("lamdb    " + passwordEncoder.encode("lamdb")); // Tai khoan cua thay Lam
        System.out.println("thanhnt    " + passwordEncoder.encode("thanhnt")); // Tai khoan cua thay Thanh
        System.out.println(passwordEncoder.encode("duyenpt").length());
    }

}
