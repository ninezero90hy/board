package com.lightning.board.utils;

import com.lightning.board.Board;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class BoardFixturesUtil {
    public static Board generateRandomBoard() {
        final Random random = new Random();
        final Board board = new Board(generateRandomString() + "날씨 알려드립니다", "오늘은 " + generateRandomString() + "가 좋습니다", "홍길동", "anonymous123");
        final LocalDateTime createdDate = LocalDate.now().atTime(random.nextInt(10), random.nextInt(5) + random.nextInt(10));
        board.setCreatedDate(createdDate);
        return board;
    }

    private static String generateRandomString() {

        final StringBuffer stringBuffer = new StringBuffer();
        final Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    stringBuffer.append((char) ((int) (random.nextInt(26)) + 97));
                    break;
                case 1:
                    stringBuffer.append((char) ((int) (random.nextInt(26)) + 65));
                    break;
                case 2:
                    stringBuffer.append((random.nextInt(10)));
                    break;
            }
        }
        return stringBuffer.toString();
    }

}
