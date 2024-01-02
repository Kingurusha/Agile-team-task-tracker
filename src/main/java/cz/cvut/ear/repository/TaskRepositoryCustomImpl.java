package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepositoryCustomImpl implements TaskRepositoryCustom {
    private EntityManager em;


    @Override
    public List<Task> getAllEmployeeTasks(Long employeeId) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);

        Root<Task> root = criteriaQuery.from(Task.class);
        Join<Task, Employee> assigneeJoin = root.join("assignee");

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(assigneeJoin.get("id"), employeeId));

        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<Task> getAllEmployeeTasksByUsername(String username) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);

        Root<Task> root = criteriaQuery.from(Task.class);
        Join<Task, Employee> assigneeJoin = root.join("assignee");

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(assigneeJoin.get("username"), username));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
