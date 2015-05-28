package af.specification.editor

class Category {
    
    long id;
    String name;
    String description;
    String reporterHeading;
    Country country;
    String releaseMethod;
    String releaseLocation;
    
    static hasMany = ['messages':Message,
                        'data': Datum]

    static mapping = {
        id generator: "assigned"
    }
    static constraints = {
        description maxSize: 2048
    }
}
