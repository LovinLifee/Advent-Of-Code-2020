package net.avuna.aoc.days;

import lombok.ToString;
import net.avuna.aoc.Challenge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day4 extends Challenge<Long> {

    public Day4() {
        super(4);
    }

    @Override
    public Long doPartOne() {
        return parsePassports().stream().filter(Passport::isValid).count();
    }

    @Override
    public Long doPartTwo() {
        return parsePassports().stream().filter(Passport::isValid2).count();
    }

    private static final Map<String, String> FIELDS = Map.of(
            "byr", "birthYear",
            "iyr", "issueYear",
            "eyr", "expirationYear",
            "hgt", "height",
            "hcl", "hairColour",
            "ecl", "eyeColour",
            "pid", "passportId",
            "cid", "countryId");

    /*
        Reflection is totally unnecessary, also parsing of the passports can be done way better.
        There's currently a bug where it doesn't read the last passport since there is no terminating '\n' at the end of the file
        To circumvent this I've just pushed the last passport after the loop has finished.
     */
    private List<Passport> parsePassports() {
        List<Passport> passports = new ArrayList<>();
        Passport passport = new Passport();
        List<String> lines = readLines().collect(Collectors.toList());
        for(int i = 0; i < lines.size(); i++) {
            String s = lines.get(i);
            if(s.isBlank()) {
                passports.add(passport);
                passport = new Passport();
            } else {
                String[] line = s.split(" ");
                for(String l : line) {
                    String[] data = l.split(":");
                    String key = data[0];
                    String value = data[1];
                    try {
                        Field field = Passport.class.getField(FIELDS.get(key));
                        field.set(passport, value);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        passports.add(passport);
        return passports;
    }

    @ToString
    private final class Passport {
        public String birthYear = null;
        public String issueYear = null;
        public String expirationYear = null;
        public String height = null;
        public String hairColour = null;
        public String eyeColour = null;
        public String passportId = null;
        public String countryId = null;

        private boolean isValid() {
            return birthYear != null && issueYear != null && expirationYear != null && height != null && hairColour != null && eyeColour != null && passportId != null;
        }

        private boolean isValidBirthYear() {
            int birthYear = Integer.parseInt(this.birthYear);
            return birthYear >= 1920 && birthYear <= 2002;
        }

        private boolean isValidExprYear() {
            int expirationYear = Integer.parseInt(this.expirationYear);
            return expirationYear >= 2020 && expirationYear <= 2030;
        }

        private boolean isValidIssueYear() {
            int issueYear = Integer.parseInt(this.issueYear);
            return issueYear >= 2010 && issueYear <= 2020;
        }

        private boolean isValidHeight() {
            if(height.length() <= 2) {
                return false;
            }
            String unit = this.height.substring(this.height.length() - 2);
            int height = Integer.parseInt(this.height.substring(0, this.height.length() - 2));
            if(unit.equals("cm") && height >= 150 && height <= 193) {
                return true;
            } else if(unit.equalsIgnoreCase("in") && height >= 59 && height <= 76) {
                return true;
            }
            return false;
        }

        private boolean isValidHairColour() {
            return hairColour.matches("#[0-9a-f]{6}");
        }

        private boolean isValidEyeColour() {
            return eyeColour.matches("(amb|blu|brn|gry|grn|hzl|oth)");
        }

        private boolean isValidPid() {
            return passportId.matches("\\d{9}");
        }

        private boolean isValid2() {
            return isValid() && isValidBirthYear() && isValidIssueYear() && isValidPid()
                    && isValidExprYear() && isValidEyeColour()
                    && isValidHairColour() && isValidHeight();
        }
    }
}
