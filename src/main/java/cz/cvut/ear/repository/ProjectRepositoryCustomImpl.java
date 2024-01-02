package cz.cvut.ear.repository;

import cz.cvut.ear.model.Employee;
import cz.cvut.ear.model.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class ProjectRepositoryCustomImpl implements ProjectRepositoryCustom {
    private EntityManager em;


    @Override
    public List<Project> getAllEmployeeProjects(Long employeeId) {
        Metamodel metamodel = em.getMetamodel();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        EntityType<Employee> Employee_ = metamodel.entity(Employee.class);
        EntityType<Project> Project_ = metamodel.entity(Project.class);

        Root<Employee> r = criteriaQuery.from(Employee.class);
        criteriaQuery.select(r.get(Employee_.getSingularAttribute("userProjects", Set.class)).get(String.valueOf(Project_.getSingularAttribute("project", Project.class))));
        criteriaQuery.where(criteriaBuilder.equal(r.get(Employee_.getSingularAttribute("id", Long.class)), employeeId));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
