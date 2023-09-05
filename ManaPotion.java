package a12026025;

public class ManaPotion extends Potion {
	// must not be negative
	private int mana;
	
	public ManaPotion(String name, int usages, int price, int weight, int mana) {
		super(name, usages, price, weight);
		if (mana < 0) {
			throw new IllegalArgumentException("Mana cannot be less than 0");
		}
		this.mana = mana;
	}

	// returns "; + ’ mana ’ MP ";
	//e.g. ( total result of toString ) "[ Mana Potion ; 1 g; 2 Knuts ; 1 gulp ;
	//+20 MP ]"
	@Override
	public String additionalOutputString () {
		return "; +" + mana + " MP";
	}
	
	// if usages >0 reduce usages by 1 ( tryUsage method ) and
	// increase MP of target by mana ( call method enforceMagic ( mana ))
	@Override
	public void useOn ( MagicEffectRealization target ) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if(target!= null) {
			if (tryUsage()) {
				target.enforceMagic(mana);
			}
		}
		
	}

}
