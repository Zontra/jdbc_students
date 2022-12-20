package persistence;

import domain.Gender;
import domain.Student;
import org.h2.bnf.RuleOptional;

import java.sql.*;
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
        ResultSet rs = statement.executeQuery("SELECT * FROM students WHERE student_number = " + number + " AND class = '" + schoolClass + "'");
        while (rs.next()) {
            String lastname = rs.getString("last_name");
            String firstname = rs.getString("first_name");
            Gender gender = Gender.valueOf(rs.getString("gender"));
            return Optional.of(new Student(lastname, firstname, gender, number, schoolClass));
        }
        return Optional.empty();
    }

    @Override
    public Stream<Student> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM students");
        List<Student> students = new ArrayList<>();
        while (rs.next()) {
            String lastname = rs.getString("last_name");
            String firstname = rs.getString("first_name");
            Gender gender = Gender.valueOf(rs.getString("gender"));
            int number = rs.getInt("student_number");
            String schoolClass = rs.getString("class");
            students.add(new Student(lastname, firstname, gender, number, schoolClass));
        }
    return students.stream();
    }

    @Override
    public SortedSet<Student> findStudentsByClass(String schoolClass) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM students WHERE class = '" + schoolClass + "'");
        SortedSet<Student> students = new TreeSet<>();
        while (rs.next()) {
            String lastname = rs.getString("last_name");
            String firstname = rs.getString("first_name");
            Gender gender = Gender.valueOf(rs.getString("gender"));
            int number = rs.getInt("student_number");
            students.add(new Student(lastname, firstname, gender, number, schoolClass));
        }
        return students;
    }

    @Override
    public Set<Student> findStudentsByGender(Gender gender) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM students WHERE gender = '" + gender + "'");
        Set<Student> students = new HashSet<>();
        while (rs.next()) {
            String lastname = rs.getString("last_name");
            String firstname = rs.getString("first_name");
            int number = rs.getInt("student_number");
            String schoolClass = rs.getString("class");
            students.add(new Student(lastname, firstname, gender, number, schoolClass));
        }
        return students;
    }

    @Override
    public Map<String, Integer> findClasses() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT class, COUNT(*) FROM students GROUP BY class ORDER BY class");
        Map<String, Integer> classes = new HashMap<>();
        while (rs.next()) {
            String schoolClass = rs.getString("class");
            int count = rs.getInt("COUNT(*)");
            classes.put(schoolClass, count);
        }
        return classes;
    }

    @Override
    public Student save(Student student) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into students (last_name, first_name, gender, student_number, class) values (?, ?, ?, ?, ?)");

            ps.setString(1, student.getLastName());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getGender().toString());
            ps.setInt(4, student.getStudentNumber());
            ps.setString(5, student.getSchoolClass());
            ps.executeUpdate();

        return student;
    }
}
