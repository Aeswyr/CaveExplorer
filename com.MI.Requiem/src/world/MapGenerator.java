package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import core.Driver;
import utility.NoiseGenerator;


public class MapGenerator {

	public static void generateMap() {
		Random rng = new Random();
		BufferedWriter write = null;
		try {
			File f = new File(Driver.saveDir + "saves/world/world.dat");
			f.delete();
			f.createNewFile();
			FileWriter file = new FileWriter(f);
			write = new BufferedWriter(file);
		} catch (IOException e) {
		}
		
		NoiseGenerator noise = new NoiseGenerator(rng);
		
		for (int j = 0; j < World.maxChunks; j++) { //Primary loop establishes various densities of things like water within each cell
			for (int i = 0; i < World.maxChunks; i++) {

				for (int y = 0; y < Chunk.chunkDim; y++) {
					for (int x = 0; x < Chunk.chunkDim; x++) {
						
						double mod = noise.fractalNoise(i * Chunk.chunkDim + x, j * Chunk.chunkDim + y, 8, 256 / (World.maxChunks * Chunk.chunkDim), 2);
						int id = 0;
						
						if (mod > 0.50) id = 1;
						else {
						mod = noise.fractalNoise(i * Chunk.chunkDim + x, j * Chunk.chunkDim + y, 8, 256 / (World.maxChunks), 2);
						if (mod > 0.50) id = 1;
						}
						
						try {
							write.write(id + " ");
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
