package com.lightning.board;

import com.lightning.exceptions.IllegalBoardPasswordLengthException;
import com.lightning.exceptions.IllegalBoardPasswordRuleException;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BoardTest {

    @Test
    public void 게시글은_제목_내용_작성자이름_비밀번호로_구성됨() {

        // Given
        final String title = "제목";
        final String content = "내용";
        final String author = "송희윤";
        final String password = "password123";

        // When
        final Board board = new Board(title, content, author, password);

        // Then
        assertThat(board).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void 제목은_20자를_넘으면_오류가_발생한다() {

        // Given
        final String title = "가나다라마가나다라마가나다라마가나다라마가";
        final String content = "내용";
        final String author = "송희윤";
        final String password = "password!@#";

        // When
        new Board(title, content, author, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 내용은_200자를_넘을_수_없으면_오류가_발생한다() {

        // Given
        final String title = "제목";
        final String content = "가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마가나다라마";
        final String author = "송희윤";
        final String password = "password!@#";

        // When
        new Board(title, content, author, password);
    }

    @Test(expected = IllegalBoardPasswordLengthException.class)
    public void 비밀번호는_6자_이상_되지_않으면_오류가_발생한다() {

        // Given
        final String title = "제목";
        final String content = "내용";
        final String author = "송희윤";
        final String password = "passw";

        // When
        new Board(title, content, author, password);
    }

    @Test(expected = IllegalBoardPasswordRuleException.class)
    public void 비밀번호는_영어와_숫자를_조합하지_않은_경우_오류_발생한다() {

        // Given
        final String title = "제목";
        final String content = "내용";
        final String author = "송희윤";
        final String password = "password@";

        // When
        new Board(title, content, author, password);
    }

    @Test
    public void 비밀번호는_6자_이상_영어와_숫자를_조합해야한다() {

        // Given
        final String title = "제목";
        final String content = "내용";
        final String author = "송희윤";
        final String password = "password123";

        // When
        new Board(title, content, author, password);
    }
}
