package af.specification.editor

class Country {
    
    String code;

    static constraints = {
        code unique: true
    }
    
    @Override
    String toString(){
        return code;
        
    }
}
