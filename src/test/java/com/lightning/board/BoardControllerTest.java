package com.lightning.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightning.constants.CommonApiConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.lightning.board.utils.BoardFixturesUtil.generateRandomBoard;
import static java.util.Optional.of;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BoardRepository boardRepository;

    @Test
    public void 제목이_20자를_넘을수_없다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.save(any())).thenReturn(entity);

        final BoardDto.Create content = new BoardDto.Create("가나다라마가나다라마가나다라마가나다라마가", "내용", "홍길동", "password123");

        mockMvc.perform(
                post(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(content))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 내용은_200자를_넘을수_없다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.save(any())).thenReturn(entity);

        final BoardDto.Create content = new BoardDto.Create("제목", "가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마", "홍길동", "password123");

        mockMvc.perform(
                post(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(content))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 비밀번호는_6이상_영어와_숫자가_조합_되어야한다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.save(any())).thenReturn(entity);

        final BoardDto.Create content = new BoardDto.Create("제목", "내용", "홍길동", "pass1");

        mockMvc.perform(
                post(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(content))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void 올바른_값인_경우_글을_등록할_수_있다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.save(any())).thenReturn(entity);

        mockMvc.perform(
                post(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(board))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 존재하지_않는_아이디로는_글을_조회할_수_없다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(2L)).thenReturn(of(entity));

        mockMvc.perform(
                get(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void 존재하는_아이디는_글을_조회할_수_있다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(1L)).thenReturn(of(entity));

        mockMvc.perform(
                get(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 글_수정시_비밀번호를_입력받고_기존_비밀번호와_같을_경우_글_제목과_내용을_수정할_수_있다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(1L)).thenReturn(of(entity));

        final BoardDto.Update content = new BoardDto.Update("코로나 현재", "확진자가 늘고 있습니다", "anonymous123");

        mockMvc.perform(
                patch(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(content))
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void 글_수정시_비밀번호를_입력받고_기존_비밀번호와_일치하지_않으면_수정할_수_없다는_응답을_반환한다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(1L)).thenReturn(of(entity));

        final BoardDto.Update content = new BoardDto.Update("코로나 현재", "확진자가 늘고 있습니다", "test123");

        mockMvc.perform(
                patch(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(content))
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 글_삭제시_비밀번호를_입력받고_기존_비밀번호와_일치하면_삭제처리한다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(1L)).thenReturn(of(entity));

        mockMvc.perform(
                delete(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .param("password", "anonymous123")
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void 글_삭제시_비밀번호를_입력받고_기존_비밀번호와_일치하지_않으면_삭제_처리하지_않는다() throws Exception {

        final BoardDto.Create board = new BoardDto.Create("날씨 알려드립니다", "오늘은 날씨가 좋습니다", "홍길동", "anonymous123");
        final Board entity = new Board(board.getTitle(), board.getContent(), board.getAuthor(), board.getPassword());
        Mockito.when(boardRepository.findByIdAndDeletedFalse(1L)).thenReturn(of(entity));

        mockMvc.perform(
                delete(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS + "/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .param("password", "test123")
        )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void 범위내에_존재하는_페이지_번호로_목록_조회하면_정상적으로_컨텐츠가_페이지_사이즈_만큼_반환된다() throws Exception {

        final int page = 0;
        final int size = 10;
        final String sort = "createdDate";
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, sort);

        List<Board> boards = new ArrayList();
        for (int i = 0; i < 10; i++) {
            boards.add(generateRandomBoard());
        }
        Mockito.when(boardRepository.findAllByDeletedFalse(pageRequest)).thenReturn(of(new PageImpl(boards)));

        mockMvc.perform(
                get(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").value(hasSize(10)));
    }

    @Test
    public void 범위에_존재하지_않는_페이지_번호로_목록_조회하면_컨텐츠는_빈_배열로_반환된다() throws Exception {

        final int size = 10;
        final PageRequest pageRequest = PageRequest.of(0, size);

        List<Board> boards = new ArrayList();
        for (int i = 0; i < 10; i++) {
            boards.add(generateRandomBoard());
        }
        Mockito.when(boardRepository.findAllByDeletedFalse(pageRequest)).thenReturn(of(new PageImpl(boards)));

        mockMvc.perform(
                get(CommonApiConstant.PREFIX + BoardApiConstant.BOARDS)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE)
                        .param("page", "1")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").value(hasSize(0)));
    }
}
