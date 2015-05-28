package af.specification.editor

class DatumScale {
    
    String code;

    static constraints = {
        code unique: true
    
    }
    
    @Override
    String toString(){
        return code;
    }
}
