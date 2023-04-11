package interfaces;

import org.hibernate.reactive.stage.Stage;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

public interface DBConfig {
    Future<Void> initialize(Vertx vertx);

    static Stage.SessionFactory getSessionFactory() {
        throw new UnsupportedOperationException("This method must be implemented in the subclass.");
    }

    static void close() {
        throw new UnsupportedOperationException("This method must be implemented in the subclass.");
    }
}
