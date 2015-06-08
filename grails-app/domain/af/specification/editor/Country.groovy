package af.specification.editor

class Country extends SpecificationObject {
    
    String code;

    static constraints = {
        code unique: true,immutable:true
    }
    
    @Override
    String toString(){
        return code;
        
    }

    @Override
    boolean isPublished() {
        return false
    }
}
