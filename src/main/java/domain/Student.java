package domain;

import domain.parser.StudentParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Student {

    private Integer id;
    private final String lastName;
    private final String firstName;
    private final Gender gender;
    private final int number;
    private final String schoolClass;

    public Student(String lastName, String firstName, Gender gender, int number, String schoolClass) {
        if (lastName.isBlank())
            throw new IllegalArgumentException("Last name cannot be blank");
        if (firstName.isBlank())
            throw new IllegalArgumentException("First name cannot be blank");
        if (number < 1 || number > 36)
            throw new IllegalArgumentException("Number out of range, must be of [1,36]");
        if (schoolClass.isBlank())
            throw new IllegalArgumentException("Student must be part of a class");
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.number = number;
        this.schoolClass = schoolClass;
    }

    public Student(Integer id, String lastName, String firstName, Gender gender, int number, String schoolClass) {
        this(lastName, firstName, gender, number, schoolClass);
        this.id = id;
    }

    public static Student of(String csv) {
        if (csv.charAt(csv.length() - 1) == ',') {
            throw new IllegalArgumentException("; at the end of the line");
        }

        String[] splitted = csv.split(",");

        if (splitted.length != 5) {
            throw new IllegalArgumentException();
        }
        String firstName = splitted[1];
        String secondName = splitted[0];
        Gender gender = null;

        String[] parts = splitted[4].split("(?<=\\d)(?=\\D)");
        int tmp = Integer.parseInt(parts[0]);

        if (tmp < 1 || tmp > 5) {
            throw new IllegalArgumentException("Number out of range, must be of [1,5]");
        }

        if (!splitted[4].contains("HIF")) {
            throw new IllegalArgumentException("Invalid class");
        }

        switch (splitted[2]) {
            case "m"-> gender = Gender.MALE;
            case "w" -> gender = Gender.FEMALE;
            case "d" -> gender = Gender.DIVERSE;
            default -> throw new IllegalArgumentException("Wrong Gender");
        }



        int number = Integer.parseInt(splitted[3]);
        String schoolClass = splitted[4];
        return new Student(secondName, firstName, gender, number, schoolClass);
    }

    public String getLastName() {
        return lastName;
    }


    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Gender getGender() {
        return gender;
    }

    public int getStudentNumber() {
        return number;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Student{" +
               "id=" + id +
               ", lastName='" + lastName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", gender=" + gender +
               ", number=" + number +
               ", schoolClass='" + schoolClass + '\'' +
               '}';
    }

    public static void main(String[] args) throws IOException {
        StudentParser studentParser = new StudentParser();
        studentParser.readFromCsv(Path.of("src/test/resources/students.csv")).forEach(System.out::println);
    }


}
