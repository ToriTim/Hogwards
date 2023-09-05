package a12026025;

public class HealthPotion extends Potion {
	private int health ; // must not be negative
	
	public HealthPotion(String name, int usages, int price, int weight, int health) {
		super(name, usages, price, weight);
		
		if(health < 0) {
			throw new IllegalArgumentException("health cannot be negative");
		}
		
		this.health = health;
	}

	
	
	// returns "; + ’ health ’ HP ";
	//e.g. ( total result of toString ) "[ Health Potion ; 1 g; 1 Knut ; 5
	//gulps ; +10 HP ]"
	@Override
	public String additionalOutputString () {
		return "; +" + health + " HP";
	}
	
	// if usages >0 , reduce usages by 1 ( tryUsage method ) and
	// increase HP of target by health ( call method heal ( health ))
	@Override
	public void useOn ( MagicEffectRealization target ) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if (target != null) {
			if (tryUsage()) {
				target.heal(health);
				}
		}	
	}


}
