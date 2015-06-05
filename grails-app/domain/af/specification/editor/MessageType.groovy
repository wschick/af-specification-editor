package af.specification.editor

class MessageType {

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
}
