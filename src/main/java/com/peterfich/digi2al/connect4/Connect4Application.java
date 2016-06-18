package com.peterfich.digi2al.connect4;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Connect4Application extends Application<Connect4Configuration> {

    public static void main(String[] args) throws Exception {
        new Connect4Application().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void run(Connect4Configuration connect4Configuration, Environment environment) throws Exception {
        environment.jersey().register(Connect4Resource.class);
    }

    @Override
    public void initialize(Bootstrap<Connect4Configuration> bootstrap) {
        // nothing to do yet
    }
}
