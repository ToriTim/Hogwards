package a12026025;

import java.util.Set;
import java.util.HashSet;

public class AttackingSpell extends Spell {
	
	private boolean type;
	private boolean percentage;
	private int amount; // has to be non negative ; if percentage == true ,
	//amount must be in the interval [0 ,100]
	
	public AttackingSpell(String name, int manaCost, MagicLevel levelNeeded, boolean type,
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

	/*// if the target is protected against this spell ( isProtected ), then
	protection against exactly this spell is removed ( removeProtection )
	.
	// otherwise use one of the functions takeDamage , takeDamagePercent ,
	weakenMagic or weakenMagicPercent on target according to the flags
	type and percentage*/

	@Override
	public void doEffect(MagicEffectRealization target) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if(target != null) {
			if (target.isProtected(this)) {
				Set<AttackingSpell> a = new HashSet<AttackingSpell>();
				a.add(this);
				target.removeProtection(a);
			}
			else {
				if (type && percentage) {
		            target.takeDamagePercent(amount);
		        } else if (type && !percentage) {
		            target.takeDamage(amount);
		        } else if (!type && percentage) {
		            target.weakenMagicPercent(amount);
		        } else {
		            target.weakenMagic(amount);
		        }
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
		return "; -" + amount + per + HPorMP;
	}

}
