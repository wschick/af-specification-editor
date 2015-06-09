package af.specification.editor

class MulticastEnvironmentType extends SpecificationObject {

    String name;
    String description;
    static constraints = {
        name unique: true
        description unique: true
    }

    @Override
    boolean isPublished(){
        return false;
    }
    
    @Override
    String toString(){
        return name;
    }
}
