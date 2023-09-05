package a12026025;

public enum MagicLevel {
	NOOB(50),
    ADEPT(100),
    STUDENT(200),
    EXPERT(500),
    MASTER(1000);

    private final int mana;

    private MagicLevel(int m) {
        this.mana = m;
    }
    
    public int toMana() {
        return mana;
    }
    
    @Override
    public String toString() {
    	switch (this) {
        case NOOB:
            return "*";
        case ADEPT:
            return "**";
        case STUDENT:
            return "***";
        case EXPERT:
            return "****";
        case MASTER:
            return "*****";
        default:
            return "";
    }
  }
}
