package crafting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.Player;
import item.Inventory;
import utility.Loader;

/**
 * collection of static utility methods for use with crafting
 * @author Pascal
 *
 */
public class Craft {

	/**
	 * gets a list of recipes the player could craft at the current moment
	 * @param p - the player whos inventory to scan
	 * @param n - the inventory to scan
	 * @returns a list of all recipies the player could make
	 */
	public static ArrayList<Recipe> getRecipes(Player p, Inventory n) {
		ArrayList<Recipe> recipes = getAllRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (!recipes.get(i).qualify(p, n))  {
				recipes.remove(i);
				i--;
			}
		}
		return recipes;
	}
	
	/**
	 * turns a recipe string into code
	 * @param s - a recipe string representation
	 * @returns the recipe object detailed by the string
	 */
	public static Recipe parseRecipe(String s) {
		Recipe r = new Recipe();
		r.setVal(s);
		return r;
	}
	/**
	 * gets all the potential recipes in the game
	 * @returns a list containing all the ingame recipes
	 */
	private static ArrayList<Recipe> getAllRecipe() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		BufferedReader read = Loader.loadText("/data/recipe.dat");
		String s;
		try {
			while (!(s = read.readLine()).equals("!")) {
				recipes.add(parseRecipe(s));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recipes;
	}
	
	
}
