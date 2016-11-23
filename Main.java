import java.util.ArrayList;
import java.util.Scanner;

abstract class Product {
	private int price;
	private String name;

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public Product(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public String toString() {
		return getName();
	}
}

class Meal extends Product {
	String cuisine;

	public Meal(String name, int price, String cuisine) {
		super(name, price);
		this.cuisine = cuisine;
	}
}

class Dessert extends Product {
	public Dessert(String name, int price) {
		super(name, price);
	}
}

class Drink extends Product implements Cloneable {
	private boolean iceCubes;
	private boolean lemon;

	public Drink(String name, int price) {
		super(name, price);
		this.iceCubes = false;
		this.lemon = false;
	}

	public int getPrice() {
		int price = super.getPrice();
		if (iceCubes) {
		}
		if (lemon) {
		}
		return price;
	}

	public void addIceCubes() {
		iceCubes = true;
	}

	public void addLemon() {
		lemon = true;
	}

	public String getName() {
		String name = super.getName();
		if (iceCubes || lemon) {
			name += " with:";
		}
		if (iceCubes) {
			name += " ice cubes";
		}
		if (lemon) {
			name += " lemon";
		}
		return name;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

class Lunch {
	public Meal meal;
	public Dessert dessert;

	public Lunch(Meal meal, Dessert dessert) {
		this.meal = meal;
		this.dessert = dessert;
	}

	public int getPrice() {
		return meal.getPrice() + dessert.getPrice();
	}
}

class Order {
	public Lunch lunch;
	public Drink drink;

	public Order(Lunch lunch, Drink drink) {
		this.lunch = lunch;
		this.drink = drink;
	}

	public int getPrice() {
		int price = 0;
		if (lunch != null) {
			price += lunch.getPrice();
		}
		if (drink != null) {
			price += drink.getPrice();
		}
		return price;
	}

	public String getOrderInfo() {
		String info = "";
		if (lunch != null) {
			info += lunch.meal.getName() + " " + lunch.dessert.getName() + " ";
		}
		if (drink != null) {
			info += drink.getName();
		}
		return info;
	}
}

public class Main {

	public static void main(String[] argv) {

		/*
		 * Import all products here, Hard-coded example bellow
		 */
		ArrayList<Meal>[] meals = (ArrayList<Meal>[]) new ArrayList[3];
		for (int i = 0; i < 3; ++i) {
			meals[i] = new ArrayList<Meal>();
		}
		ArrayList<Drink> drinks = new ArrayList<Drink>();
		ArrayList<Dessert> desserts = new ArrayList<Dessert>();

		meals[0].add(new Meal("pierogi", 1000, "polish"));
		meals[1].add(new Meal("mex", 1000, "mexican"));
		meals[1].add(new Meal("mex2", 1100, "mexican"));
		meals[2].add(new Meal("pizza", 1600, "italian"));
		meals[2].add(new Meal("pizza 2", 1500, "italian"));
		meals[2].add(new Meal("spaghetti", 1200, "italian"));
		drinks.add(new Drink("Coca-cola", 300));
		drinks.add(new Drink("Sprite", 300));
		desserts.add(new Dessert("Chocolate Cake", 800));
		desserts.add(new Dessert("Cookies", 500));



		Scanner scanner = new Scanner(System.in);

		System.out.println("What do you want to order?");
		System.out.println("1-Lunch, 2-Drink, 3-Both");
		byte orderType = scanner.nextByte();
		if (orderType <= 0 || orderType >= 4) {
			System.out.println("Bad selection");
			return;
		}

		Lunch lunch = null;
		if ((orderType & 1) == 1) {
			System.out.println("Please select cuisine");
			System.out.println("0-Polish, 1-Mexican, 2-Italian");
			byte cuisine = scanner.nextByte();
			if (cuisine <= -1 || cuisine >= 3) {
				System.out.println("Bad selection");
				return;
			}

			System.out.println("Please select meal");
			for (int i = 0; i < meals[cuisine].size(); ++i) {
				System.out.println(i + " - " + meals[cuisine].get(i));
			}
			int mealId = scanner.nextInt();
			if (mealId <= -1 || mealId >= meals[cuisine].size()) {
				System.out.println("Bad selection");
				return;
			}

			System.out.println("Please select dessert");
			for (int i = 0; i < desserts.size(); ++i) {
				System.out.println(i + " - " + desserts.get(i));
			}
			int dessertId = scanner.nextInt();
			if (dessertId <= -1 || dessertId >= desserts.size()) {
				System.out.println("Bad selection");
				return;
			}
			
			lunch = new Lunch(meals[cuisine].get(mealId), desserts.get(dessertId));
		}

		Drink drink = null;
		if ((orderType & 2) == 2) {
			System.out.println("Please select drink");
			for (int i = 0; i < drinks.size(); ++i) {
				System.out.println(i + " - " + drinks.get(i));
			}
			int drinkId = scanner.nextInt();
			if (drinkId <= -1 || drinkId >= drinks.size()) {
				System.out.println("Bad selection");
				return;
			}
			
			drink = (Drink) drinks.get(drinkId).clone();

			System.out.println("ice cubes?");
			System.out.println("0-No, 1-Yes");
			if (scanner.nextByte() == 1) {
				drink.addIceCubes();
			}

			System.out.println("lemon?");
			System.out.println("0-No, 1-Yes");
			if (scanner.nextByte() == 1) {
				drink.addLemon();
			}
		}

		Order order = new Order(lunch, drink);
		System.out.println("your order: " + order.getOrderInfo() + " ;price: " + order.getPrice());

	}
}
