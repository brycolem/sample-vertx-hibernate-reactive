package configurations;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;

import org.hibernate.reactive.provider.Settings;
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
        Map<String, Object> settings = new HashMap<>();
        String jdbcUrl = "jdbc:postgresql://" + System.getenv("DB_URI");
        settings.put(Settings.URL, jdbcUrl);
        settings.put(Settings.USER, System.getenv("DB_USERNAME"));
        settings.put(Settings.PASS, System.getenv("DB_PASSWORD"));
        return Persistence.createEntityManagerFactory("CertiMatchPersistenceUnit", settings)
                .unwrap(org.hibernate.reactive.stage.Stage.SessionFactory.class);
    }
}
