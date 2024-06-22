package com.Ox08.experiments.kligon;
import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

import java.util.logging.Logger;
/**
 * Configuration class, used to register customized authentication provider.
 *
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@FacesConfig
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/guestbook.xhtml?login=true",
                errorPage = "/guestbook.xhtml?login=true")
)
@DeclareRoles({"ROLE_ADMIN", "ROLE_USER"})
@ApplicationScoped
public class BookConfig {
    /**
     * A producer for logger.
     * Used to inject Logger instance directly into CDI managed beans
     *
     * @param p
     *          injection target
     * @see InjectionPoint
     * @return
     *      A logger instance
     */
    @Produces
    public Logger getLogger(InjectionPoint p) {
        return Logger.getLogger(p.getClass().getCanonicalName());
    }
}
