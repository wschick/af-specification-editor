package af.specification.editor

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

/**
 * Created by will.schick on 6/8/15.
 */
class ImmutabilityConstraint extends AbstractConstraint{
    
    static final String CONSTRAINT_NAME = "immutable";
    @Override
    protected void processValidate(Object target, Object value, Errors errors) {

        //println("Validating immutability for ${field} on ${object}")

        println "Processing constraint : ${target} ${value} ${constraintPropertyName} ${constraintParameter}"
        
        
        def object = target;
        def DomainClass = object.class

        if (object.isPublished()){

            def oldValue = null
            def oldValueExists = false;

            DomainClass.withNewSession { session ->
                def old = DomainClass.get(object.id)
                
                if (old) {

                    oldValueExists = true;
                    
                    if (old[constraintPropertyName] instanceof Collection){
                        println "This is a collection"
                        oldValue = []
                        oldValue.addAll(old[constraintPropertyName]);

                    }else {
                        
                        oldValue = old[constraintPropertyName];
                    }
                }
            }

            if (!oldValueExists)
                return;

            if (SpecificationObject.isAssignableFrom(value.class)){
                println("This is a domain value")
                
            }
            if(oldValue != value) {
                println("Immutability violation for ${target} ${constraintPropertyName}")
                errors.rejectValue(constraintPropertyName, 'af.specification.immutablity', constraintPropertyName)
            }
        }

        
    }

    @Override
    boolean supports(Class type) {
        return true
    }

    @Override
    String getName() {
        return CONSTRAINT_NAME;
    }
}
