package a12026025;

public abstract class Potion extends MagicItem{
	public Potion(String name, int usages, int price, int weight) {
		super(name, usages, price, weight);
	}
	
	// delegates to method call useOn ( drinker )
	public void drink (Wizard drinker) {
		if(drinker == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		this.useOn(drinker);
	}
	
	// returns " gulp " if usages is equal to 1 , " gulps " otherwise
    @Override
	public String usageString () {
    	return (getUsages() == 1) ? "gulp" : "gulps";
    }
}
