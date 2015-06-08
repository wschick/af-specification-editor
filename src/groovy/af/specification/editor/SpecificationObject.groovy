package af.specification.editor
/**
 * Created by will.schick on 6/2/15.
 */
abstract class SpecificationObject {
    
    Date dateCreated
    Date lastUpdated
    
    static def validateImmutablityFor(String field) {
        
        return { value,SpecificationObject object,errors ->
            //println("Validating immutability for ${field} on ${object}")
            
            def c = object.class
            
            if (object.isPublished()){
                
                def oldValue = null
                def oldValueExists = false;
                
                c.withNewSession { session ->
                    def old = c.get(object.id)
                    if (old) {
                        oldValueExists = true;
                        oldValue = old[field];
                    }
                }
                
                if (!oldValueExists)
                    return true;
                
                if(oldValue != value)
                    errors.rejectValue(field,'af.specification.immutablity',field)
            }
        }
    }
    
    abstract boolean isPublished()
    
    def beforeDelete = {
        if (isPublished())
            return false;
    }
    
    @Override
    boolean equals(Object o){
        throw new Exception();
        
    }
}
