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

    @Nested
    @DisplayName("패스 모델 테스트")
    class PassModelTests {
        @Test
        @DisplayName("시간제 패스 생성 및 가격 확인 테스트")
        void testHourlyPassCreation() {
            StudyCafePass hourlyPass = new StudyCafePass("1시간", 5000, StudyCafePassType.HOURLY);
            assertTrue(hourlyPass.getType().isHourly());
            assertEquals(5000, hourlyPass.getPrice());
            assertEquals("1시간", hourlyPass.getName());
        }

        @Test
        @DisplayName("주간 패스 생성 및 가격 확인 테스트")
        void testWeeklyPassCreation() {
            StudyCafePass weeklyPass = new StudyCafePass("1주", 50000, StudyCafePassType.WEEKLY);
            assertTrue(weeklyPass.getType().isWeekly());
            assertEquals(50000, weeklyPass.getPrice());
            assertEquals("1주", weeklyPass.getName());
        }

        @Test
        @DisplayName("고정석 패스 생성 및 가격 확인 테스트")
        void testFixedPassCreation() {
            StudyCafePass fixedPass = new StudyCafePass("고정석", 100000, StudyCafePassType.FIXED);
            assertTrue(fixedPass.getType().isFixed());
            assertEquals(100000, fixedPass.getPrice());
            assertEquals("고정석", fixedPass.getName());
        }
    }

    @Nested
    @DisplayName("락커 패스 테스트")
    class LockerPassTests {
        @Test
        @DisplayName("락커 패스 생성 및 가격 확인 테스트")
        void testLockerPassCreation() {
            StudyCafeLockerPass lockerPass = new StudyCafeLockerPass("A-1", 20000);
            assertEquals("A-1", lockerPass.getLockerNumber());
            assertEquals(20000, lockerPass.getPrice());
        }

        @Test
        @DisplayName("고정석 패스에 락커 패스 연결 테스트")
        void testFixedPassWithLocker() {
            StudyCafePass fixedPass = new StudyCafePass("고정석", 100000, StudyCafePassType.FIXED);
            StudyCafeLockerPass lockerPass = new StudyCafeLockerPass("A-1", 20000);
            
            fixedPass.setLockerPass(lockerPass);
            
            assertNotNull(fixedPass.getLockerPass());
            assertEquals(lockerPass, fixedPass.getLockerPass());
            assertEquals(120000, fixedPass.getTotalPrice());
        }

        @Test
        @DisplayName("고정석 패스에 락커 패스 없을 때 테스트")
        void testFixedPassWithoutLocker() {
            StudyCafePass fixedPass = new StudyCafePass("고정석", 100000, StudyCafePassType.FIXED);
            
            assertNull(fixedPass.getLockerPass());
            assertEquals(100000, fixedPass.getTotalPrice());
        }
    }
} 