package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.exception.AppException;
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

class InputHandlerTest {

    private InputHandler inputHandler;

    @BeforeEach
    void setUp() {
        inputHandler = new InputHandler();
    }

    @Nested
    @DisplayName("패스 타입 선택 테스트")
    class PassTypeSelectionTests {
        @Test
        @DisplayName("시간제 패스 타입 선택 테스트")
        void testSelectHourlyPassType() {
            // given
            String input = "1";

            // when
            StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

            // then
            assertEquals(StudyCafePassType.HOURLY, result);
        }

        @Test
        @DisplayName("주간 패스 타입 선택 테스트")
        void testSelectWeeklyPassType() {
            // given
            String input = "2";

            // when
            StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

            // then
            assertEquals(StudyCafePassType.WEEKLY, result);
        }

        @Test
        @DisplayName("고정석 패스 타입 선택 테스트")
        void testSelectFixedPassType() {
            // given
            String input = "3";

            // when
            StudyCafePassType result = inputHandler.getPassTypeSelectingUserAction();

            // then
            assertEquals(StudyCafePassType.FIXED, result);
        }

        @Test
        @DisplayName("잘못된 패스 타입 선택 시 예외 발생 테스트")
        void testInvalidPassTypeSelection() {
            // given
            String input = "4";

            // when & then
            assertThrows(AppException.class, () -> {
                inputHandler.getPassTypeSelectingUserAction();
            });
        }
    }

    @Nested
    @DisplayName("패스 선택 테스트")
    class PassSelectionTests {
        @Test
        @DisplayName("유효한 패스 선택 테스트")
        void testValidPassSelection() {
            // given
            List<StudyCafePass> passes = Arrays.asList(
                StudyCafePass.of(StudyCafePassType.HOURLY, 1, 5000, 1.0),
                StudyCafePass.of(StudyCafePassType.HOURLY, 2, 9000, 1.0)
            );
            String input = "1";

            // when
            StudyCafePass result = inputHandler.getSelectPass(passes);

            // then
            assertNotNull(result);
            assertEquals(StudyCafePassType.HOURLY, result.getPassType());
            assertEquals(5000, result.getPrice());
        }

        @Test
        @DisplayName("잘못된 패스 선택 시 예외 발생 테스트")
        void testInvalidPassSelection() {
            // given
            List<StudyCafePass> passes = Arrays.asList(
                StudyCafePass.of(StudyCafePassType.HOURLY, 1, 5000, 1.0)
            );
            String input = "2";

            // when & then
            assertThrows(AppException.class, () -> {
                inputHandler.getSelectPass(passes);
            });
        }
    }

    @Nested
    @DisplayName("락커 선택 테스트")
    class LockerSelectionTests {
        @Test
        @DisplayName("락커 선택 시 true 반환 테스트")
        void testLockerSelectionTrue() {
            // given
            String input = "Y";

            // when
            boolean result = inputHandler.getLockerSelection();

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("락커 미선택 시 false 반환 테스트")
        void testLockerSelectionFalse() {
            // given
            String input = "N";

            // when
            boolean result = inputHandler.getLockerSelection();

            // then
            assertFalse(result);
        }

        @Test
        @DisplayName("잘못된 락커 선택 입력 시 예외 발생 테스트")
        void testInvalidLockerSelection() {
            // given
            String input = "X";

            // when & then
            assertThrows(AppException.class, () -> {
                inputHandler.getLockerSelection();
            });
        }
    }
}