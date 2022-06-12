package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.annotations.implementations.EnumValidator;
import bg.tu.varna.informationSystem.common.BookStatuses;
import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.book.BookRequestDto;
import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.entity.Book;
import bg.tu.varna.informationSystem.entity.Genre;
import bg.tu.varna.informationSystem.entity.ReadingRoom;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final ReadingRoomService readingRoomService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, GenreService genreService, ReadingRoomService readingRoomService, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.readingRoomService = readingRoomService;
        this.modelMapper = modelMapper;
    }

    public BookResponseDto save(BookRequestDto bookRequestDto) {
        Book book = convertToEntity(bookRequestDto);
        book.setStatus(BookStatuses.AVAILABLE.toString());
        book = bookRepository.save(book);
        return convertToResponseDto(book);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public BookResponseDto update(Long id, String status) {
        Book book = findById(id);

        if (status.equals(book.getStatus())) {
            throw new BadRequestException(Messages.BOOK_STATUS_ALREADY_SET, book.getStatus());
        }

        book.setStatus(status);
        book = bookRepository.save(book);
        return convertToResponseDto(book);
    }

    public Book findById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BadRequestException("Book not found!"));
    }

    private Book convertToEntity(BookRequestDto bookRequestDto) {
        Genre genre = genreService.findById(bookRequestDto.getGenreId());
        ReadingRoom readingRoom = readingRoomService.findById(bookRequestDto.getReadingRoomId());
        Book book = modelMapper.map(bookRequestDto, Book.class);
        book.setAuthor(bookRequestDto.getAuthor());
        book.setGenre(genre);
        book.setReadingRoom(readingRoom);

        return book;
    }

    private BookResponseDto convertToResponseDto(Book book) {
        BookResponseDto bookResponseDto = modelMapper.map(book, BookResponseDto.class);
        bookResponseDto.setGenre(genreService.getById(book.getGenre().getId()));
        bookResponseDto.setReadingRoomId(book.getReadingRoom().getId());
        return bookResponseDto;
    }

    public BookResponseDto getById(Long id) {
        return convertToResponseDto(findById(id));
    }

    public List<BookResponseDto> findAll() {
        return bookRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public List<BookResponseDto> findAllByStatus(String status) {
        Boolean valid = Arrays.stream(BookStatuses.values()).anyMatch((b) -> b.toString().equals(status));

        if (!valid) {
            throw new BadRequestException(Messages.INVALID_BOOK_STATUS);
        }

        return bookRepository.findAllByStatus(status).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }
}
