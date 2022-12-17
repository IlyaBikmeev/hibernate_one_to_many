package org.example.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Author")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    //writer - объект, который описан в классе Book. Там уже описаны связи с сущностью Author
    @OneToMany(mappedBy = "writer")
    private List<Book> books;

    public Author(String name) {
        this.name = name;
    }
}
