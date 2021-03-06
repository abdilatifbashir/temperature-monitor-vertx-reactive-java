import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Random;
import java.util.UUID;


public class SensorsVertical extends AbstractVerticle {
    public static final Logger logger = LoggerFactory.getLogger(SensorsVertical.class);
    private static final int httpPort = Integer.parseInt(System.getenv().getOrDefault(
            "HTTP_PORT","8080"));

    private final String uuid = UUID.randomUUID().toString();
    private double temperature = 21.0;
    private final Random random = new Random();

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.setPeriodic(200,this::updateTemperature);
        startPromise.complete();
        Router router = Router.router(vertx);
        router.get("/data").handler(this::getData);
        vertx.createHttpServer().requestHandler(router).listen(httpPort).onSuccess(ok->{
            logger.info("http server is running:http://127.0.0:" + httpPort);
            startPromise.complete();
                }).
                onFailure(startPromise::fail);
    }
    private void getData(RoutingContext context){
        logger.info("processing http request from: " + context.request().remoteAddress());
        JsonObject payload = new JsonObject()
                .put("uuid",uuid)
                .put("temperature",temperature)
                .put("timestamp",System.currentTimeMillis());
        context.response().putHeader("content-type","application/jsom")
                .setStatusCode(200)
                .end(payload.encode());

    }
    private void updateTemperature(Long id){
        temperature = temperature + (random.nextGaussian()/2.0d);
        logger.info("temperature update: " + temperature );

    }

}
