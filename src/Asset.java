
public class Asset {
	
	private int id;
	private String assetName;
	private boolean legal;
	private int profit;
	private int penalty;
	private static final int appleProfit = 2;
	private static final int cheeseProfit = 3;
	private static final int breadProfit = 4;
	private static final int chickenProfit = 4;
	private static final int silkProfit = 9;
	private static final int pepperProfit = 8;
	private static final int barrelProfit = 7;
	private static final int legalPenalty = 2;
	private static final int illegalPenalty = 4;
	private static final int silkID = 10;
	private static final int pepperID = 11;
	private static final int barrelID = 12;
	
	public Asset(int ID) {
		switch(ID) {
		case 0:
			id = ID;
			assetName = "Apple";
			legal = true;
			profit = appleProfit;
			penalty = legalPenalty;
			
			break;
		case 1:
			id = ID;
			assetName = "Cheese";
			legal = true;
			profit = cheeseProfit;
			penalty = legalPenalty;
			
			break;
		case 2:
			id = ID;
			assetName = "Bread";
			legal = true;
			profit = breadProfit;
			penalty = legalPenalty;
			
			break;
		case 3:
			id = ID;
			assetName = "Chicken";
			legal = true;
			profit = chickenProfit;
			penalty = legalPenalty;
			
			break;
			
		case silkID:
			id = ID;
			assetName = "Silk";
			legal = false;
			profit = silkProfit;
			penalty = illegalPenalty;
			
			break;
		case pepperID:
			id = ID;
			assetName = "Pepper";
			legal = false;
			profit = pepperProfit;
			penalty = illegalPenalty;
			
			break;
		case barrelID:
			id = ID;
			assetName = "Barrel";
			legal = false;
			profit = barrelProfit;
			penalty = illegalPenalty;
			
			break;
			
		}
		
		
	}
	
	public boolean equals(Object object) {
        if (!(object instanceof Asset)) {
            return false;
        }
        Asset otherMember = (Asset)object;
        return otherMember.id == this.id;
    }
	
	public int getProfit() {
		return this.profit;
	}
	
	public int getPenalty() {
		return this.penalty;
	}
	
	public boolean isLegal() {
		return this.legal;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getAssetName() {
		return this.assetName;
	}
	
	public int getIdOf(String asset) {
		int ID = 0;
		if (this.assetName.equals(asset)) {
			ID = this.id;
		}
		return ID;
	}
	
	public String toString() {
		return this.assetName;
	}
}
