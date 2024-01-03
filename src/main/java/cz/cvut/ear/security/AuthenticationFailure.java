package cz.cvut.ear.security;
public class AuthenticationFailure {}
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import java.io.IOException;
//
//public class AuthenticationFailure implements AuthenticationFailureHandler {
//
//    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationFailure.class);
//
//    private final ObjectMapper mapper;
//
//    public AuthenticationFailure(ObjectMapper mapper) {
//        this.mapper = mapper;
//    }
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
//                                        AuthenticationException e) throws IOException {
//        LOG.debug("Login failed for user {}.", httpServletRequest.getParameter("username"));
//        final LoginStatus status = new LoginStatus(false, false, null, e.getMessage());
//        mapper.writeValue(httpServletResponse.getOutputStream(), status);
//    }
//}
