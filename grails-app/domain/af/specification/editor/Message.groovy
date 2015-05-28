package af.specification.editor

class Message {

    int messageVersion;
    boolean hasEstimate;
    MulticastGroup releaseMulticastGroup;
    MulticastGroup estimateMulticastGroup;
    
    static hasMany = ['messageFields':MessageField]
    
    static constraints = {
    }
}
