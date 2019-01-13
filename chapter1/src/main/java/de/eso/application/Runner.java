package de.eso.application;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 *
 * COPIED from https://github.com/vert-x3/vertx-examples/blob/master/web-examples/src/main/java/io/vertx/example/util/Runner.java
 */
class Runner {
  private static final String WEB_EXAMPLES_DIR = "web-examples";
  private static final String WEB_EXAMPLES_JAVA_DIR = WEB_EXAMPLES_DIR + "/src/main/java/";

  public static void runExample(Class clazz) {
    runExample(WEB_EXAMPLES_JAVA_DIR, clazz, new VertxOptions().setClustered(false), null);
  }


  public static void runExample(String exampleDir, Class clazz, VertxOptions options, DeploymentOptions deploymentOptions) {
    runExample(exampleDir + clazz.getPackage().getName().replace(".", "/"), clazz.getName(), options, deploymentOptions);
  }

  public static void runExample(String exampleDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
    if (options == null) {
      // Default parameter
      options = new VertxOptions();
    }
    // Smart cwd detection

    // Based on the current directory (.) and the desired directory (exampleDir), we try to compute the vertx.cwd
    // directory:
    try {
      // We need to use the canonical file. Without the file name is .
      File current = new File(".").getCanonicalFile();
      if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
        exampleDir = exampleDir.substring(current.getName().length() + 1);
      }
    } catch (IOException e) {
      // Ignore it.
    }

    System.setProperty("vertx.cwd", exampleDir);
    Consumer<Vertx> runner =
        vertx -> {
          try {
            if (deploymentOptions != null) {
              vertx.deployVerticle(verticleID, deploymentOptions);
            } else {
              vertx.deployVerticle(verticleID);
            }
          } catch (Throwable t) {
            t.printStackTrace();
          }
        };
    if (options.isClustered()) {
      Vertx.clusteredVertx(
          options,
          res -> {
            if (res.succeeded()) {
              Vertx vertx = res.result();
              runner.accept(vertx);
            } else {
              res.cause().printStackTrace();
            }
          });
    } else {
      Vertx vertx = Vertx.vertx(options);
      runner.accept(vertx);
    }
  }
}
