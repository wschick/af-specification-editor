package af.specification.editor

class MessageType extends SpecificationObject {

    String name;
    String description;
    Integer value;
    
    static constraints = {
        name unique: true
        value unique: true
    }
    
    static mapping = {
    }
    
    @Override
    String toString(){
        return name;
    }

    @Override
    boolean isPublished(){
        return false;
    }
}
