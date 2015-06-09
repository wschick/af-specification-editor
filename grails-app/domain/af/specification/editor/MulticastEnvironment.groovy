package af.specification.editor


class MulticastEnvironment extends SpecificationObject {

    String address
    Integer port
    Boolean beta
    MulticastEnvironmentType multicastEnvironmentType
    
    static belongsTo = ['multicastGroup':MulticastGroup]
    static hasMany = ['multicastSources':MulticastSource]
    
    static constraints = {
        multicastEnvironmentType unique: ['multicastGroup']
    }

    @Override
    String toString(){
        return "${multicastGroup}/${multicastEnvironmentType}/${address}";
    }

    @Override
    boolean isPublished(){
        return false;
    }
}
