package af.specification.editor

class DatumScale extends SpecificationObject {

    String name;
    String description;
    String display;

    static constraints = {
        name unique: true, immutable:true
        description nullable: true
        display nullable: true
    
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
