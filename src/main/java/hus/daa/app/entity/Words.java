package hus.daa.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "words", indexes = @Index(name = "idx_word", columnList = "word"))
@Data
public class Words {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "word")
    private String word;
}
