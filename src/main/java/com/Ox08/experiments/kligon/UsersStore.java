package com.Ox08.experiments.kligon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This is 'in-memory' store, used as provider for users accounts.
 * 
 * @author <a href="mailto:alex3.145@gmail.com">Alex Chernyshev</a>
 */
@ApplicationScoped
public class UsersStore implements IdentityStore {
    @Inject
    private Logger log;
    /**
     * Validates provided credentials
     * @param credential
     *          provided credential
     * @see IdentityStore
     * @return
     *      validation result
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {
        // check for credential type to ignore all other types of authentication
        if (!(credential instanceof final UsernamePasswordCredential userCredential))
            return CredentialValidationResult.INVALID_RESULT;

        // extract caller (username)
        final String login = userCredential.getCaller();
        log.log(Level.INFO, "called validate for  {0} ", login);
        // check if user exist in store
        if (!USERS.containsKey(login))
            return CredentialValidationResult.INVALID_RESULT;

        // get User object by username
        final User user = USERS.get(login);
        // dumb password check
        if (!userCredential.compareTo(login, user.password))
            return CredentialValidationResult.INVALID_RESULT;

        // respond successful result
        log.log(Level.INFO, "user {0} validated", login);
        return new CredentialValidationResult(user.login,
                user.roles);
    }
    // This is very simple in-memory store for demo users credentials
    private static final Map<String, User> USERS = new TreeMap<>();
    static {
        USERS.put("admin@test.org",
                new User("admin@test.org", "admin", Set.of("ROLE_ADMIN")));
        USERS.put("user@test.org",
                new User("user@test.org", "user", Set.of("ROLE_USER")));
    }
    /**
     * For more fun I used new 'record' construct, instead of ordinary class.
     */
    private record User(String login, String password, Set<String> roles) {    }
    
}
