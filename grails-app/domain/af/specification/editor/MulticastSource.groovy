package af.specification.editor

class MulticastSource extends SpecificationObject {
    
    String name;
    String address;
    
    static belongsTo = MulticastEnvironment
    static hasMany = ['multicastEnvironments':MulticastEnvironment]

    static constraints = {
        name unique: true
        address unique: true
    }

    @Override
    boolean isPublished(){
        return false;
    }
    
    @Override
    String toString(){
        return "${name} (${address})";
    }
}
