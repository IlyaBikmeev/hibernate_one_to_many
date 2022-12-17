package org.example.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
//Owning side (управляющая сторона)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;


    //@ManyToOne - Указываем, что внешний ключ author_id указывает на первичный ключ
    // в связанной таблице с названием id
    /*
    @JoinColumn
    author_id - внешний ключ в таблице Book
    id - первичный ключ в таблице Author
     */
    @ManyToOne      //Указываем, что "книг много, а автор один"
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author writer;

    public Book(String title, Author writer) {
        this.title = title;
        this.writer = writer;
    }
}
