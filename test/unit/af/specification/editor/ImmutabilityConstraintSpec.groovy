package af.specification.editor

import org.codehaus.groovy.grails.plugins.testing.GrailsMockErrors
import org.hibernate.Session
import org.springframework.validation.Errors
import spock.lang.Shared

/**
 * Created by will.schick on 6/8/15.
 */
class ImmutabilityConstraintSpec extends spock.lang.Specification {
    
    @Shared
    ImmutabilityConstraint constraint
    
    @Shared
    Errors errors
    
    @Shared
    TestObject testObject
    
    class TestObject {
        
        
        static def persistence = [:]
        
        long id;
        int intField;
        String stringField;
        Datum datumField;
        
        static TestObject get(Object id){
            return persistence[id];
        }
        
        static def withNewSession(Closure c){
            c(null);
        }
        
        boolean published;
    }
    
    
    void setup(){
        testObject = new TestObject(id:123);
        constraint = new ImmutabilityConstraint();
        errors = new GrailsMockErrors(testObject);
    }
    
    void "it should do nothing if the object is not published"(){
        given:
        "an upublished object"
        testObject.published = false
        
        and:
        "the constraint applied"
        constraint.propertyName = "intField";
        
        when:
        "constraint is checked"
        constraint.processValidate(testObject,"123",errors);
        
        then:
        "there should be no errors"
        errors.allErrors.size() == 0
        
    }
    
    void "it should check primative values if there is a shared object and set an error if they are different"(){
        given:
        "an published object"
        testObject.published = true

        and:
        "the constraint applied"
        constraint.propertyName = "intField";
        
        and:
        "a saved item"
        TestObject.persistence[123l] = new TestObject(intField: 234);

        when:
        "constraint is checked"
        constraint.processValidate(testObject,123,errors);

        then:
        "there should be  errors"
        errors.allErrors.size() == 1
        
    }

    void "it should check domain object values by ID if there is a shared object and set an error if they are different"(){
        given:
        "an published object"
        testObject.published = true

        and:
        "the constraint applied"
        constraint.propertyName = "datumField";

        and:
        "a saved item with a datum"
        TestObject.persistence[123l] = new TestObject(datumField: new Datum(id:99));

        when:
        "constraint is checked with a new datum (by id)"
        constraint.processValidate(testObject,new Datum(id: 88),errors);

        then:
        "there should be  errors"
        errors.allErrors.size() == 1

    }

    void "it should check domain object values by ID if there is a shared object and set no error if they are the same"(){
        given:
        "an published object"
        testObject.published = true

        and:
        "the constraint applied"
        constraint.propertyName = "datumField";

        and:
        "a saved item"
        TestObject.persistence[123l] = new TestObject(datumField: new Datum(id:99));

        when:
        "constraint is checked with a new datum with the same id"
        constraint.processValidate(testObject,new Datum(id: 99),errors);

        then:
        "there should be no errors"
        errors.allErrors.size() == 0

    }
    
    
    
    
}
