//import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import java.util.Map;

public class Bag {
	
	List<Asset> bagList = new LinkedList<>();
	private String declaredType;
	// public int declaredTypeID;
	private int bribe;
	
	public Bag() {
		bribe = 0;
	}
	
	public void addInBag(Asset a) {
		bagList.add(a);
	}
	
	public Asset drawFromBag(Asset a) {
		if (bagList.contains(a))
		    bagList.remove(a);
		return a;
	}
	
	// la fiecare runda, golim sacul
	public void clearBag() {
		if (!bagList.isEmpty())
		    bagList.clear();
	}
	
	public void setBribe(int bribe) {
		this.bribe = bribe;
	}
	
    public int getBribe() {
    	return this.bribe;
    }
    
    public void setDeclaredType(String s) {
    	this.declaredType = s;
    }
    
    public String getDeclaredType() {
    	return this.declaredType;
    }
    public boolean isFull() {
    	return bagList.size() == 5;
    }
}
