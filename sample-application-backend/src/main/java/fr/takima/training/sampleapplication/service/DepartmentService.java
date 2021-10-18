package fr.takima.training.sampleapplication.service;

import fr.takima.training.sampleapplication.dao.DepartmentDAO;
import fr.takima.training.sampleapplication.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentService(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public Department getByName(String departmentName) {
        return this.departmentDAO.getDepartmentByName(departmentName);
    }

    public List<Department> getAll() {
        return this.departmentDAO.findAll();
    }

}
