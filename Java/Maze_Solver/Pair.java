
public class Pair<F, S> {
    private F first;
    private S second;
    
    public Pair(F first_, S second_){
    	first=first_;
    	second=second_;
    }
    
    public String toString(){
    	return first.toString() + " " + second.toString();
    }
    
    public F getFirst(){
    	return first;
    }
    
    public S getSecond(){
    	return second;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        @SuppressWarnings("unchecked")
		Pair<F,S> guest = (Pair<F,S>) obj;
        
        return (guest.first == this.first) && (guest.second == this.second);
    }
}
