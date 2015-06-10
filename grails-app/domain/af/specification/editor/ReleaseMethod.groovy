package af.specification.editor

class ReleaseMethod {

    String name
    String display
    
    static constraints = {
        name unique: true
        display unique: true
    }
}
