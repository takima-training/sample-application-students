package fr.takima.training.sampleapplication.unit;

import fr.takima.training.sampleapplication.dao.StudentDAO;
import fr.takima.training.sampleapplication.entity.Department;
import fr.takima.training.sampleapplication.entity.Student;
import fr.takima.training.sampleapplication.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentsServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentDAO studentDAO;

    private final Department department = Department.builder().id(1L).name("DepartementTest").build();
    private final Student student = Student
            .builder()
            .id(1L)
            .firstname("Firstname")
            .lastname("lastname")
            .department(department)
            .build();

    @Test
    void testGetStudentById() {
        when(studentDAO.getById(1L)).thenReturn(student);
        assertThat(studentService.getStudentById(1L)).isEqualTo(student);
    }

    @Test
    void testGetStudentByIdWithNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentById(-5));
    }

    @Test
    void testGetStudentsByDepartmentName() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentDAO.findStudentsByDepartment_Name("DepartmentTest")).thenReturn(students);

        assertThat(studentService.getStudentsByDepartmentName("DepartmentTest")).containsExactly(student);
    }

    @Test
    void testGetStudentsByDepartmentNameWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByDepartmentName(null));
    }

    @Test
    void testGetStudentsByDepartmentNameWithEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsByDepartmentName(""));
    }

    @Test
    void testGetStudentsNumberByDepartmentName() {
        when(studentDAO.countAllByDepartment_Name("DepartmentTest")).thenReturn(1);
        assertThat(studentService.getStudentsNumberByDepartmentName("DepartmentTest")).isEqualTo(1);
    }

    @Test
    void testGetStudentsNumberByDepartmentNameWithNullValue() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsNumberByDepartmentName(null));
    }

    @Test
    void testGetStudentsNumberByDepartmentNameWithEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> studentService.getStudentsNumberByDepartmentName(null));
    }

    @Test
    void testAddStudent() {
        when(studentDAO.save(student)).thenReturn(student);
        assertThat(studentService.addStudent(student)).isEqualTo(student);
    }

    @Test
    void testAddStudentWithBadLastname() {
        Student studentWithNullLastname = Student.builder().id(1L).firstname("abc").department(department).build();
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(studentWithNullLastname));

        Student studentWithEmptyLastname = Student.builder().id(1L).firstname("abc").lastname("").department(department).build();
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(studentWithEmptyLastname));
    }

    @Test
    void testAddStudentWithoutDepartment() {
        Student studentWithoutDepartment = Student.builder().id(1L).lastname("abc").build();
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(studentWithoutDepartment));
    }

    @Test
    void testRemoveStudentById() {
        assertDoesNotThrow(() -> studentService.removeStudentById(1L));
    }

    @Test
    void testRemoveStudentWithNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> studentService.removeStudentById(-5));
    }

}
