package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudyCafeFileHandlerTest {

    private StudyCafeFileHandler studyCafeFileHandler;

    @BeforeEach
    void setUp() {
        studyCafeFileHandler = new StudyCafeFileHandler(StudyCafePassType.HOURLY);
    }

    @Nested
    @DisplayName("시간제 패스 조회 테스트")
    class HourlyPassTests {
        @Test
        @DisplayName("시간제 패스 목록 조회 테스트")
        void testGetHourlyPasses() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.HOURLY);
            List<StudyCafePass> expectedPasses = Arrays.asList(
                StudyCafePass.of(StudyCafePassType.HOURLY, 1, 5000, 1.0),
                StudyCafePass.of(StudyCafePassType.HOURLY, 2, 9000, 1.0)
            );

            // when
            List<StudyCafePass> actualPasses = handler.getHourlyPasses();

            // then
            assertNotNull(actualPasses);
            assertFalse(actualPasses.isEmpty());
            actualPasses.forEach(pass -> 
                assertEquals(StudyCafePassType.HOURLY, pass.getPassType())
            );
        }
    }

    @Nested
    @DisplayName("주간 패스 조회 테스트")
    class WeeklyPassTests {
        @Test
        @DisplayName("주간 패스 목록 조회 테스트")
        void testGetWeeklyPasses() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.WEEKLY);
            List<StudyCafePass> expectedPasses = Arrays.asList(
                StudyCafePass.of(StudyCafePassType.WEEKLY, 7, 50000, 0.9),
                StudyCafePass.of(StudyCafePassType.WEEKLY, 14, 90000, 0.9)
            );

            // when
            List<StudyCafePass> actualPasses = handler.getHourlyPasses();

            // then
            assertNotNull(actualPasses);
            assertFalse(actualPasses.isEmpty());
            actualPasses.forEach(pass -> 
                assertEquals(StudyCafePassType.WEEKLY, pass.getPassType())
            );
        }
    }

    @Nested
    @DisplayName("고정석 패스 조회 테스트")
    class FixedPassTests {
        @Test
        @DisplayName("고정석 패스 목록 조회 테스트")
        void testGetFixedPasses() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.FIXED);
            List<StudyCafePass> expectedPasses = Arrays.asList(
                StudyCafePass.of(StudyCafePassType.FIXED, 30, 100000, 0.8),
                StudyCafePass.of(StudyCafePassType.FIXED, 90, 280000, 0.8)
            );

            // when
            List<StudyCafePass> actualPasses = handler.getHourlyPasses();

            // then
            assertNotNull(actualPasses);
            assertFalse(actualPasses.isEmpty());
            actualPasses.forEach(pass -> 
                assertEquals(StudyCafePassType.FIXED, pass.getPassType())
            );
        }

        @Test
        @DisplayName("고정석 패스의 락커 패스 조회 테스트")
        void testGetLockerPass() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.FIXED);
            StudyCafePass fixedPass = StudyCafePass.of(StudyCafePassType.FIXED, 30, 100000, 0.8);

            // when
            StudyCafeLockerPass lockerPass = handler.getLockerPass(fixedPass);

            // then
            assertNotNull(lockerPass);
            assertEquals(StudyCafePassType.FIXED, lockerPass.getPassType());
            assertEquals(30, lockerPass.getDuration());
            assertEquals(20000, lockerPass.getPrice());
        }
    }

    @Nested
    @DisplayName("CSV 파일 읽기 테스트")
    class CsvFileReadTests {
        @Test
        @DisplayName("잘못된 파일 경로로 인한 예외 발생 테스트")
        void testInvalidFilePath() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.HOURLY);

            // when & then
            assertThrows(RuntimeException.class, () -> {
                handler.readCsvFile("invalid-file.csv", values -> values[0]);
            });
        }

        @Test
        @DisplayName("잘못된 데이터 형식으로 인한 예외 발생 테스트")
        void testInvalidDataFormat() {
            // given
            StudyCafeFileHandler handler = new StudyCafeFileHandler(StudyCafePassType.HOURLY);

            // when & then
            assertThrows(NumberFormatException.class, () -> {
                handler.readCsvFile("pass-list.csv", values -> {
                    int price = Integer.parseInt(values[2]);
                    return price;
                });
            });
        }
    }
}