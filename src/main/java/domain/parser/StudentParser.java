package domain.parser;


import domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class StudentParser {
    private final Collection<Student> students = new ArrayList<>();
    /**
     * Converts all lines of a csv-formatted file to the students. Invalid lines are skipped
     *
     * @param path the path of the csv-file
     */
    public Collection<Student> readFromCsv(Path path) throws IOException {
        try(Stream<String> stringStream = Files.lines(path)) {
            stringStream
                    .skip(1)
                    .map(s -> {
                        try {
                            return Student.of(s);
                        } catch (IllegalArgumentException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .forEach(students::add);
        }
        return students;
    }
}
