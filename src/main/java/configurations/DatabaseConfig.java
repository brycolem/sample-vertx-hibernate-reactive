package configurations;

import javax.persistence.Persistence;

import org.hibernate.reactive.stage.Stage;

import io.vertx.core.Vertx;
import io.vertx.core.Future;

public class DatabaseConfig {

    private static Stage.SessionFactory sessionFactory;

    public static Future<Void> initialize(Vertx vertx) {
        return vertx.executeBlocking(promise -> {
            sessionFactory = createSessionFactory();
            promise.complete();
        });
    }

    public static Stage.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory has not been initialized.");
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private static Stage.SessionFactory createSessionFactory() {
        return Persistence.createEntityManagerFactory("CertiMatchPersistenceUnit")
                .unwrap(org.hibernate.reactive.stage.Stage.SessionFactory.class);
    }
}
