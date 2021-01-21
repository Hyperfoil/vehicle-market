package io.hyperfoil.market.user;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class Config {
   @ConfigProperty(name = "user.hash.iterations")
   int hashIterations;
}
