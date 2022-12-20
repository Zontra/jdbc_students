package persistence;

import domain.Gender;
import domain.Student;
import org.h2.bnf.RuleOptional;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Stream;

public record JdbcStudentRepository(Connection connection) implements StudentRepository {

    @Override
    public void deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("TRUNCATE TABLE if exists student");
    }

    @Override
    public Optional<Student> findByNumberAndClass(int number, String schoolClass) throws SQLException {
        Statement statement = connection.createStatement();
        return Optional.of(Student.of(String.valueOf(statement.executeQuery("SELECT * FROM student WHERE student_number = " + number + " AND class = " + schoolClass))));
    }

    @Override
    public Stream<Student> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        return Stream.of(Student.of(String.valueOf(statement.executeQuery("SELECT * FROM student"))));
    }

    @Override
    public SortedSet<Student> findStudentsByClass(String schoolClass) throws SQLException {
        Statement statement = connection.createStatement();
        return new TreeSet<>(Arrays.asList(Student.of(String.valueOf(statement.executeQuery("SELECT * FROM student WHERE class = " + schoolClass)))));
    }

    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        Statement statement = connection.createStatement();
        return new TreeSet<>(Arrays.asList(Student.of(String.valueOf(statement.executeQuery("SELECT * FROM student WHERE gender = " + gender)))));
    }

    @Override
    public Map<String, Integer> findClasses() throws SQLException {
        return null;
    }

    @Override
    public Student save(Student student) throws SQLException {
        return null;
    }
}
