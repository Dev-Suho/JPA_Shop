package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.Item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
public class ItemUpdateTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void updateTest() {
        Book book = em.find(Book.class, 1L);

        book.setName("asdf");

        // 변경 감지 - dirty checking
    }
}
