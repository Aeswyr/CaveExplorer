package crafting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.Player;
import utility.Loader;

public class Craft {

	/**
	 * gets a list of recipes the player could craft at the current moment
	 * @param p
	 * @return
	 */
	public static ArrayList<Recipe> getRecipes(Player p) {
		ArrayList<Recipe> recipes = getAllRecipe();
		for (int i = 0; i < recipes.size(); i++) {
			if (!recipes.get(i).qualify(p))  {
				recipes.remove(i);
				i--;
			}
		}
		return recipes;
	}
	
	/**
	 * turns a recipe string into code
	 * @param s
	 * @return
	 */
	public static Recipe parseRecipe(String s) {
		Recipe r = new Recipe();
		r.setVal(s);
		return r;
	}
	/**
	 * gets all the potential recipes in the game
	 * @return
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
