package charactor;

public class Hero {
	
	public String name;
	public float hp;
	public int damage;

	public Hero() {
	}

	public Hero(String name) {
		this.name = name;
	}

	public Hero(String name, int hp, int damage) {
		this.name = name;
		this.hp = hp;
		this.damage = damage;
	}

	public String getName(){
		return this.name;
	}
	
	public String toString() {
		return "Hero [name=" + name + ", hp=" + hp + ", damage=" + damage + "]\r\n";
	}
}