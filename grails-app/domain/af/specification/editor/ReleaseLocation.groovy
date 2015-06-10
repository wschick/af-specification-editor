package af.specification.editor

class ReleaseLocation {

    String name
    String display
    Country country
    
    static constraints = {
        name unique: true
        display unique: true
    }
}
