package services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import models.Answer;
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
        return sessionFactory.withTransaction((session, transaction) -> {
            return session.persist(question)
                    .thenCompose(v -> {
                        List<CompletionStage<Void>> answerStages = new ArrayList<>();
                        for (Answer answer : question.getAnswers()) {
                            answer.setQuestion(question);
                            answerStages.add(session.persist(answer));
                        }
                        return CompletableFuture.allOf(answerStages.toArray(new CompletableFuture<?>[0]));
                    })
                    .thenApply(v -> question);
        });
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

                        // Update existing answers
                        List<Answer> existingAnswers = existingQuestion.getAnswers();
                        for (int i = 0; i < existingAnswers.size(); i++) {
                            Answer existingAnswer = existingAnswers.get(i);
                            Answer newAnswer = question.getJsonArray("answers").getJsonObject(i).mapTo(Answer.class);
                            existingAnswer.setText(newAnswer.getText());
                            existingAnswer.setCorrect(newAnswer.isCorrect());
                        }

                        return session.merge(existingQuestion)
                                .thenApply(updatedQuestion -> updatedQuestion);
                    } else {
                        String message = String.format("Record with the ID of %d Not Found", id);
                        return CompletableFuture.failedFuture(new RecordNotFoundException(message));
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
