package af.specification.editor

class MulticastGroup extends SpecificationObject {

   
    Integer multicastGroupId;
    String name;
    String description;
    
    
    
    static constraints = {
        multicastGroupId unique: true
        description nullable: true
        name blank: false
    }
    
    static mapping = {
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
