package com.lightning.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lightning.exceptions.IllegalBoardPasswordLengthException;
import com.lightning.exceptions.IllegalBoardPasswordRuleException;
import com.lightning.exceptions.PasswordsNotSameException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.util.regex.Pattern.compile;

@Entity
@EqualsAndHashCode(of = "id")
public class Board {

    private final static int TITLE_MAX_LENGTH = 20;
    private final static int CONTENT_MAX_LENGTH = 200;
    private final static String PASSWORD_RULE = "^[a-zA-Z0-9]*$";

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(length = TITLE_MAX_LENGTH)
    @Getter
    private String title;

    @Column(length = CONTENT_MAX_LENGTH)
    @Getter
    private String content;

    @Getter
    private String author;

    @Getter
    @JsonIgnore
    private String password;

    @Getter
    @JsonIgnore
    private boolean deleted = false;

    @Type(type = "org.hibernate.type.LocalDateTimeType")
    @Getter
    @Setter
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    public Board() {
    }

    public Board(final String title,
                 final String content,
                 final String author,
                 final String password) {

        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid title value");
        }

        if (content.length() > CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid content value");
        }

        verifyPassword(password);

        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
    }

    private void verifyPassword(final String password) {

        if (password.length() < 6) {
            throw new IllegalBoardPasswordLengthException("Password must be at least 6 characters long");
        }

        if (!compile(PASSWORD_RULE).matcher(password).matches()) {
            throw new IllegalBoardPasswordRuleException("Password rule is not valid");
        }
    }

    public void changeTitle(BoardDto.Update board) {
        checkPassword(board.getPassword());
        Optional.of(board.getTitle()).ifPresent(title -> this.title = title);
    }

    public void changeContent(BoardDto.Update board) {
        checkPassword(board.getPassword());
        Optional.of(board.getContent()).ifPresent(content -> this.content = content);
    }

    private void checkPassword(final String password) {
        if (!this.password.equals(password)) {
            throw new PasswordsNotSameException();
        }
    }

    public void delete(final String password) {
        checkPassword(password);
        this.deleted = true;
    }
}
