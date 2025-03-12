package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();
            outputHandler.askPassTypeSelection();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler(studyCafePassType);
            List<StudyCafePass> hourlyPasses = studyCafeFileHandler.getHourlyPasses();

            outputHandler.showPassListForSelection(hourlyPasses);
            StudyCafePass selectedPass = inputHandler.getSelectPass(hourlyPasses);

            if (studyCafePassType == StudyCafePassType.HOURLY || studyCafePassType == StudyCafePassType.WEEKLY) {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

            if (studyCafePassType == StudyCafePassType.FIXED) {
                List<StudyCafeLockerPass> lockerPasses = studyCafeFileHandler.readLockerPasses();
                StudyCafeLockerPass lockerPass = lockerPasses.stream()
                        .filter(option ->
                                option.getPassType() == selectedPass.getPassType()
                                        && option.getDuration() == selectedPass.getDuration()
                        )
                        .findFirst()
                        .orElse(null);

                if(lockerPass == null) {
                    outputHandler.showPassOrderSummary(selectedPass, null);
                    return;
                }
                outputHandler.askLockerPass(lockerPass);

                boolean lockerSelection = inputHandler.getLockerSelection();
                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass, lockerPass);
                }
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

}
