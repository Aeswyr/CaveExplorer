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
		Random rng = new Random(12); // for temp id 12
		BufferedWriter write = null;
		try {
			File f = new File(Driver.saveDir + "saves/world/world.dat");
			f.delete();
			f.createNewFile();
			FileWriter file = new FileWriter(f);
			write = new BufferedWriter(file);
		} catch (IOException e) {
		}
		BufferedWriter writeMap = null;
		try {
			File f = new File(Driver.saveDir + "saves/world/map.dat");
			f.delete();
			f.createNewFile();
			FileWriter file = new FileWriter(f);
			writeMap = new BufferedWriter(file);
		} catch (IOException e) {
		}

		NoiseGenerator noise = new NoiseGenerator(rng);
		noise.enableTesselation(World.maxChunks * Chunk.chunkDim, 256);
		NoiseGenerator ore = new NoiseGenerator(new Random(rng.nextLong()));

		for (int j = 0; j < World.maxChunks; j++) { // Primary loop establishes various densities of things like water
													// within each cell
			for (int i = 0; i < World.maxChunks; i++) {

				for (int y = 0; y < Chunk.chunkDim; y++) {
					for (int x = 0; x < Chunk.chunkDim; x++) {

						int xf = i * Chunk.chunkDim + x;
						int yf = j * Chunk.chunkDim + y;
						double mod = noise.fractalNoise(xf, yf, 8, 256 / (World.maxChunks * Chunk.chunkDim), 2);
						double modOre = ore.fractalNoise(xf, yf, 8, 64, 2);

						int id = 0;
						int mapID = -1;

						if (mod > 0.50) {
							if (noise.tesselation(xf, yf) % 2 == 0) {
								id = 1;
								if (modOre > 0.5)
									mapID = 7;
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
						
						try {
							write.write(id + " ");
							writeMap.write(mapID + " ");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
				try {
					write.newLine();
					writeMap.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
