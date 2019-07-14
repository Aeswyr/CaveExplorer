package crafting;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.Player;
import utility.Loader;

public class Craft {

	public static ArrayList<Recipe> getRecipes(Player p) {
		ArrayList<Recipe> recipes = getAllRecipe();
		
		return recipes;
	}
	
	public static Recipe parseRecipe(String s) {
		Recipe r = new Recipe();
		r.setVal(s);
		return r;
	}
	
	private static ArrayList<Recipe> getAllRecipe() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		BufferedReader read = Loader.loadText("/res/data/recipe.dat");
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
