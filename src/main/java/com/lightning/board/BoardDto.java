package com.lightning.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class BoardDto {

    @AllArgsConstructor
    static class Create {
        @Getter
        @Setter
        private String title;
        @Getter
        @Setter
        private String content;
        @Getter
        @Setter
        private String author;
        @Getter
        @Setter
        private String password;
    }

    @AllArgsConstructor
    static class Update {
        @Getter
        @Setter
        private String title;
        @Getter
        @Setter
        private String content;
        @Getter
        @Setter
        private String password;
    }

    @AllArgsConstructor
    static class Delete {
        @Getter
        @Setter
        private String password;
    }
}
