package com.lightning.board;

import com.lightning.constants.CommonApiConstant;
import com.lightning.exceptions.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = CommonApiConstant.PREFIX)
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(final BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @PostMapping(
            value = BoardApiConstant.BOARDS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Board> add(@RequestBody final BoardDto.Create data) {
        return ResponseEntity.ok(boardRepository.save(new Board(data.getTitle(), data.getContent(), data.getAuthor(), data.getPassword())));
    }

    @GetMapping(
            value = BoardApiConstant.BOARDS + "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Board> get(@PathVariable final Long id) {
        return ResponseEntity.ok(
                boardRepository.findByIdAndDeletedFalse(id)
                        .orElseThrow(() -> new ResourceNotFound("Please check the board ID"))
        );
    }

    @GetMapping(
            value = BoardApiConstant.BOARDS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Page<Board>> getList(@SortDefault(sort = "createdDate", direction = Sort.Direction.DESC) final Pageable pageable) {
        return ResponseEntity.ok(boardRepository.findAllByDeletedFalse(pageable).orElse(new PageImpl(emptyList())));
    }

    @PatchMapping(
            value = BoardApiConstant.BOARDS + "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Board> fetch(@PathVariable final Long id,
                                @RequestBody final BoardDto.Update board) {
        return ResponseEntity.ok(fetchBoard(id, board));
    }

    @DeleteMapping(
            value = BoardApiConstant.BOARDS + "/{id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    ResponseEntity<Board> delete(@PathVariable final Long id,
                                 @RequestParam() final String password) {
        deleteBoard(id, password);
        return ResponseEntity.noContent().build();
    }

    private Board fetchBoard(final Long id,
                             final BoardDto.Update board) {

        final Long boardId = Optional.of(id).orElseThrow(() -> new IllegalArgumentException("The ID is required"));

        final Board entity = boardRepository
                .findByIdAndDeletedFalse(boardId)
                .orElseThrow(() -> new ResourceNotFound("Please check the board ID"));
        entity.changeTitle(board);
        entity.changeContent(board);

        boardRepository.save(entity);

        return entity;
    }

    private void deleteBoard(final Long id,
                             final String password) {
        final Board entity = boardRepository.findByIdAndDeletedFalse(Optional.of(id).orElseThrow(() -> new IllegalArgumentException("The ID is required")))
                .orElseThrow(() -> new ResourceNotFound("Please check the board ID"));
        entity.delete(password);
        boardRepository.save(entity);
    }
}
