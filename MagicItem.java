package a12026025;

public abstract class MagicItem implements Tradeable, MagicEffectRealization, MagicSource {
	
	private String name; // must not be null or empty	
	private int usages; // number of usages remaining; must not be negative	
	private int price; // must not be negative
	private int weight; // must not be negative
	
	public MagicItem(String name, int usages, int price, int weight) {
		if (name == null || name.isEmpty()) {
	        throw new IllegalArgumentException("Name must not be null or empty.");
	    }

	    if (usages < 0) {
	        throw new IllegalArgumentException("Usages must not be negative.");
	    }

	    if (price < 0) {
	        throw new IllegalArgumentException("Price must not be negative.");
	    }

	    if (weight < 0) {
	        throw new IllegalArgumentException("Weight must not be negative.");
	    }

	    this.name = name;
	    this.usages = usages;
	    this.price = price;
	    this.weight = weight;
	}
	
	public int getUsages () {
		return usages;
	}
	
	// if usages > 0 reduce usage by 1 and return true , otherwise return
	//false
	public boolean tryUsage() {
		if (usages > 0) {
			--usages;
			return true;
		}
		else {
			return false;
		}
	}
	
	// returns "use" if usages is equal to 1 , "uses" otherwise
	public String usageString() {
		if(usages == 1) {
			return "use";
		}
		else {
			return "uses";
		}
	}
	
	public String additionalOutputString () {
		return "";
	}
	
	
	// formats this object according to "[ ’ name ’; ’weight ’ g; ’price ’ ’
	//currencyString ’; ’usages ’ ’usageString ’’ additionalOutputString ’]"
	// ’ currencyString ’ is " Knut " if price is 1 , " Knuts " otherwise
	//e.g. ( when additionalOutput () returns an empty string ) "[ Accio
	//Scroll ; 1 g; 1 Knut ; 5 uses ]" or "[ Alohomora Scroll ; 1 g; 10 Knuts ;
	//1 use ]"
	@Override
	public String toString() {
		String currencyString = (price == 1) ? "Knut" : "Knuts";
	    return "[" + name + "; " + weight + " g; " + price + " " + currencyString + "; " 
	    		+ usages + usageString() + additionalOutputString () + "]";
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public boolean provideMana(MagicLevel levelNeeded, int manaAmount) {
		return true;
	}
	
	// reduce usages to usages *(1 - percentage /100.)
	// calculations must be done in double data type converting back to int
	//	only in the last step*/
	
	@Override
	public void takeDamagePercent (int percentage ) {
		double calculation = usages;
		calculation = calculation * (1 - percentage);
		usages = (int) calculation;
	}

}
