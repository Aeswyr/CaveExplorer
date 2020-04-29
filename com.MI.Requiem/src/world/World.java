package world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import core.Engine;
import entities.Ooze;
import entities.Player;
import entities.WillowWisp;
import entity.Entity;
import entity.Entity_KS;
import gfx.DrawGraphics;
import map.WorldMap_KS;
import runtime.Handler;
import utility.Loader;
import utility.LoadingScreen;

/**
 * Manages the world, chunks, and entities
 * 
 * @author Pascal
 *
 */
public class World extends WorldMap_KS {

	private static Random rng = new Random();

	ChunkLoader chunkLoader;
	ArrayList<Chunk> chunks;
	Chunk currChunk;
	public static int maxChunks;
	public static final int MAP_BASE = 0;
	public static final int MAP_OVERLAY = 1;
	int biome;

	Player player;

	String loadedWorld = null;

	public World() {
		super(2);
		maxChunks = 64;

		chunkLoader = new ChunkLoader();
		chunks = chunkLoader.getActive();
		currChunk = new Chunk(-1, -1);

	}

	/**
	 * loads a selected world file based on it's name and begins the chunk loader
	 * 
	 * @param name - name of the world file to load
	 */
	public void init(String name) {
		File dir = new File(Engine.ROOT_DIRECTORY + "saves/" + name + "/");
		loadedWorld = name;
		boolean d = dir.mkdir();
		try {
			if (d)
				MapGenerator.generateMap(name, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chunkLoader.start();
		BufferedReader info = Loader.loadTextFromFile(Engine.ROOT_DIRECTORY + "saves/" + name + "/data.dat");
		if (d) {
			try {
				player = new Player();
				Handler.getEntityManager().addEntity(player);
				String[] playerCoord = info.readLine().split(" ");
				player.setX(Integer.parseInt(playerCoord[0]) * Tile.TILE_SIZE);
				player.setY(Integer.parseInt(playerCoord[1]) * Tile.TILE_SIZE);
				Handler.getCamera().centerOnEntity(player);
				info.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			loadAllEntities(Engine.ROOT_DIRECTORY + "saves/" + name + "/data.dat");
			ArrayList<Entity_KS> list = Handler.getEntityManager().getEntities();
			for (Entity_KS e : list) {
				if (e instanceof Player) {
					player = (Player) e;
					Handler.getCamera().centerOnEntity(player);
					System.out.println("player found");
					break;
				}
			}
		}
	}

	/**
	 * draws loaded chunks to the screen, as well as all entities, entity UI, and
	 * lights
	 * 
	 * @param g - the DrawGraphics component to draw on
	 */
	public void render(DrawGraphics g) {

		synchronized (chunks) {
			for (int i = 0; i < chunks.size(); i++) {
				chunks.get(i).render(g);
			}
		}
	}

	int[] biomeData;
	int spawnTick;

	/**
	 * updates world data: controls biome management, mob spawning, and entity +
	 * chunk updating
	 */
	public void update() {
		spawnTick++;
		biomeData = new int[Tile.TILE_MAX];
		synchronized (chunks) {
			for (int i = 0; i < chunks.size(); i++) {
				chunks.get(i).update(biomeData);
			}
		}

		if (biomeData[0] + biomeData[1] + biomeData[8] + biomeData[9] > 400) {
			biome = 0;
		} else if (biomeData[5] + biomeData[6] > 400) {
			biome = 1;
		} else {
			biome = 0;
		}
		updateChunks();

		if (spawnTick % 600 == 0) {
			switch (biome) {
			case 0:
				if (rng.nextDouble() < 0.3) {
					int x = rng.nextInt(256) + player.getX();
					int y = rng.nextInt(256) + player.getY();
					int attempts = 0;
					while ((getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()) && attempts < 10) {
						attempts++;
						x = rng.nextInt(256) + player.getX();
						y = rng.nextInt(256) + player.getY();
					}

					if (attempts != 10) {
						WillowWisp u = new WillowWisp();
						u.setX(x);
						u.setY(y);
						Handler.getEntityManager().addEntity(u);
						System.out.println("wisp spawned");
					}
				}
				break;
			case 1:
				if (rng.nextDouble() < 0.3) {
					int x = rng.nextInt(256) + player.getX();
					int y = rng.nextInt(256) + player.getY();
					int attempts = 0;
					while ((getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()
							|| getTile(x, y, World.MAP_BASE).getCollidable()) && attempts < 10) {
						attempts++;
						x = rng.nextInt(256) + player.getX();
						y = rng.nextInt(256) + player.getY();
					}

					if (attempts != 10) {
						Ooze u = new Ooze();
						u.setX(x);
						u.setY(y);
						Handler.getEntityManager().addEntity(u);
						System.out.println("Ooze spawned");
					}
				}
				break;
			}
		}
	}

	/**
	 * updates loaded chunks based on the player movement. If the player enters a
	 * new chunk, this contacts the chunkloader and collects a new list of chunks,
	 * unloads the old ones, and loads the new ones
	 * 
	 */
	private void updateChunks() {

		int pcx = player.getChunkX();
		int pcy = player.getChunkY();

		if (currChunk.getX() != pcx || currChunk.getY() != pcy) {
			currChunk = new Chunk(pcx, pcy);

			int cx = currChunk.getX();
			int cy = currChunk.getY();

			ArrayList<Chunk> test = new ArrayList<Chunk>();
			for (int x = -2; x < 3; x++) {
				for (int y = -2; y < 3; y++) {
					if (cx + x >= 0 && cx < maxChunks && cy + y >= 0 && cy < maxChunks)
						test.add(new Chunk(cx + x, cy + y));
				}
			}

			for (int i = 0; i < chunks.size(); i++) {
				if (!test.contains(chunks.get(i)))
					chunkLoader.unload(chunks.get(i));
			}
			for (int i = 0; i < test.size(); i++) {
				if (!chunks.contains(test.get(i)))
					chunkLoader.load(test.get(i));
			}

		}
		synchronized (chunks) {
			chunks.clear();
			chunks.addAll(chunkLoader.getActive());
		}
	}

	/**
	 * returns the tile at the given position
	 * 
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
	 */
	public Tile getTile(int x, int y, int map) {

		if (map == MAP_BASE) {
			int id = -1;
			for (int i = 0; i < chunks.size(); i++) {
				if (chunks.get(i) != null) {
					int pos = chunks.get(i).tileAt(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
					if (pos != -1)
						id = pos;
				}
			}
			if (id == -1)
				return Tile.toTile(1);
			return Tile.toTile(id);
		} else if (map == MAP_OVERLAY) {
			int id = -1;
			for (int i = 0; i < chunks.size(); i++) {
				if (chunks.get(i) != null) {
					int pos = chunks.get(i).overlayAt(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
					if (pos >= 0)
						id = pos;
					if (pos != -2)
						break;
				}
			}
			if (id == -1 || id == -2)
				return null;
			return Tile.toTile(id);
		}
		return null;
	}

	/**
	 * returns the tile id at the given position
	 * 
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
	 */
	public int getTileID(int x, int y, int map) {
		if (map == MAP_BASE) {
			int id = -1;
			for (int i = 0; i < chunks.size(); i++) {
				if (chunks.get(i) != null) {
					int pos = chunks.get(i).tileAt(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
					if (pos != -1)
						id = pos;
				}
			}
			if (id == -1)
				return 0;
			return id;
		} else if (map == MAP_OVERLAY) {
			int id = -2;
			for (int i = 0; i < chunks.size(); i++) {
				if (chunks.get(i) != null) {
					int pos = chunks.get(i).overlayAt(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
					if (pos > -2) {
						id = pos;
					}
				}
			}
			return id;
		}
		return -2;
	}

	/**
	 * returns the tile id at the given position even if the chunk is unloaded
	 * 
	 * @param x - the x coordinate of the tile (tile position)
	 * @param y - the y coordinate of the tile (tile position)
	 */
	public int getUnloadedTileID(int x, int y) {
		int id = -1;
		int chunkx = x / Chunk.chunkDim;
		int chunky = y / Chunk.chunkDim;
		String line = null;
		BufferedReader read = Loader.loadTextFromFile(Engine.ROOT_DIRECTORY + "saves/" + loadedWorld + "/world.dat",
				StandardCharsets.UTF_8);
		int find = chunky * World.maxChunks + chunkx;

		try {
			for (int i = 0; i < find; i++) {
				read.readLine();
			}
			line = read.readLine();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringTokenizer data = new StringTokenizer(line);
		int c = (y % Chunk.chunkDim) * Chunk.chunkDim + x % Chunk.chunkDim;
		String a;
		while (c >= 0) {
			a = data.nextToken();
			id = a.charAt(0);
			c -= a.charAt(1) - MapGenerator.OFFSET;
		}
		return id - MapGenerator.OFFSET;
	}

	/**
	 * replaces the tile at the given coordinate with a specified one
	 * 
	 * @param x  - tile x coordinate (in pixels)
	 * @param y  - tile y coordinate (in pixels)
	 * @param id - id of the desired tile
	 */
	public void setTile(int x, int y, int id, int map) {
		if (map == 0) {
			for (int i = 0; i < chunks.size(); i++)
				chunks.get(i).setTile(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE, id);
		} else if (map == 1) {
			for (int i = 0; i < chunks.size(); i++)
				chunks.get(i).setOverlay(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE, id);
		}
	}

	/**
	 * unloads all currently loaded chunks
	 */
	public void unloadWorld() {
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i).loaded)
				chunks.get(i).unload();
		}
	}

	/**
	 * saves all entities within the world
	 */
	public void save() {
		saveAllEntities(Engine.ROOT_DIRECTORY + "saves/" + loadedWorld + "/data.dat");
	}

	/**
	 * @returns the id of the biome the player is currently located in
	 */
	public int getCurrentBiome() {
		return biome;
	}

	public static final int MAX_BIOME = 3;
	public static final int BIOME_EARTHEN = 0;
	public static final int BIOME_LIMESTONE = 1;
	public static final int BIOME_SEA = 2;

	/**
	 * @param biome - id of the desired biome name
	 * @returns the name of the biome associated with the numerical id
	 */
	public static String biomeToString(int biome) {
		switch (biome) {
		case 0:
			return "Earthen Caverns";
		case 1:
			return "Limestone Tunnels";
		case 2:
			return "Underground Sea";
		default:
			return "Hip and Fresh Land";
		}
	}

	/**
	 * saves all entities to a specified world file
	 * 
	 * @param path - the path to the specified world file
	 */
	public void saveAllEntities(String path) {
		LoadingScreen ls = new LoadingScreen(1);
		ls.displayText("Saving...");
		try {
			File f = new File(path);
			f.delete();
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			ObjectOutputStream stream = new ObjectOutputStream(fo);
			stream.writeObject(Handler.getEntityManager().getEntities());
			stream.close();
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ls.increment(1);
		ls.close();

	}

	/**
	 * Loads all entities into the world from the specified path
	 * 
	 * @param path - string path to the world data file
	 */
	@SuppressWarnings("unchecked")
	public void loadAllEntities(String path) {
		LoadingScreen ls = new LoadingScreen(2);
		ls.displayText("Loading...");
		ArrayList<Entity> entities = null;
		try {
			File f = new File(path);
			FileInputStream fo = new FileInputStream(f);
			ObjectInputStream stream = new ObjectInputStream(fo);
			entities = (ArrayList<Entity>) stream.readObject();
			stream.close();
			fo.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ls.increment(1);

		for (Entity e : entities) {
			e.load();
			Handler.getEntityManager().addEntity(e);
		}
		ls.increment(1);
		ls.close();
	}

	public Player getPlayer() {
		return player;
	}

}
