package af.specification.editor

class MulticastGrouping extends SpecificationObject {

    String name;
    String description;
    static hasMany = ['multicastGroups':MulticastGroup]
    
    static constraints = {
    }
    
    
    
    @Override
    boolean isPublished(){
        return false;
    }
}
