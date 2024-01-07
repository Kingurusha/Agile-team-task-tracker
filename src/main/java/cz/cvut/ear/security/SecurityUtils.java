package cz.cvut.ear.security;

import cz.cvut.ear.model.Employee;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Employee getCurrentEmployee() {
        final EmployeeDetails ed = getCurrentEmployeeDetails();
        return ed != null ? ed.getEmployee() : null;
    }

    public static EmployeeDetails getCurrentEmployeeDetails() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() != null && context.getAuthentication().getPrincipal() instanceof EmployeeDetails) {
            return (EmployeeDetails) context.getAuthentication().getPrincipal();
        } else {
            return null;
        }
    }
}
