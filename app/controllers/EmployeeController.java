package controllers;

import models.Employee;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class EmployeeController extends Controller
{
    private JPAApi jpaApi;

    @Inject
    public EmployeeController(JPAApi jpaApi)
    {
        this.jpaApi = jpaApi;
    }

    @Transactional(readOnly = true)
    public Result getEmployees()
    {
        List<Employee> employees =
                jpaApi.em().createQuery("SELECT e FROM Employee e ORDER BY lastName, firstName", Employee.class).getResultList();

        return ok(views.html.employees.render(employees));
    }

    @Transactional(readOnly = true)
    public Result getEmployee(Integer id)
    {
        Employee employee =
                jpaApi.em().createQuery("SELECT e FROM Employee e WHERE employeeId = :id", Employee.class).setParameter("id", id).getSingleResult();
        return ok(views.html.employee.render(employee));
    }
}
