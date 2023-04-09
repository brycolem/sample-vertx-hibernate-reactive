package questions;

import configurations.DatabaseConfig;
import handlers.FailureHandler;
import handlers.QuestionHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import services.QuestionService;
import services.QuestionServiceImpl;

public class MainVerticle extends AbstractVerticle {
  private QuestionService questionService;

  @Override
  public void start() {
    DatabaseConfig.initialize(vertx).onComplete(initialization -> {
      if (initialization.succeeded()) {
        questionService = new QuestionServiceImpl();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().failureHandler(FailureHandler::handleExceptions);

        router.post("/api/v1/question")
            .handler(ctx -> QuestionHandler.handleAddQuestion(ctx, questionService));
        router.get("/api/v1/question")
            .handler(ctx -> QuestionHandler.handleGetQuestions(ctx, questionService));
        router.get("/api/v1/question/:id")
            .handler(ctx -> QuestionHandler.handleGetQuestionById(ctx, questionService));
        router.put("/api/v1/question/:id")
            .handler(ctx -> QuestionHandler.handleUpdateQuestion(ctx, questionService));
        router.delete("/api/v1/question/:id")
            .handler(ctx -> QuestionHandler.handleDeleteQuestion(ctx, questionService));

        vertx.createHttpServer().requestHandler(router::handle).listen(8887, res -> {
          if (res.succeeded()) {
            System.out.println("HTTP server started on port 8887");
          } else {
            res.cause().printStackTrace();
          }
        });
      } else {
        System.out.println("Error initializing DatabaseConfig: " + initialization.cause());
      }
    });
  }
}