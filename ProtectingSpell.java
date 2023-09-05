package a12026025;

import java.util.Set;
import java.util.HashSet;

public class ProtectingSpell extends Spell {
	
	private Set<AttackingSpell> attacks; // must not be null or empty ; use
	//HashSet as concrete type

	public ProtectingSpell(String name, int manaCost, MagicLevel levelNeeded, Set<AttackingSpell> attacks) {
		super(name, manaCost, levelNeeded);
		if (attacks == null || attacks.isEmpty()) {
			throw new IllegalArgumentException("Attacksmust not be null or empty");
		}
		this.attacks = new HashSet<AttackingSpell>(attacks);
	}

	// call setProtection method on target with attacks as parameter
	@Override
	public void doEffect ( MagicEffectRealization target ) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if(target != null) {
			target.setProtection(attacks);	
		}

	}
	
	// returns "; protects against ’ listOfAttackSpells ’" where ’
	//listOfAttackSpells ’ is a bracketed list of all the attack spells (
	//Java default toString method for sets )
	//e. g. "; protects against [[ Confringo : 10 mana ; -20 HP] , [ Bombarda :
	//20 mana ; -50 % HP ]]"
	@Override
	public String additionalOutputString () {
		return "; protects against " + attacks.toString();
	}
}
