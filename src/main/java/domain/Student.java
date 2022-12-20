package domain;

import domain.parser.StudentParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

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
        String[] splitted = csv.split(",");

        if (splitted.length!=5) {
            throw new IllegalArgumentException();
        }
        String firstName = splitted[0];
        String secondName = splitted[1];
        Gender gender = null;
        if (Objects.equals(splitted[2], "M")) {
            gender = Gender.MALE;
        } else if (Objects.equals(splitted[2], "W")) {
            gender = Gender.FEMALE;
        } else if (Objects.equals(splitted[2], "D")) {
            gender = Gender.DIVERSE;
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

    public int getNumber() {
        return number;
    }

    public String getSchoolClass() {
        return schoolClass;
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
