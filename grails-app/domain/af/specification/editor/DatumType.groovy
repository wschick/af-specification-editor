package af.specification.editor

class DatumType {
    
    String code;

    static constraints = {
        code unique: true
    }

    @Override
    String toString(){
        return code;
    }
}
