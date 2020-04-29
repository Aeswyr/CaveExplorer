package world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.StringTokenizer;
import core.Engine;
import utility.Loader;
import utility.LoadingScreen;
import utility.NoiseGenerator;
import utility.Utils;

/**
 * tools for generating a map file
 * 
 * @author Pascal
 *
 */
public class MapGenerator {

	public static final int OFFSET = 34;

	/**
	 * generates the tile setup for a map
	 * 
	 * @param h    - the gamehandler
	 * @param name - the name for the world
	 * @throws IOException
	 */
	public static void generateMap(String name, World w) throws IOException {

		// LOADING SCREEN START
		LoadingScreen load = new LoadingScreen(2 * World.maxChunks * World.maxChunks);
		load.displayText("Shaping Caverns");

		Random rng = new Random();

		File f1 = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/temp0.dat");
		f1.delete();
		f1.createNewFile();
		FileOutputStream file1 = new FileOutputStream(f1);
		OutputStreamWriter write = new OutputStreamWriter(file1, StandardCharsets.UTF_8);

		File f2 = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/temp1.dat");
		f2.delete();
		f2.createNewFile();
		FileOutputStream file2 = new FileOutputStream(f2);
		OutputStreamWriter writeMap = new OutputStreamWriter(file2, StandardCharsets.UTF_8);

		File f3 = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/data.dat");
		f3.delete();
		f3.createNewFile();
		FileOutputStream file3 = new FileOutputStream(f3);
		OutputStreamWriter writeData = new OutputStreamWriter(file3, StandardCharsets.UTF_8);

		NoiseGenerator noise = new NoiseGenerator(rng);
		noise.enableTesselation(World.maxChunks * Chunk.chunkDim, 256);
		NoiseGenerator liquid = new NoiseGenerator(new Random(rng.nextLong()));
		NoiseGenerator ore = new NoiseGenerator(new Random(rng.nextLong()));
		NoiseGenerator texture = new NoiseGenerator(new Random(rng.nextLong()));

		for (int j = 0; j < World.maxChunks; j++) { // Primary loop establishes various densities of things like water
													// within each cell
			for (int i = 0; i < World.maxChunks; i++) {

				int[] id = detID(i, j, 0, 0, noise, texture, liquid);

				int id0 = id[0];
				int c0 = 0;
				int id1 = id[1];
				int c1 = 0;

				for (int y = 0; y < Chunk.chunkDim; y++) {
					for (int x = 0; x < Chunk.chunkDim; x++) {

						id = detID(i, j, x, y, noise, texture, liquid);

						if (id[0] != id0) {
							encode(id0, c0, write);
							c0 = 1;
							id0 = id[0];
						} else
							c0++;
						if (id[1] != id1) {
							encode(id1, c1, writeMap);
							c1 = 1;
							id1 = id[1];
						} else
							c1++;

					}
				}

				encode(id0, c0, write);
				encode(id1, c1, writeMap);

				write.write('\n');
				writeMap.write('\n');
				load.increment(1);
			}
		}
		write.close();
		writeMap.close();

		load.displayText("Seeding Ore");

		File f4 = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/world.dat");
		f4.delete();
		f4.createNewFile();
		FileOutputStream file4 = new FileOutputStream(f4);
		write = new OutputStreamWriter(file4, StandardCharsets.UTF_8);

		File f5 = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/map.dat");
		f5.delete();
		f5.createNewFile();
		FileOutputStream file5 = new FileOutputStream(f5);
		writeMap = new OutputStreamWriter(file5, StandardCharsets.UTF_8);

		int[][] dat;
		int[][] map;
		int pos;
		for (int j = 0; j < World.maxChunks; j++) {
			for (int i = 0; i < World.maxChunks; i++) {
				pos = j * World.maxChunks + i;
				dat = load(pos, Engine.ROOT_DIRECTORY + "saves/" + name + "/temp0.dat");
				map = load(pos, Engine.ROOT_DIRECTORY + "saves/" + name + "/temp1.dat");

				int o = (int) ((1 - rng.nextGaussian()) * 8);
				int oreID = 23;
				int x = rng.nextInt(Chunk.chunkDim - 2) + 1;
				int y = rng.nextInt(Chunk.chunkDim - 2) + 1;
				int xf = i * Chunk.chunkDim + x;
				int yf = j * Chunk.chunkDim + y;
				int bio = noise.tesselation(xf, yf) % World.MAX_BIOME;

				switch (bio) {
				case 0:
					switch (rng.nextInt(2)) {
					case 0:
						oreID = 23;
						break;
					case 1:
						oreID = 17;
						break;
					}
					break;
				case 1:
					switch (rng.nextInt(2)) {
					case 0:
						oreID = 13;
						break;
					case 1:
						oreID = 14;
						break;
					}
					break;

				}

				for (int k = 0; k < o; k++) {

					for (int p = -1; p < 2; p++) {
						for (int q = -1; q < 2; q++) {
							if (Tile.toTile(dat[x + q][y + p]).isBreakable() && rng.nextInt(2) == 0)
								map[x + q][y + p] = oreID;
						}
					}

				}

				save(dat, write);
				save(map, writeMap);

				load.increment(1);

			}

		}

		write.close();
		writeMap.close();
		f1.delete();
		f2.delete();

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
		writeData.write('\n');
		writeData.close();

		load.close();
	}

	private static StringBuilder b = new StringBuilder();

	/**
	 * Writes a single tile data point to the file with the format 'AB ' where A is
	 * the character that corresponds to the integer value of the tile id + 34, and
	 * B is the character that corresponds to the integer value of the count of the
	 * id.
	 * 
	 * @param id    - id of the tile to encode
	 * @param count - the number of that tile that appear consecutively
	 * @param write - the Output stream to write with
	 */
	private static void encode(int id, int count, OutputStreamWriter write) {
		b.delete(0, b.length());
		b.append((char) (id + OFFSET));
		b.append((char) (count + OFFSET));
		b.append(' ');
		try {
			write.write(b.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * loads a chunk based on the specified filepath
	 * 
	 * @param pos  - row of the chunk
	 * @param path - path to the chunk data file
	 * @returns a 2d array which stores tile data at each point
	 */
	private static int[][] load(int pos, String path) {
		int[][] chunk = new int[Chunk.chunkDim][Chunk.chunkDim];
		String line = null;

		try {
			BufferedReader read = Loader.loadTextFromFile(path, StandardCharsets.UTF_8);
			for (int i = 0; i < pos; i++) {
				read.readLine();
			}
			line = read.readLine();
			read.close();
		} catch (IOException e) {

		}

		StringTokenizer data = new StringTokenizer(line);

		int id0 = 0;
		int c0 = 0;

		String a0;

		for (int y = 0; y < Chunk.chunkDim; y++) {
			for (int x = 0; x < Chunk.chunkDim; x++) {
				if (c0 == 0) {
					a0 = data.nextToken();
					id0 = a0.charAt(0) - MapGenerator.OFFSET;
					c0 = a0.charAt(1) - MapGenerator.OFFSET;
				}
				chunk[x][y] = id0;
				c0--;
			}
		}

		return chunk;
	}

	/**
	 * saves a chunk back to the specified filepath
	 * 
	 * @param chunk - array data of the chunk
	 * @param pos   - row of the chunk
	 * @param path  - path to the chunk data file
	 */
	private static void save(int[][] chunk, int pos, String path) {

		StringBuilder c = new StringBuilder();

		int id0 = chunk[0][0];
		int c0 = 0;

		for (int y = 0; y < Chunk.chunkDim; y++) {
			for (int x = 0; x < Chunk.chunkDim; x++) {
				if (chunk[x][y] == id0)
					c0++;
				else {
					c.append((char) (id0 + MapGenerator.OFFSET));
					c.append((char) (c0 + MapGenerator.OFFSET));
					c.append(' ');
					c0 = 1;
					id0 = chunk[x][y];
				}
			}
		}

		c.append((char) (id0 + MapGenerator.OFFSET));
		c.append((char) (c0 + MapGenerator.OFFSET));

		Utils.editText(c.toString(), pos, path);
	}

	/**
	 * saves a chunk using a specified filewriter
	 * 
	 * @param chunk - array data of the chunk
	 * @param write - the filewriter to save with
	 */
	private static void save(int[][] chunk, OutputStreamWriter write) {

		StringBuilder c = new StringBuilder();

		int id0 = chunk[0][0];
		int c0 = 0;

		for (int y = 0; y < Chunk.chunkDim; y++) {
			for (int x = 0; x < Chunk.chunkDim; x++) {
				if (chunk[x][y] == id0)
					c0++;
				else {
					c.append((char) (id0 + MapGenerator.OFFSET));
					c.append((char) (c0 + MapGenerator.OFFSET));
					c.append(' ');
					c0 = 1;
					id0 = chunk[x][y];
				}
			}
		}

		c.append((char) (id0 + MapGenerator.OFFSET));
		c.append((char) (c0 + MapGenerator.OFFSET));

		try {
			write.write(c.toString());
			write.write('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Calculates the ids for both the world map and the overlay map
	 * 
	 * @param i       - chunk x coordinate
	 * @param j       - chunk y coordinate
	 * @param x       - tile x coordinate (chunk offset)
	 * @param y       - tile y coordinate (chunk offset)
	 * @param noise   - noise generator for map shape
	 * @param texture - secondary noise generator for variance and texture
	 * @param liquid  - noise generator for generating pools of lava or water
	 * @returns an array containing the world map id at position 0, and the overlay
	 *          map id at position 1
	 */
	private static int[] detID(int i, int j, int x, int y, NoiseGenerator noise, NoiseGenerator texture,
			NoiseGenerator liquid) {
		int xf = i * Chunk.chunkDim + x;
		int yf = j * Chunk.chunkDim + y;
		double mod = noise.fractalNoise(xf, yf, 8, 256 / (World.maxChunks * Chunk.chunkDim), 2);
		double tex = texture.fractalNoise(xf, yf, 8, 64, 2);
		int biome = noise.tesselation(xf, yf) % World.MAX_BIOME;

		int id = 0;
		int mapID = -1;

		if (mod > 0.50) {
			if (biome == World.BIOME_EARTHEN) {
				id = 1;
				if (tex > 0.5)
					id = 9;
			} else if (biome == World.BIOME_LIMESTONE) {
				id = 6;
			} else if (biome == World.BIOME_SEA) {
				id = 28;
			}
		} else {
			mod = noise.fractalNoise(xf, yf, 8, 256 / (World.maxChunks), 2);
			if (mod > 0.50) {
				if (biome == World.BIOME_EARTHEN) {
					id = 1;
					if (tex > 0.5)
						id = 9;
				} else if (biome == World.BIOME_LIMESTONE) {
					id = 6;
				} else if (biome == World.BIOME_SEA) {
					id = 7;
				}
			} else {
				if (biome == World.BIOME_EARTHEN)
					id = 0;
				else if (biome == World.BIOME_LIMESTONE)
					id = 5;
				else if (biome == World.BIOME_SEA)
					id = 27;
			}
		}
		if (liquid.fractalNoise(xf, yf, 8, 256 / (World.maxChunks * Chunk.chunkDim), 2) > 0.50
				&& biome == World.BIOME_SEA)
			id = 7;
		int[] r = { id, mapID };
		return r;
	}

}
