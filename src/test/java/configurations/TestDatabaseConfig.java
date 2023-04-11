package configurations;

import org.junit.jupiter.api.*;

import interfaces.DBConfig;
import io.vertx.core.Vertx;

public class TestDatabaseConfig {
    private Vertx vertx;
    private DBConfig databaseConfig = new DatabaseConfig();

    @BeforeEach
    public void setUp() {
        vertx = Vertx.vertx();
        this.databaseConfig.initialize(vertx).toCompletionStage().toCompletableFuture().join();
        DatabaseConfig.close(); // Close the session factory after initializing it
    }

    @AfterEach
    public void tearDown() {
        DatabaseConfig.close();
        vertx.close();
    }
}
