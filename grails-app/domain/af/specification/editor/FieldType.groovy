package af.specification.editor

class FieldType {

    Integer fieldTypeId
    String name
    String description
    Integer length
    
    static constraints = {
        fieldTypeId unique: true
        name unique: true,blank: false
    }
    
    @Override
    String toString(){
        return name;
    }
}
