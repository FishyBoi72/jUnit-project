package org.example;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookService bookService;
    private UserService userService;
    private User mockUser;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        mockUser = mock(User.class);
    }

    @Test
    void testSearchBookByTitle() {
        bookService.addBook(new Book("1984", "George Orwell", "Dystopian", 9.99));
        List<Book> books = bookService.searchBook("1984");
        assertEquals(1, books.size(), "Should find one book with the title '1984'");
    }

    @Test
    void testSearchBookByNonExistentTitle() {
        List<Book> books = bookService.searchBook("NonExistentTitle");
        assertEquals(0, books.size(), "Should find no books with the title 'NonExistentTitle'");
    }

    @Test
    void testSearchBookByGenre() {
        bookService.addBook(new Book("1984", "George Orwell", "Dystopian", 9.99));
        List<Book> books = bookService.searchBook("Dystopian");
        assertEquals(1, books.size(), "Should find one book in the genre 'Dystopian'");
    }

    @Test
    void testPurchaseBookSuccess() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        bookService.addBook(book);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(book));
        assertTrue(bookService.purchaseBook(mockUser, book), "Purchase should be successful if the book exists in the database");
    }

    @Test
    void testPurchaseBookNotAvailable() {
        Book book = new Book("NonExistentBook", "Unknown Author", "Fiction", 19.99);
        assertFalse(bookService.purchaseBook(mockUser, book), "Purchase should fail if the book is not available");
    }

    @Test
    void testAddBookReviewSuccess() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(book));
        boolean reviewAdded = bookService.addBookReview(mockUser, book, "Great book!");
        assertTrue(reviewAdded, "Review should be added successfully if the user has purchased the book");
        assertEquals(1, book.getReviews().size(), "There should be one review for the book");
    }

    @Test
    void testAddBookReviewWithoutPurchase() {
        Book book = new Book("1984", "George Orwell", "Dystopian", 9.99);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of());
        boolean reviewAdded = bookService.addBookReview(mockUser, book, "Great book!");
        assertFalse(reviewAdded, "Review should not be added if the user has not purchased the book");
        assertEquals(0, book.getReviews().size(), "There should be no reviews for the book");
    }
}