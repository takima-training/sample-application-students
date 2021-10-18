package fr.takima.training.sampleapplication.unit;

import fr.takima.training.sampleapplication.dao.DepartmentDAO;
import fr.takima.training.sampleapplication.entity.Department;
import fr.takima.training.sampleapplication.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentsServiceTest {

    @Mock
    private DepartmentDAO departmentDAO;

    @InjectMocks
    private DepartmentService departmentService;

    private final Department department = Department.builder().id(1L).name("DepartementTest").build();

    @Test
    void testGetDepartmentByName() {
        when(departmentDAO.getDepartmentByName("DepartmentTest")).thenReturn(department);
        assertThat(departmentService.getByName("DepartmentTest")).isEqualTo(department);
    }

}
