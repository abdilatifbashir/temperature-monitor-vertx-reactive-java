
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger =  LoggerFactory.getLogger(Main.class);
    public static void main(String[] args){
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SensorsVertical());
//        vertx.deployVerticle("SensorVertical",new DeploymentOptions().setInstances(2));
    }


}
