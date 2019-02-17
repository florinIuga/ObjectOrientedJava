package game;
import java.util.LinkedList;
import java.util.List;

public final class Bag {

    private static final int MAX = 5;
    protected List<Asset> bagList = new LinkedList<>();
    private String declaredType;
    private int bribe;

    public Bag() {
       bribe = 0;
    }

    public void addInBag(final Asset a) {
       bagList.add(a);
    }

    public Asset drawFromBag(final Asset a) {
        if (bagList.contains(a)) {
           bagList.remove(a);
        }
       return a;
    }

    public void clearBag() {
        if (!bagList.isEmpty()) {
           bagList.clear();
         }
    }

    public void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    public int getBribe() {
        return this.bribe;
    }

    public void setDeclaredType(final String s) {
        this.declaredType = s;
    }

    public String getDeclaredType() {
        return this.declaredType;
    }

    public boolean isFull() {
        return bagList.size() == MAX;
    }
}
