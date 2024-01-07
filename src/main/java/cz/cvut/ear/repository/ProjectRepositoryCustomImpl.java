package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Project> getAllEmployeeProjects(Long employeeId) {
        Metamodel metamodel = em.getMetamodel();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        EntityType<Employee> employeeType = metamodel.entity(Employee.class);
        //EntityType<Project> projectType = metamodel.entity(Project.class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Join<Employee, Project> projectsJoin = employeeRoot.join(employeeType.getSet("userProjects", Project.class));

        criteriaQuery.select(projectsJoin)
                .where(criteriaBuilder.equal(employeeRoot.get(employeeType.getSingularAttribute("id", Long.class)), employeeId));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
