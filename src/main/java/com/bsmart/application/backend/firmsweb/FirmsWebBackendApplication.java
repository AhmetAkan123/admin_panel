package com.bsmart.application.backend.firmsweb;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bsmart.application.backend")
public class FirmsWebBackendApplication {
    public static Logger log = Logger.getLogger(FirmsWebBackendApplication.class.getName());

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FirmsWebBackendApplication.class, args);
    }
}
