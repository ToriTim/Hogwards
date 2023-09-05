package a12026025;

import java.util.Set;

public interface MagicEffectRealization {
	/* negative amount must throw IllegalArgumentException ;
	//a typical implementation will reduce the object’s HP by amount
	ensuring however that HP does not become negative .*/
	default void takeDamage (int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Ammount cannot be negative");
		}
	}
	
	/*// percentage must be between 0 and 100 ( inclusive ) otherwise an
	IllegalArgumentException must be thrown ;
	//a typical implementation will reduce the object ’s HP by the
	percentage given of the object ’s basic HP value ensuring however ,
	that HP does not become negative .
	// calculations must be done in double data type converting back to int
	only in the last step*/
	default void takeDamagePercent (int percentage ) {
		if (percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
	}
	
	/* negative amount must throw IllegalArgumentException ;
	//a typical implementation will reduce the object ’s MP by amount
	ensuring however that MP does not become negative .*/
	default void weakenMagic (int amount ) {
		if (amount < 0) {
			throw new IllegalArgumentException("Ammount cannot be negative");
		}
	}
	
	/* percentage must be between 0 and 100 ( inclusive ) otherwise an
	IllegalArgumentException must be thrown ;
	//a typical implementation will reduce the object ’s MP by the
	percentage given of the object ’s basic MP value value ensuring
	however , that MP does not become negative ..
	// calculations must be done in double data type converting back to int
	only in the last step*/
	default void weakenMagicPercent (int percentage ) {
		if (percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
	}
	
	/* negative amount must throw IllegalArgumentException ;
	//a typical implementation will increase the object ’s HP by the amount
	given .*/
	default void heal (int amount ) {
		if (amount < 0) {
			throw new IllegalArgumentException("Ammount cannot be negative");
		}
	}
	
	/* percentage must be between 0 and 100 ( inclusive ) otherwise an
	IllegalArgumentException must be thrown ;
	//a typical implementation will increase the object ’s HP by the
	percentage given of the object ’s basic HP value .
	// calculations must be done in double data type converting back to int
	only in the last step*/
	default void healPercent (int percentage ) {
		if (percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
	}
	
	/* negative amount must throw IllegalArgumentException ;
	//a typical implementation will increase the object ’s MP by the amount
	given .*/
	default void enforceMagic (int amount ) {
		if (amount < 0) {
			throw new IllegalArgumentException("Ammount cannot be negative");
		}
	}
	
	/* percentage must be between 0 and 100 ( inclusive ) otherwise an
	IllegalArgumentException must be thrown ;
	//a typical implementation will increase the object ’s MP by the
	percentage given of the object ’s basic MP value
	 calculations must be done in double data type converting back to int
	only in the last step*/
	default void enforceMagicPercent (int percentage ) {
		if (percentage < 0 || percentage > 100) {
			throw new IllegalArgumentException("Percentage must be between 0 and 100");
		}
	}
	
	/* if s is null , an IllegalArgumentException must be thrown;
	// an implementation returns true if the object is protected against
	the spell s, false otherwise .*/
	default boolean isProtected (Spell s) { 
		return false;
	}
	
	/*if attacks is null an IllegalArgumentException must be thrown ;
	//a typical implementation will register the object as protected
	against all the spells in attacks*/
	default void setProtection (Set<AttackingSpell> attacks) {
		if (attacks == null) {
			throw new IllegalArgumentException("Attacks are null");
		}
	}
	
	/*/ if attacks is null an IllegalArgumentException must be thrown ;
	//a typical implementation will register the object as not protected
	against all the spells in attacks*/
	default void removeProtection (Set<AttackingSpell> attacks ) {
		if (attacks == null) {
			throw new IllegalArgumentException("Attacks are null");
		}
	}
		
}
