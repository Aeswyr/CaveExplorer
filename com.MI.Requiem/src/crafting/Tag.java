package crafting;

/**
 * contains integer tags associated with different research, crafting table, and resource requirements
 * @author Pascal
 *
 */
public class Tag {
	// RESEARCH tags
	static int r = 0;
	public static final int RESEARCH_BASIC_CRAFT = r;
	public static final int RESEARCH_MOLDS = ++r;
	public static final int RESEARCH_MAX_ARRAY = ++r;
	
	// STATION tags
	static int s = 0;
	public static final int STATION_WORKTABLE = s;
	public static final int STATION_FORGE = ++s;
	public static final int STATION_ANVIL = ++s;
	public static final int STATION_MAX_ARRAY = ++s;
	
	// RESOURCE tags
	static int e = 0;
	public static final int RESOURCE_TISSUE = e;
	public static final int RESOURCE_SOIL = ++e;
	public static final int RESOURCE_METAL = ++e;
	public static final int RESOURCE_PHANTASM = ++e;
	public static final int RESOURCE_MINERAL = ++e;
	public static final int RESOURCE_GEM = ++e;
	public static final int RESOURCE_CORD = ++e;
	public static final int RESOURCE_CARVABLE = ++e;
	public static final int RESOURCE_CLOTH = ++e;
	public static final int RESOURCE_MAX_ARRAY = ++e;

}
