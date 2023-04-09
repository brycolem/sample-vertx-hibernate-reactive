package services;

import models.Question;
import java.util.List;
import java.util.concurrent.CompletionStage;

import exceptions.RecordNotFoundException;
import io.vertx.core.json.JsonObject;

public interface QuestionService {
    CompletionStage<Question> createQuestion(Question question);

    CompletionStage<Question> getQuestion(int id) throws RecordNotFoundException;

    CompletionStage<List<Question>> getAllQuestions();

    CompletionStage<Question> updateQuestion(int id, JsonObject question) throws RecordNotFoundException;

    CompletionStage<Boolean> deleteQuestion(int id);
}
