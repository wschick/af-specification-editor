package af.specification.editor

class MenuItem extends SpecificationObject{

    String displayText
    MenuItem parentMenuItem
    
    static constraints = {
    }

    @Override
    boolean isPublished(){
        return false;
    }
}
