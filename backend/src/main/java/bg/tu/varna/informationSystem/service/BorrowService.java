package bg.tu.varna.informationSystem.service;

import bg.tu.varna.informationSystem.common.BookStatuses;
import bg.tu.varna.informationSystem.common.DateTimeParser;
import bg.tu.varna.informationSystem.common.Messages;
import bg.tu.varna.informationSystem.dto.book.BookResponseDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowResponseDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnRequestDto;
import bg.tu.varna.informationSystem.dto.borrow.BorrowReturnResponseDto;
import bg.tu.varna.informationSystem.dto.users.UserResponseDto;
import bg.tu.varna.informationSystem.entity.Book;
import bg.tu.varna.informationSystem.entity.Borrow;
import bg.tu.varna.informationSystem.entity.User;
import bg.tu.varna.informationSystem.exception.BadRequestException;
import bg.tu.varna.informationSystem.repository.BorrowRepository;
import bg.tu.varna.informationSystem.utils.UserPrincipalUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, BookService bookService, UserService userService, ModelMapper modelMapper) {
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public Borrow findById(Long borrowId) {
        return borrowRepository.findById(borrowId)
                .orElseThrow(() -> new BadRequestException(Messages.BORROW_NOT_FOUND, String.valueOf(borrowId)));
    }

    public List<BorrowResponseDto> findAll() {
        return borrowRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public BorrowResponseDto save(BorrowRequestDto borrowRequestDto) {
        Book book = bookService.findById(borrowRequestDto.getBookId());

        if (!book.getStatus().equals(BookStatuses.AVAILABLE.toString())) {
            throw new BadRequestException(Messages.BOOK_NOT_AVAILABLE);
        }

        Borrow borrow = borrowRepository.save(convertToEntity(borrowRequestDto));
        bookService.update(borrowRequestDto.getBookId(), BookStatuses.BORROWED.toString());

        return convertToResponseDto(borrow);
    }

    @Transactional
    public BorrowReturnResponseDto update(Long id, BorrowReturnRequestDto borrowReturnRequestDto) {
        Borrow borrow = findById(id);
        Book book = bookService.findById(borrowReturnRequestDto.getBookId());

        if (!book.getStatus().equals(BookStatuses.BORROWED.toString())
                || (borrow.getDateReturned() != null && borrow.getDateReturned().toString().equals(borrowReturnRequestDto.getDateReturned()))
        ) {
            throw new BadRequestException(Messages.BOOK_ALREADY_RETURNED);
        }

        borrow.setDateReturned(DateTimeParser.parse(borrowReturnRequestDto.getDateReturned()));
        bookService.update(borrowReturnRequestDto.getBookId(), BookStatuses.AVAILABLE.toString());

        return convertToReturnResponseDto(borrowRepository.save(borrow));
    }

    private Borrow convertToEntity(BorrowRequestDto borrowRequestDto) {
        Book book = bookService.findById(borrowRequestDto.getBookId());
        User operator = userService.findById(UserPrincipalUtils.getPrincipalFromContext().getId());
        User reader = userService.findById(borrowRequestDto.getReaderId());

        Borrow borrow = modelMapper.map(borrowRequestDto, Borrow.class);
        borrow.setBook(book);
        borrow.setOperator(operator);
        borrow.setReader(reader);
        borrow.setDateDueReturn(DateTimeParser.parse(borrowRequestDto.getDateDueReturn()));

        return borrow;
    }

    private BorrowResponseDto convertToResponseDto(Borrow borrow) {
        BorrowResponseDto borrowResponseDto = modelMapper.map(borrow, BorrowResponseDto.class);
        UserResponseDto operator = userService.getById(borrow.getOperator().getId());
        UserResponseDto reader = userService.getById(borrow.getReader().getId());
        BookResponseDto book = bookService.getById(borrow.getBook().getId());
        borrowResponseDto.setOperator(operator);
        borrowResponseDto.setReader(reader);
        borrowResponseDto.setBook(book);

        borrowResponseDto.setDateAdded(borrow.getDateAdded().toString());
        borrowResponseDto.setDateDueReturn(borrow.getDateDueReturn().toString());

        return borrowResponseDto;
    }

    private BorrowReturnResponseDto convertToReturnResponseDto(Borrow borrow) {
        BorrowReturnResponseDto borrowReturnResponseDto = modelMapper.map(borrow, BorrowReturnResponseDto.class);
        borrowReturnResponseDto.setDateReturned(borrow.getDateReturned().toString());
        borrowReturnResponseDto.setReturned(true);

        return borrowReturnResponseDto;
    }

    public List<BorrowResponseDto> findAllByReader(Long id) {
        return borrowRepository.findAllByReader(id).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public List<BorrowResponseDto> findAllNotReturnedByReader(Long id) {
        return borrowRepository.findAllNotReturnedByReader(id).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public List<BorrowResponseDto> findAllByOperator(Long id) {
        return borrowRepository.findAllByOperator(id).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }
}
