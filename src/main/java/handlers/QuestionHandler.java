package handlers;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import models.Question;
import services.QuestionService;

public class QuestionHandler {

    public static void handleAddQuestion(RoutingContext ctx, QuestionService questionService) {
        Question bodyQuestion = ctx.body().asPojo(Question.class);
        Future.fromCompletionStage(questionService.createQuestion(bodyQuestion))
                .onSuccess(returnQuestion -> {
                    JsonObject jsonQuestion = JsonObject.mapFrom(returnQuestion);
                    ctx.response()
                            .setStatusCode(201)
                            .putHeader("content-type", "application/json")
                            .end(jsonQuestion.toString());
                })
                .onFailure(ctx::fail);
    }

    public static void handleGetQuestions(RoutingContext ctx, QuestionService questionService) {
        Future.fromCompletionStage(questionService.getAllQuestions())
                .onSuccess(questions -> {
                    List<JsonObject> jsonQuestions = new ArrayList<>();
                    for (Question question : questions) {
                        jsonQuestions.add(JsonObject.mapFrom(question));
                    }
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json")
                            .end(jsonQuestions.toString());
                })
                .onFailure(ctx::fail);
    }

    public static void handleGetQuestionById(RoutingContext ctx, QuestionService questionService) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Future.fromCompletionStage(questionService.getQuestion(id))
                .onSuccess(question -> {
                    JsonObject jsonQuestion = JsonObject.mapFrom(question);
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json")
                            .end(jsonQuestion.toString());
                })
                .onFailure(ctx::fail);
    }

    public static void handleUpdateQuestion(RoutingContext ctx, QuestionService questionService) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        JsonObject bodyQuestion = ctx.body().asJsonObject();

        Future.fromCompletionStage(questionService.updateQuestion(id, bodyQuestion))
                .onSuccess(updatedQuestion -> {
                    JsonObject jsonQuestion = JsonObject.mapFrom(updatedQuestion);
                    ctx.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json")
                            .end(jsonQuestion.toString());
                })
                .onFailure(ctx::fail);
    }

    public static void handleDeleteQuestion(RoutingContext ctx, QuestionService questionService) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Future.fromCompletionStage(questionService.deleteQuestion(id))
                .onSuccess(deletedQuestion -> {
                    ctx.response()
                            .setStatusCode(204)
                            .end();
                })
                .onFailure(ctx::fail);
    }
}
