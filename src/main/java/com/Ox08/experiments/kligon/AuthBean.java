package com.Ox08.experiments.kligon;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * 
 * 
 * Here we do authentication.
 * CDI managed bean.
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@Named("auth")
@RequestScoped
public class AuthBean {
    @Inject
    private Principal principal;
    @Inject
    private Logger log; // logger is injected as dependency
    @Inject
    private SecurityContext securityContext; // a JAAS context
    @Inject
    private FacesContext facesContext; // JSF context
    // a DTO instance, used to store login & password
    private AuthDTO form = new AuthDTO();
    public Principal getPrincipal() {
        return principal;
    }
    public AuthDTO getForm() {
        return form;
    }
    public void setForm(AuthDTO form) {
        this.form = form;
    }
    /**
     * does authentication
     *
     * @throws IOException
     *              if error occurs
     */
    public void login() throws IOException {
        // we need to re-use 2 existing fields,
        // present in this class: 'author for username and 'title' for password
        final Credential credential = new UsernamePasswordCredential(
                form.getLogin(), new Password(form.getPassword()));
        final ExternalContext ec = facesContext.getExternalContext();
        final AuthenticationStatus status;
        if (ec.getRequest() instanceof HttpServletRequest req &&
                ec.getResponse() instanceof HttpServletResponse res) {
            req.getSession(true);
            status = securityContext
                    .authenticate(
                            req, res,
                            AuthenticationParameters.withParams()
                                    .newAuthentication(true)
                                    .rememberMe(true)
                                    .credential(credential));
        } else
            throw new IllegalStateException("Unknown servlet request|response type!");


        log.log(Level.INFO, "_status: {0}", status);
        switch (status) {
            case SEND_CONTINUE ->
                facesContext.responseComplete();

            case SEND_FAILURE ->  {
                ec.getRequestMap().put("login", "true");
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Login failed", null));
            }
            case SUCCESS ->
                ec.redirect(ec.getRequestContextPath() + "/guestbook.xhtml");

            case NOT_DONE -> {
                // ignore
            }
        }
    }
    /**
     * Does logout action from JSF page
     *
     * @return
     *          page to redirect after logout
     * @throws ServletException
     *          if error occurs
     */
    public String logout() throws ServletException {
        final ExternalContext ec = facesContext.getExternalContext();
        final HttpServletRequest req = (HttpServletRequest) ec.getRequest();
        req.logout();
        ec.invalidateSession();
        return "/guestbook.xhtml?faces-redirect=true";
    }
    /**
     * DTO class, used to transfer login and password to backing bean
     * 'record' type cannot be used here (see upper)
     */
    public static class AuthDTO {
        @Size(min = 3, max = 255)
        @Email
        private String login;
        @Size(min = 3, max = 255)
        private String password;
        public String getLogin() {
            return login;
        }
        public void setLogin(String login) {
            this.login = login;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
