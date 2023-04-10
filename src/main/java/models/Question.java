package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

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
    private int timesAsked;
    @Column
    private int timesAnsweredCorrectly;
    @Column
    private int timesAnsweredIncorrectly;

    // One-to-many relationship with answers
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Answer> answers;
}
