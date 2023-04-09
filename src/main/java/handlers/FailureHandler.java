package handlers;

import java.util.concurrent.CompletionException;

import exceptions.RecordNotFoundException;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class FailureHandler {

    public static void handleExceptions(RoutingContext ctx) {
        Throwable failure = ctx.failure();
        if (failure instanceof DecodeException) {
            ctx.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject()
                            .put("error", "Invalid JSON input")
                            .put("message", failure.getMessage())
                            .encode());
        } else if (failure instanceof CompletionException && failure.getCause() instanceof RecordNotFoundException) {
            Throwable cause = failure.getCause();
            ctx.response()
                    .setStatusCode(404)
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject()
                            .put("error", "Record not found")
                            .put("message", cause.getMessage())
                            .encode());
        } else {
            ctx.next();
        }
    }
}
