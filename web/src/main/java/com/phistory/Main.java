package com.phistory;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main entry point to the application
 *
 * @author gonzalo
 */
@SpringBootApplication
@EnableAutoConfiguration
@Slf4j
public class Main {

    /**
     * Entry method to the application
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(Main.class, args);
        } catch (Exception e) {
            log.error(e.toString(), e);
        }
    }
}
