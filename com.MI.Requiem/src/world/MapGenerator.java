package world;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import core.Driver;
import runtime.Handler;
import utility.LoadingScreen;
import utility.NoiseGenerator;

/**
 * tools for generating a map file
 * @author Pascal
 *
 */
public class MapGenerator {

	/**
	 * generates the tile setup for a map
	 * @param h - the gamehandler
	 * @param name - the name for the world
	 * @throws IOException
	 */
	public static void generateMap(Handler h, String name) throws IOException {

		// LOADING SCREEN START
		LoadingScreen load = new LoadingScreen(World.maxChunks * World.maxChunks);
		load.displayText("Shaping Caverns");

		World w = h.getWorld();
		
		Random rng = new Random();

		File f1 = new File(Driver.saveDir + "saves/" + name + "/world.dat");
		f1.delete();
		f1.createNewFile();
		FileWriter file1 = new FileWriter(f1);
		BufferedWriter write = new BufferedWriter(file1);

		File f2 = new File(Driver.saveDir + "saves/" + name + "/map.dat");
		f2.delete();
		f2.createNewFile();
		FileWriter file2 = new FileWriter(f2);
		BufferedWriter writeMap = new BufferedWriter(file2);

		File f3 = new File(Driver.saveDir + "saves/" + name + "/data.dat");
		f3.delete();
		f3.createNewFile();
		FileWriter file3 = new FileWriter(f3);
		BufferedWriter writeData = new BufferedWriter(file3);

		NoiseGenerator noise = new NoiseGenerator(rng);
		noise.enableTesselation(World.maxChunks * Chunk.chunkDim, 256);
		NoiseGenerator ore = new NoiseGenerator(new Random(rng.nextLong()));
		NoiseGenerator texture = new NoiseGenerator(new Random(rng.nextLong()));

		for (int j = 0; j < World.maxChunks; j++) { // Primary loop establishes various densities of things like water
													// within each cell
			for (int i = 0; i < World.maxChunks; i++) {

				for (int y = 0; y < Chunk.chunkDim; y++) {
					for (int x = 0; x < Chunk.chunkDim; x++) {

						int xf = i * Chunk.chunkDim + x;
						int yf = j * Chunk.chunkDim + y;
						double mod = noise.fractalNoise(xf, yf, 8, 256 / (World.maxChunks * Chunk.chunkDim), 2);
						double modOre = ore.fractalNoise(xf, yf, 8, 64, 2);
						double tex = texture.fractalNoise(xf, yf, 8, 64, 2);

						int id = 0;
						int mapID = -1;

						if (mod > 0.50) {
							if (noise.tesselation(xf, yf) % 2 == 0) {
								id = 1;
								if (modOre > 0.5)
									mapID = 7;
								if (tex > 0.5)
									id = 9;
							} else {
								id = 6;
								if (modOre > 0.5)
									mapID = 7;
							}
						} else {
							mod = noise.fractalNoise(xf, yf, 8, 256 / (World.maxChunks), 2);
							if (mod > 0.50) {
								if (noise.tesselation(xf, yf) % 2 == 0) {
									id = 1;
									if (modOre > 0.5)
										mapID = 7;
									if (tex > 0.5)
										id = 9;
								} else {
									id = 6;
									if (modOre > 0.5)
										mapID = 7;
								}
							} else {
								if (noise.tesselation(xf, yf) % 2 == 0)
									id = 0;
								else
									id = 5;

							}
						}

						write.write(id + " ");
						writeMap.write(mapID + " ");

					}
				}
				write.newLine();
				writeMap.newLine();
				load.increment(1);
			}
		}
		load.displayText("Placing Player");

		boolean spawnFound = false;
		int spawnX = 0, spawnY = 0;
		while (!spawnFound) {
			spawnX = rng.nextInt(World.maxChunks * Chunk.chunkDim);
			spawnY = rng.nextInt(World.maxChunks * Chunk.chunkDim);
			switch (w.getUnloadedTileID(spawnX, spawnY)) {
			case 0:
				spawnFound = true;
				break;
			case 5:
				spawnFound = true;
				break;
			default:
				break;
			}

		}

		writeData.write(spawnX + " " + spawnY);
		writeData.newLine();

		write.close();
		writeMap.close();
		writeData.close();

		load.close();
	}

}
