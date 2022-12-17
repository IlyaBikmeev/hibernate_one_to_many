package org.example;

import org.example.model.Author;
import org.example.model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main( String[] args ) {
        //Будем здесь делать транзакции
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Author.class)
                .addAnnotatedClass(Book.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        //Получить список книг для конкретного автора
        Author author1 = session.get(Author.class, 2);  //Автор с id 2
        List<Book> books = author1.getBooks();
        System.out.printf("Автор %s написал следующие книги:\n", author1.getName());
        books.forEach(b -> System.out.println(b.getTitle()));

        //Добавить конкретную книгу автору
        Author author2 = session.get(Author.class, 1);
        Book book = new Book("Капитанская дочка", author2);
        author2.getBooks().add(book);   //Обязательно делаем связь с двух сторон!!!
        session.save(book);

        //На досуге посмотрите разницу:
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 3, 6));
        List<Integer> list2 = new ArrayList<>(List.of(1, 3, 6));
        List<Integer> list3 = Collections.singletonList(6);

        //Создать нового автора и добавить ему книгу
        Author author3 = new Author("Фёдор Михайлович Достоевский");
        Book book3 = new Book("Преступление и наказание", author3);
//        //Связь с двух сторон!
        author3.setBooks(new ArrayList<>(Collections.singletonList(book3)));

        session.save(author3);
        session.save(book3);

        //Удалить все книги у автора
        Author targetAuthor = session.get(Author.class, 3);
        targetAuthor.getBooks()
                .forEach(session::remove);      //Удаляем в базе
        targetAuthor.getBooks().clear();        //Удаляем на уровне объекта

        //Удалить автора
        Author authorToBeRemoved = session.get(Author.class, 1);
        session.remove(authorToBeRemoved);  //Удаляем из бд данного автора.
//        //У каждой книги данного автора устанавливаем автора как null
        authorToBeRemoved.getBooks().forEach(b -> b.setWriter(null));

        //Поменять автора у книги
        Author newWriter = session.get(Author.class, 4);
        Book bookToBeChanged = session.get(Book.class, 4);
        //У настоящего автора из списка его книг нужно удалить эту книгу
        bookToBeChanged.getWriter().getBooks().remove(bookToBeChanged);
        //Устанавливаем у этой книги нового автора
        bookToBeChanged.setWriter(newWriter);
        //Добавляем в список книг нового автора данную книгу
        newWriter.getBooks().add(bookToBeChanged);

        session.getTransaction().commit();      //Сохраняем изменения
        session.close();
    }
}
