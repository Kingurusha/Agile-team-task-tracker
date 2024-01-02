package cz.cvut.ear.security;

import cz.cvut.ear.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class EmployeeDetails implements UserDetails {
    private final Employee employee;
    private final Set<GrantedAuthority> authorities;

    public EmployeeDetails(Employee employee) {
        Objects.requireNonNull(employee);
        this.employee = employee;
        this.authorities = new HashSet<>();
        addEmployeeRole();
    }

    public EmployeeDetails(Employee employee, Set<GrantedAuthority> authorities) {
        Objects.requireNonNull(employee);
        Objects.requireNonNull(authorities);
        this.employee = employee;
        this.authorities = new HashSet<>();
        addEmployeeRole();
        this.authorities.addAll(authorities);
    }

    private void addEmployeeRole() {
        authorities.add(new SimpleGrantedAuthority(employee.getRole().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Employee getEmployee() {
        return employee;
    }
}
