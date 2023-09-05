package a12026025;

public class Scroll extends MagicItem {
	 // must not be null
	private Spell spell;

	public Scroll(String name, int usages, int price, int weight, Spell spell) {
		super(name, usages, price, weight);
		if (spell == null) {
			throw new IllegalArgumentException("Spell is null");
		}
		this.spell = spell;
	}
	
	// returns "; casts ’spell ’"
		//e.g. ( total result of toString ) "[ Scroll of doom ; 1 g; 100 Knuts ; 5
		//usages ; casts [ Bombarda : 20 mana ; -50 % HP ]]"
	@Override
	public String additionalOutputString () {
		return "; casts " + spell;
	}
	
	// if usages >0 reduce usages by 1 ( tryUsage method ) and
	// cast the spell using this as magic source and parameter target as
	//target
	@Override
	public void useOn (MagicEffectRealization target) {
		if (tryUsage()) {		
			spell.cast(this, target);
		}
	}

}
