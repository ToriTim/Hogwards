package a12026025;

public class HealingSpell extends Spell {

	private boolean type ;
	private boolean percentage ;
	private int amount ; // has to be non negative ; if percentage == true ,
	//amount must be in the interval [0 ,100]
	
	
	public HealingSpell(String name, int manaCost, MagicLevel levelNeeded, boolean type,
			boolean percentage, int amount) {
		super(name, manaCost, levelNeeded);
		
		if(amount <0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		
		if (percentage == true && amount > 100) {
			throw new IllegalArgumentException("Amount must be in the interval [0 ,100]");
		}
		
		this.type = type;
		this.percentage = percentage;
		this.amount = amount;
	}

	// use one of the functions heal , healPercent , enforceMagic or
	//enforceMagicPercent according to the flags type and percentage

	@Override
	public void doEffect(MagicEffectRealization target) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if(target != null) {
			if (type && percentage) {
	            target.healPercent(amount);
	        } else if (type && !percentage) {
	            target.heal(amount);
	        } else if (!type && percentage) {
	            target.enforceMagicPercent(amount);
	        } else {
	            target.enforceMagic(amount);
	        }
		}
	}


	
	
	// returns "; -’ amount ’ ’percentage ’ ’HPorMP ’" , where ’percentage ’ is a
	//’% ’ - sign if percentage is true , empty otherwise and HPorMP is HP
	//if type is true , MP otherwise
	//e. g. "; -10 MP" or "; -50 % HP"
	@Override
	public String additionalOutputString () {
		String per = (percentage) ? " %" : "";
		String HPorMP = (type) ? " HP" : " MP";
		return "; +" + amount + per + HPorMP;
	}

}
