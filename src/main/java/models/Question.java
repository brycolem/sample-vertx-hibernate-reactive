package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String text;

    // Reference to the subject list endpoint
    @Column(name = "subject_id")
    private Integer subjectId;
    @Column
    private String answers;
    @Column
    private Integer correctAnswerIndex;
    @Column
    private int timesAsked;
    @Column
    private int timesAnsweredCorrectly;
    @Column
    private int timesAnsweredIncorrectly;
}
