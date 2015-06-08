package af.specification.editor

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

/**
 * Created by will.schick on 6/8/15.
 */
class RequiredForPublicationConstraint extends AbstractConstraint {
    @Override
    protected void processValidate(Object target, Object propertyValue, Errors errors) {

    }

    @Override
    boolean supports(Class type) {
        return false
    }

    @Override
    String getName() {
        return null
    }
}
