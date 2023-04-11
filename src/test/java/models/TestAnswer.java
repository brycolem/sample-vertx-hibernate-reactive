package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnswer {

    private Answer answer;
    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();
        question.setId(1);
        question.setText("What is the capital of France?");
        question.setSubjectId(1);

        answer = new Answer();

        // Set up a sample answer with some data
        answer.setId(1);
        answer.setText("Paris");
        answer.setCorrect(true);
        answer.setQuestion(question);
    }

    @Test
    public void testAnswerProperties() {
        assertEquals(1, answer.getId());
        assertEquals("Paris", answer.getText());
        assertEquals(true, answer.isCorrect());
        assertEquals(question, answer.getQuestion());
    }
}
