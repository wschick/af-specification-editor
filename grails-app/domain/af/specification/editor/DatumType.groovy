package af.specification.editor

class DatumType extends SpecificationObject{

    String name;
    String description;
    String display;
    

    static constraints = {
        name unique: true,blank: false, immutable:true
        description()
        display()
        
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
