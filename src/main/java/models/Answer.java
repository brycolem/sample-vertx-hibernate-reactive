package models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String text;
    @Column
    private boolean correct;

    // Reference to the associated question
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;
}
