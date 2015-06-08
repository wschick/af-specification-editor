import af.specification.editor.ImmutabilityConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

// Place your Spring DSL code here
beans = {
    ConstrainedProperty.registerNewConstraint(ImmutabilityConstraint.CONSTRAINT_NAME, ImmutabilityConstraint.class);
}
