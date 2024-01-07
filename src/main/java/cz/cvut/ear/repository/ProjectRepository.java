package cz.cvut.ear.repository;

import cz.cvut.ear.model.Project;
import cz.cvut.ear.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends BaseRepository<Project, Long>, ProjectRepositoryCustom {
    Optional<Project> findByProjectName(String projectName);

    @Query(name = "Employee.findProjectsByIdAndStatus")
    List<Project> findProjectsByEmployeeIdAndStatus(@Param("employeeId") Long employeeId,
                                                    @Param("projectStatus") ProjectStatus projectStatus
    );

    @Query("SELECT p FROM Project p WHERE p.startDate <= :date AND p.endDate >= :date")
    List<Project> findActiveProjectsByDate(@Param("date") LocalDate date);
}