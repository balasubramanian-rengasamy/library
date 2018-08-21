package br.eti.arthurgregorio.library.domain.services.validators.user;

import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.services.validators.BusinessValidator;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import br.eti.arthurgregorio.library.infrastructure.cdi.qualifier.AuthenticatedUser;

/**
 * {@link BusinessValidator} to validte if you are deleting the admin
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/08/2018
 */
@Dependent
public class DeleteAdminUserValidator implements UserDeletingValidator {

    @Inject
    @AuthenticatedUser
    private User principal;
    
    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void validate(User value) {
        
        final String principalUsername = this.principal.getUsername();
        
        // prevent to delete you own user 
        if (principalUsername.equals(value.getUsername())) {
            throw new BusinessLogicException("error.user.delete-principal");
        }
        
        // prevent to delete the main admin
        if (value.isAdministrator()) {
            throw new BusinessLogicException("error.user.delete-administrator");
        }
    }
}
