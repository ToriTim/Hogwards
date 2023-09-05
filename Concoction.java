package a12026025;

import java.util.List;
import java.util.ArrayList;

public class Concoction extends Potion {
	private int health; // may be any int value
	private int mana; // may be any int value
	private List <Spell> spells; // must not be null but may be empty ; Use
	//ArrayList as concrete type
	// it is not allowed for health and mana to be both 0 and spells to be
	//empty ; The potion must at least have one effect
	
	public Concoction(String name, int usages, int price, int weight, int health, 
			int mana, List <Spell> spells) {
        super(name, usages, price, weight);
   
        if ( spells == null) {
        	throw new IllegalArgumentException("Spells must not be null");
        } 
        if ( health == 0 && mana == 0 && spells.isEmpty()) {
        	throw new IllegalArgumentException("The potion must at least have one effect");
        }
        
        this.health = health;
        this.mana = mana;
        this.spells = new ArrayList<Spell>(spells);
    }

	public Concoction(String name, int usages, int price, int weight, int health, 
			int mana) {
		this(name, usages, price, weight, health, mana, new ArrayList<Spell>());
	}
	
	// returns "; ’+/ - ’ ’ health ’ HP; ’+/ - ’ ’ mana ’ MP; cast ’spells ’ ";
	// here ’+/ - ’ denotes the appropriate sign , spells will be a bracketed
	//list of spells ( Java default toString method for lists )
	//e.g. ( total result of toString ) "[ My Brew ; 2 g; 2 Knuts ; 4 gulps ; -5
	//HP; +10 MP; cast [[ Confringo -20 HP] , [ Diffindo -15 HP ]]]"
	// if health or mana is 0 or spells is empty , then the respective part (
	//s) are suppressed e. g. "[ Your Brew ; 2 g; 1 Knut ; 1 gulp ; +5 MP]
	@Override
	public String additionalOutputString () {
		String sHealth = (health == 0) ? "" : 
			(health < 0) ? "; -" + health + " HP" : "; +" + health + " HP";
		
		String sMana = (mana == 0) ? "" : 
			(mana < 0) ? "; -" + mana + " MP" : "; +" + mana + " MP";
		
		String sSpells = (spells.isEmpty()) ? "" : "; cast " + spells;
		
		return  sHealth + sMana + sSpells;
	}

    /*// if usages >0 reduce usages by 1 ( tryUsage method ) and
    // change HP of target by health ( call method heal ( health ) or
   takeDamage ( health ) depending on sign of health )
   // change MP of target by mana ( call method enforceMagic ( magic ) or
   weakenMagic ( magic ) depending on sign of mana )
   // call cast Method for every spell in spells*/
	@Override
	public void useOn(MagicEffectRealization target) {
		if(target == null) {
			throw new IllegalArgumentException("Drinker is null");
		}
		if(target!= null) {
			if(tryUsage()) {
				if (health < 0) {
		            target.takeDamage(health*(-1));
		        } else {
		            target.heal(health);
		        }
				
				if (mana < 0) {
		            target.weakenMagic(mana*(-1));
		        } else {
		            target.enforceMagic(mana);
		        }
				if (!spells.isEmpty()) {
					for (Spell s: spells) {
						 s.cast(this, target);
					 }
				}	 
			}
		}
		
	}

}
