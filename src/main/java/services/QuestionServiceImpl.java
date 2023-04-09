package services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import models.Question;
import org.hibernate.reactive.stage.Stage;

import configurations.DatabaseConfig;
import exceptions.RecordNotFoundException;
import io.vertx.core.json.JsonObject;

public class QuestionServiceImpl implements QuestionService {
    private final Stage.SessionFactory sessionFactory;

    public QuestionServiceImpl() {
        this.sessionFactory = DatabaseConfig.getSessionFactory();
    }

    @Override
    public CompletionStage<Question> createQuestion(Question question) {
        return sessionFactory.withTransaction((session, transaction) -> session.persist(question)
                .thenApply(v -> question));
    }

    @Override
    public CompletionStage<Question> getQuestion(int id) throws RecordNotFoundException {
        return sessionFactory.withSession(session -> session.find(Question.class, id)
                .thenCompose(question -> {
                    if (question != null) {
                        return CompletableFuture.completedFuture(question);
                    } else {
                        String message = String.format("Record with the ID of %d Not Found", id);
                        return CompletableFuture.failedFuture(new RecordNotFoundException(message));
                    }
                }));
    }

    @Override
    public CompletionStage<List<Question>> getAllQuestions() {
        return sessionFactory.withSession(session -> {
            return session.createQuery("SELECT q FROM Question q", Question.class)
                    .getResultList();
        });
    }

    @Override
    public CompletionStage<Question> updateQuestion(int id, JsonObject question) {
        return sessionFactory.withTransaction((session, tx) -> session.find(Question.class, id)
                .thenCompose(existingQuestion -> {
                    if (existingQuestion != null) {
                        JsonObject mergeQuestion = JsonObject.mapFrom(existingQuestion);
                        question.remove("id");
                        mergeQuestion.mergeIn(question);
                        existingQuestion = mergeQuestion.mapTo(Question.class);
                        return session.merge(existingQuestion)
                                .thenApply(updatedQuestion -> updatedQuestion);
                    } else {
                        String message = String.format("Record with the ID of %d Not Found", id);
                        throw new RuntimeException(new RecordNotFoundException(message));
                    }
                }));
    }

    @Override
    public CompletionStage<Boolean> deleteQuestion(int id) {
        return sessionFactory.withTransaction((session, tx) -> session.find(Question.class, id)
                .thenCompose(question -> {
                    return session.remove(question)
                            .thenApply(ignore -> true);
                }));
    }

}
