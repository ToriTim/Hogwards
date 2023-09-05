package a12026025;

import java.util.Set;
import java.util.HashSet;
import java.util.Random;

public class Wizard implements MagicSource, Trader, MagicEffectRealization {

	private String name; // not null not empty
	private MagicLevel level; // not null
	private int basicHP; // not negative
	private int HP; // not negative ; defaults to basicHP
	private int basicMP; // not less than the manapoints associated with the
	//magic level
	private int MP; // not negative ; defaults to basicMP
	private int money; // not negative
	private Set <Spell> knownSpells; // not null , may be empty ; use HashSet
	//for instantiation
	private Set <AttackingSpell> protectedFrom; // not null , may be empty ; use
	//HashSet for instantiation
	private int carryingCapacity; // not negative
	private Set <Tradeable> inventory; // not null , may be empty , use HashSet
	//for instantiation , total weight of inventory may never exceed
	//carryingCapacity
	
	public Wizard(String name, MagicLevel level, int basicHP,int HP, int basicMP, int MP, int money, 
			Set <Spell> knownSpells, Set <AttackingSpell> protectedFrom, int carryingCapacity,
			Set <Tradeable> inventory) {
		
		if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (level == null) {
            throw new IllegalArgumentException("MagicLevel cannot be null.");
        }
        if (basicHP < 0) {
            throw new IllegalArgumentException("Basic HP cannot be negative.");
        }
        if (HP < 0) {
            throw new IllegalArgumentException("HP cannot be negative.");
        }
        if (basicMP < level.toMana()) {
            throw new IllegalArgumentException("Basic MP cannot be less than the mana points associated with the magic level.");
        }
        if (MP < 0) {
            throw new IllegalArgumentException("MP cannot be negative.");
        }
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative.");
        }
        if (knownSpells == null) {
            throw new IllegalArgumentException("Known spells set cannot be null.");
        }
        
        if (protectedFrom == null) {
            throw new IllegalArgumentException("Known spells set cannot be null.");
        }
        
        if (carryingCapacity < 0) {
            throw new IllegalArgumentException("Carrying capacity cannot be negative.");
        }
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory set cannot be null.");
        }

        this.name = name;
        this.level = level;
        this.basicHP = basicHP;
        this.HP = HP;
        this.basicMP = basicMP;
        this.MP = MP;
        this.money = money;
        this.knownSpells = new HashSet<Spell>(knownSpells);
        this.protectedFrom = new HashSet<AttackingSpell>(protectedFrom);
        this.carryingCapacity = carryingCapacity;
        this.inventory = new HashSet<Tradeable>(inventory);
    }
	
	public Wizard(String name, MagicLevel level, int basicHP, int basicMP, int money, 
			Set <Spell> knownSpells, Set <AttackingSpell> protectedFrom, int carryingCapacity,
			Set <Tradeable> inventory) {
	this(name, level, basicHP, basicHP, basicMP, basicMP,money, 
				knownSpells, protectedFrom, carryingCapacity,
				inventory);
	}
	
	/*HP = zero = dead*/
	public boolean isDead () {
	  if (HP == 0) {
		  return true;
	  }
	  else {
		  return false;
	  }
	}
	
	/* calculates and returns the total weight of all the items in the
	inventory*/
	private int inventoryTotalWeight () {
		int res = 0;
		for (Tradeable i : inventory) {
			res += i.getWeight();
		}
		return res;
	}
	
	// if spell is null , IllegalArgumentException has to be thrown
	// if wizard is dead ( isDead ) no action can be taken and false is
	//returned
	// add spell to the set of knownSpells
	// returns true , if insertion was successful , false otherwise
	public boolean learn (Spell s) {
		if (s == null) {
			throw new IllegalArgumentException("Spell is null");
		}
		
		if (this.isDead()) {return false;}
		else {
			return knownSpells.add(s);
		}
		
	}
	
	// if spell is null , IllegalArgumentException has to be thrown
	// if wizard is dead ( isDead ) no action can be taken and false is
	//returned
	// remove spell from the set of knownSpells
	// returns true , if removal was successful , false otherwise
	public boolean forget ( Spell s ) {
		if (s == null) {
			throw new IllegalArgumentException("Spell is null");
		}
		
		if (this.isDead()) {return false;}
		else {
			return knownSpells.remove(s);
		}
	}
	
	// if s or target is null , IllegalArgumentException has to be thrown
	// if wizard is dead ( isDead ) no action can be taken and false is
	//returned
	// if wizard does not know the spell , false is returned
	// call cast on s with this as source and parameter target as target
	// return true , if cast was called
	public boolean castSpell (Spell s, MagicEffectRealization target) {
		if (s == null || target == null) {
			throw new IllegalArgumentException("Spell/target is null");
		}
		
		if (this.isDead() || !knownSpells.contains(s)) {return false;}
		else {
			int oldMana = MP;
			s.cast(this, target);
			if(oldMana != MP) {return true;}
			else {return false;}
		}
	}
	
	// if this object ’s knownSpells is empty , return false
	// otherwise choose a random spell from knownSpells and delegate to
	//castSpell (Spell , MagicEffectRealization )
	public boolean castRandomSpell (MagicEffectRealization target) {
		if(knownSpells.isEmpty()) {return false;}
		
		int randomIndex = new Random().nextInt(knownSpells.size());
		Spell[] sArray = knownSpells.toArray(new Spell[knownSpells.size()]);
		Spell randomSpell =  sArray[randomIndex];
		
		return this.castSpell(randomSpell, target);
	}
	
	// if item or target is null , IllegalArgumentException has to be thrown
	// if wizard is dead ( isDead ) no action can be taken and false is
	//returned
	// if wizard does not possess the item , false is returned
	// call useOn on the item with parameter target as target
	// return true if useOn was called
	public boolean useItem ( Tradeable item , MagicEffectRealization target ) {
		if (item == null || target == null) {
			throw new IllegalArgumentException("Item/target is null");
		}
		
		if (this.isDead() || !inventory.contains(item)) {return false;}
		else {
			item.useOn(target);
			return true;
		}
	}
	
	
	// if this object ’s inventory is empty , return false
	// otherwise choose a random item from inventory and delegate to
	//useItem ( Tradeable , MagicEffectRealization )
	public boolean useRandomItem ( MagicEffectRealization target ) {
		if(inventory.isEmpty()) {return false;}
		
		int randomIndex = new Random().nextInt(inventory.size());
		Tradeable[]  iArray = inventory.toArray(new Tradeable[inventory.size()]);
		Tradeable randomItem =  iArray[randomIndex];
		
		return this.useItem(randomItem, target);
	}
	
	// if item or target is null , IllegalArgumentException has to be thrown
	// if wizard is dead ( isDead ), no action can be taken and false is
	//returned
	// call purchase on the item with this as seller and target as buyer
	// return true , if purchase was called successfully ( returned true ),
	//false otherwise .
	public boolean sellItem (Tradeable item, Trader target) {
		if (item == null || target == null) {
			throw new IllegalArgumentException("Item/target is null");
		}
		
		if (this.isDead()) {return false;}
		
		else {
			return item.purchase(this, target);
		}
	}

	// if this object ’s inventory is empty , return false
	// otherwise choose a random item from inventory and delegate to
	//sellItem ( Tradeable , MagicEffectRealization )
	public boolean sellRandomItem (Trader target) {
		if(inventory.isEmpty()) {return false;}
		
		int randomIndex = new Random().nextInt(inventory.size());
		Tradeable[]  iArray = inventory.toArray(new Tradeable[inventory.size()]);
		Tradeable randomItem =  iArray[randomIndex];
		
		return this.sellItem(randomItem, target);
	}
	
	
	// returns a string in the format "[ ’ name ’( ’ level ’): ’HP ’/ ’ basicHP ’ ’MP
	//’/ ’ basicMP ’; ’money ’ ’KnutOrKnuts ’; knows ’knownSpells ’; carries ’
	//inventory ’]"; where ’level ’ is the asterisks representation of the
	//level (see MagicLevel . toString ) and ’knownSpells ’ and ’inventory ’
    //use the default toString method of Java Set; ’ KnutOrKnuts ’ is Knut
	//if ’money ’ is 1 , Knuts otherwise
	//e.g. [ Ignatius (**) : 70/100 100/150; 72 Knuts ; knows [[ Episkey (*): 5
	//mana ; +20 HP] , [ Confringo : 10 mana ; -20 HP ]]; carries []]
	@Override
	public String toString () {
		String kn = (money == 1) ? " Knut; knows " : " Knuts; knows ";
		return "[" + name + "(" + level.toString() + "): " + HP + "/" +
	basicHP + " " + MP + "/" + basicMP + "; " + money + kn + knownSpells.toString() 
	+ "; carries " + inventory.toString() + "]";
	}
	
	// MagicSource Interface
	/*// if wizard is dead ( isDead ) no action can be taken and false is
	//returned
	// check if level is at least levelNeeded , return false otherwise
	// if MP < manaAmount return false
	// subtract manaAmount from MP and return true*/
	@Override
	public boolean provideMana (MagicLevel levelNeeded, int manaAmount) {
		if(levelNeeded == null || manaAmount < 0){
			throw new IllegalArgumentException ("Mana is negative or level is null");
		} 
				
		if (this.isDead()) {return false;}
		if (level.toMana() >= levelNeeded.toMana() && MP >= manaAmount) {
			MP -= manaAmount;
			return true;
		}
		else {return false;}
	}
	
	// Trader Interface
	// return true . if the item is in the inventory , false otherwise
	@Override
	public boolean possesses (Tradeable item) {
		if (inventory.contains(item)) {return true;}
		else {return false;}
	}
	
	// return true , if money >= amount , false otherwise
	@Override
	public boolean canAfford (int amount) {
		if(amount<0) {
			throw new IllegalArgumentException("Amount is negative");
		}
		if (money >= amount) {return true;}
		else {return false;}
	}
	
	// return true , if inventoryTotalWeight +weight <= carryingCapacity , false
	//otherwise
	@Override
	public boolean hasCapacity (int weight) {
		if(weight<0) {
			throw new IllegalArgumentException("weight is negative");
		}
		if (inventoryTotalWeight() + weight <= carryingCapacity) {return true;}
		else {return false;}
	}
	
	// if wizard is dead ( isDead ), no action can be taken and false is
	//returned
	// if this owns enough money , deduct amount from money and return true ,
	//return false otherwise
	@Override
	public boolean pay (int amount) {
		if(this.isDead()) {return false;}
		else {
			if(this.canAfford(amount)) {
				money -= amount;
				return true;
			}
			else {return false;}
		}
	}

	// if wizard is dead ( isDead ), no action can be taken and false is
	//returned
	// add amount to this object ’s money and return true
	@Override
	public boolean earn (int amount ) {
		if(amount<0) {
			throw new IllegalArgumentException("earn is negative");
		}
		if(this.isDead()) {return false;}
		else {
			money += amount;
			return true;
		}
	}

	// add item to inventory if carryingCapacity is sufficient
	// returns true , if item is successfully added , false otherwise (
	//carrying capacity exceeded or item is already in the inventory )
	@Override
	public boolean addToInventory (Tradeable item) {
		if(item == null) {
			throw new IllegalArgumentException("Item is null");
		}
		if (this.hasCapacity(item.getWeight())) {
			return inventory.add(item);
		}
		else {return false;}
	}
	
	// remove item from inventory
	// returns true , if item is successfully removed , false otherwise ( item
	//not in the inventory )
	@Override
	public boolean removeFromInventory (Tradeable item) {
		return inventory.remove(item);
	}
	
	// returns true , if this object ’s HP are not 0 ( alive wizard )
	@Override
	public boolean canSteal () {
		return !this.isDead();
	}
	
	// if thief is null , IllegalArgumentException has to be thrown
	// if thief cannot steal ( canSteal returns false ), no action can be
	//taken and false is returned
	// returns false , if the object ’s inventory is empty
	// otherwise transfers a random item from the this object ’s inventory
	//into the thief ’s inventory ;
	// if the thief ’s inventory has not enough capacity , the object just
	//vanishes and false is returned
	// returns true if , theft was successful
	@Override
	public boolean steal (Trader thief) {
		if (thief == null ) {
			throw new IllegalArgumentException("Thief is null");
		}
		
		if (!thief.canSteal() || inventory.isEmpty() ) {return false;}
		else {
			int randomIndex = new Random().nextInt(inventory.size());
			Tradeable[]  iArray = inventory.toArray(new Tradeable[inventory.size()]);
			Tradeable randomItem =  iArray[randomIndex];
			return randomItem.give(this, thief);
		}
		
	}
	
	// returns true , if this object ’s HP are 0 ( dead wizard )
	@Override
	public boolean isLootable () {
		return this.isDead();
	}
	
	// returns true , if this object ’s HP are not 0 ( alive wizard )
	@Override
	public boolean canLoot () {
		return !this.isDead();
	}

	// if looter is null , IllegalArgumentException has to be thrown
	// if looter cannot loot ( canLoot returns false ), no action can be
	//taken and false is returned
	// if the this object can be looted ( isLootable ), transfer all the
	//items in the object ’s inventory into the looter ’s inventory ;
	// items that don ’t fit in the looter ’s inventory because auf the
	//weight limitation just vanish
	// returns true , if at least one item was successfully transferred ,
	//false otherwise
	@Override
	public boolean loot (Trader looter) {
		if (looter == null) {
			throw new IllegalArgumentException("Looter is null");
		}
		
		if (!looter.canLoot()) {return false;}
		if (this.isLootable()) {
			int looted = 0;
			Tradeable[]  iArray = inventory.toArray(new Tradeable[inventory.size()]);
			for (Tradeable i: iArray) {
				if (i.give(this, looter)){looted++;}
			}
			return (looted == 0) ? false : true;
		}
		return false;
	}
	
	
	// MagicEffectRealization Interface
	// reduce the object ’s HP by amount ensuring however that HP does not
	//become negative.
	@Override
	public void takeDamage (int amount ) {
		MagicEffectRealization.super.takeDamage(amount);
		if (amount > HP) {HP = 0;}
		else {HP -= amount;}
	}
	
	// reduce the object ’s HP by the percentage given of the object ’s basic
	//HP value ensuring however , that HP does not become negative . Do
	//calculations in double truncating to int only for the assignment
	@Override
	public void takeDamagePercent (int percentage ) {
		MagicEffectRealization.super.takeDamagePercent(percentage);
		double blood = ((double)basicHP)*percentage/100;
		HP -= (int)blood;
		if (HP < 0 ) {HP = 0;}
	}
	
	// reduce the object ’s MP by amount ensuring however that MP does not
	//become negative .
	@Override
	public void weakenMagic (int amount ) {
		MagicEffectRealization.super.weakenMagic(amount);
		if (amount > MP) {MP = 0;}
		else {MP -= amount;}
	}

	// reduce the object ’s MP by the percentage given of the object ’s basic
	//MP value ensuring however , that MP does not become negative . Do
	//calculations in double truncating to int only for the assignment
	@Override
	public void weakenMagicPercent (int percentage ) {
		MagicEffectRealization.super.weakenMagicPercent(percentage);
		double leak = ((double)basicMP)*percentage/100;
		MP -= (int)leak;
		if (MP < 0 ) {MP = 0;}
	}
	
	// increase the object ’s HP by the amount given .
	@Override
	public void heal (int amount) {
		MagicEffectRealization.super.heal(amount);
		HP = HP + amount;
	}
	
	// increase the object ’s HP by the percentage given of the object ’s
	//basic HP. Do calculations in double truncating to int only for the
	//assignment
	@Override
	public void healPercent (int percentage ) {
		MagicEffectRealization.super.healPercent(percentage);
		double blood = ((double)basicHP)*percentage/100;
		HP += (int)blood;
	}

	// increase the object ’s MP by the amount given .
	@Override
	public void enforceMagic (int amount ) {
		MagicEffectRealization.super.enforceMagic(amount);
		MP += amount;

	}

	// increase the object ’s MP by the percentage given of the object ’s
	//basic MP. Do calculations in double truncating to int only for the
	//assignment
	@Override
	public void enforceMagicPercent (int percentage ) {
		MagicEffectRealization.super.enforceMagicPercent(percentage);
		double leak = ((double)basicMP)*percentage/100;
		MP += (int)leak;

	}
	
	//if s is null , an IllegalArgumentException must be thrown;
	// return true , if s is contained in instance variable protectedFrom
	@Override
	public boolean isProtected (Spell s) {
		if(s == null) {
			throw new IllegalArgumentException("S is null");
		}
		if (protectedFrom.contains(s)) {
			return true;
		}
		else {return false;}
	}
	
	// add all spells from attacks to instance variable protectedFrom
	@Override
	public void setProtection ( Set < AttackingSpell > attacks ) {
		if(attacks == null) {
			throw new IllegalArgumentException("Item is null");
		}
		for (AttackingSpell a : attacks) {
			protectedFrom.add(a);
		}
	}
	
	// remove all spells from attacks from instance variable protectedFrom
	@Override
	public void removeProtection ( Set < AttackingSpell > attacks ) {
		if(attacks == null) {
			throw new IllegalArgumentException("Item is null");
		}
		for (AttackingSpell a : attacks) {
			protectedFrom.remove(a);
		}
	}

}
