package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import core.Driver;


public class MapGenerator {

	public static void generateMap() {
		BufferedWriter write = null;
		try {
			File f = new File(Driver.saveDir + "saves/world/world.dat");
			f.delete();
			f.createNewFile();
			FileWriter file = new FileWriter(f);
			write = new BufferedWriter(file);
		} catch (IOException e) {
		}
		
		
		for (int j = 0; j < World.maxChunks; j++) { //Primary loop establishes various densities of things like water within each cell
			for (int i = 0; i < World.maxChunks; i++) {

				for (int y = 0; y < Chunk.chunkDim; y++) {
					for (int x = 0; x < Chunk.chunkDim; x++) {
						try {
							write.write("0 ");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								
					}
				}
				try {
					write.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
