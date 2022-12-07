package persistence;

import domain.Gender;
import domain.Student;

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
        return Optional.empty();
    }

    @Override
    public Stream<Student> findAll() throws SQLException {
        return null;
    }

    @Override
    public SortedSet<Student> findStudentsByClass(String schoolClass) throws SQLException {
        return null;
    }

    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        return null;
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
