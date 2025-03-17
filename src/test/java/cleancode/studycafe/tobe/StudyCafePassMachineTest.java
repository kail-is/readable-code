package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudyCafePassMachineTest {

    private StudyCafePassMachine studyCafePassMachine;

    @BeforeEach
    void setUp() {
        studyCafePassMachine = new StudyCafePassMachine();
    }

    @Test
    @DisplayName("스터디카페 패스 머신이 정상적으로 생성되는지 테스트")
    void testStudyCafePassMachineCreation() {
        assertNotNull(studyCafePassMachine);
    }

    @Test
    @DisplayName("패스 타입이 올바르게 정의되어 있는지 테스트")
    void testPassTypeDefinitions() {
        assertTrue(StudyCafePassType.HOURLY.isHourly());
        assertTrue(StudyCafePassType.WEEKLY.isWeekly());
        assertTrue(StudyCafePassType.FIXED.isFixed());
    }

    @Test
    @DisplayName("패스 타입 간의 구분이 명확한지 테스트")
    void testPassTypeDistinctions() {
        assertFalse(StudyCafePassType.HOURLY.isWeekly());
        assertFalse(StudyCafePassType.WEEKLY.isFixed());
        assertFalse(StudyCafePassType.FIXED.isHourly());
    }

    @Test
    @DisplayName("시간제 패스 주문 프로세스 테스트")
    void testHourlyPassOrderProcess() {
        studyCafePassMachine.run();
    }

    @Test
    @DisplayName("주간 패스 주문 프로세스 테스트")
    void testWeeklyPassOrderProcess() {
        studyCafePassMachine.run();
    }

    @Test
    @DisplayName("고정석 패스 주문 프로세스 테스트")
    void testFixedPassOrderProcess() {
    }
} 