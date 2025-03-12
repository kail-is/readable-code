package cleancode.studycafe.tobe.io;

import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StudyCafeFileHandler {

    private static final String basePath = "src/main/resources/cleancode/studycafe/";
    private final StudyCafePassType studyCafePassType;

    public StudyCafeFileHandler(StudyCafePassType studyCafePassType) {
        this.studyCafePassType = studyCafePassType;
    }

    public List<StudyCafePass> getHourlyPasses() {

        List<StudyCafePass> studyCafePasses = readStudyCafePasses();
        return studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.getPassType() == studyCafePassType)
                .toList();
    }

    public StudyCafeLockerPass getLockerPass(StudyCafePass selectedPass) {
        List<StudyCafeLockerPass> lockerPasses = readLockerPasses();
        return lockerPasses.stream()
                .filter(option ->
                        option.getPassType() == selectedPass.getPassType()
                                && option.getDuration() == selectedPass.getDuration()
                )
                .findFirst()
                .orElse(null);
    }

    public <T> List<T> readCsvFile(String fileName, Function<String[], T> lineParser) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(basePath + fileName));
            List<T> result = new ArrayList<>();

            for (String line : lines) {
                String[] values = line.split(",");
                T parsedObject = lineParser.apply(values);
                result.add(parsedObject);
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는데 실패했습니다: " + fileName, e);
        }
    }

    // 구체적인 구현 메소드
    public List<StudyCafePass> readStudyCafePasses() {
        return readCsvFile("pass-list.csv", values -> {
            StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
            int duration = Integer.parseInt(values[1]);
            int price = Integer.parseInt(values[2]);
            double discountRate = Double.parseDouble(values[3]);

            return StudyCafePass.of(studyCafePassType, duration, price, discountRate);
        });
    }

    public List<StudyCafeLockerPass> readLockerPasses() {
        return readCsvFile("locker.csv", values -> {
            StudyCafePassType studyCafePassType = StudyCafePassType.valueOf(values[0]);
            int duration = Integer.parseInt(values[1]);
            int price = Integer.parseInt(values[2]);

            return StudyCafeLockerPass.of(studyCafePassType, duration, price);
        });
    }


}
