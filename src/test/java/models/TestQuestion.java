package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestQuestion {

    private Question question;

    @BeforeEach
    public void setUp() {
        question = new Question();

        // Set up a sample question with some data
        question.setId(1);
        question.setText("What is the capital of France?");
        question.setSubjectId(1);
        question.setTimesAsked(10);
        question.setTimesAnsweredCorrectly(7);
        question.setTimesAnsweredIncorrectly(3);

        Answer answer1 = new Answer();
        answer1.setId(1);
        answer1.setText("Paris");
        answer1.setCorrect(true);
        answer1.setQuestion(question);

        Answer answer2 = new Answer();
        answer2.setId(2);
        answer2.setText("London");
        answer2.setCorrect(false);
        answer2.setQuestion(question);

        List<Answer> answers = Arrays.asList(answer1, answer2);
        question.setAnswers(answers);
    }

    @Test
    public void testQuestionProperties() {
        assertEquals(1, question.getId());
        assertEquals("What is the capital of France?", question.getText());
        assertEquals(1, question.getSubjectId());
        assertEquals(10, question.getTimesAsked());
        assertEquals(7, question.getTimesAnsweredCorrectly());
        assertEquals(3, question.getTimesAnsweredIncorrectly());

        List<Answer> answers = question.getAnswers();
        assertEquals(2, answers.size());

        Answer answer1 = answers.get(0);
        assertEquals(1, answer1.getId());
        assertEquals("Paris", answer1.getText());
        assertEquals(true, answer1.isCorrect());
        assertEquals(question, answer1.getQuestion());

        Answer answer2 = answers.get(1);
        assertEquals(2, answer2.getId());
        assertEquals("London", answer2.getText());
        assertEquals(false, answer2.isCorrect());
        assertEquals(question, answer2.getQuestion());
    }
}
