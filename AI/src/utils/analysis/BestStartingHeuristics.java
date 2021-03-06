package utils.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Wrapper around collected data on the best starting heuristics
 * to start training Alpha-Beta agents with.
 * 
 * TODO could probably make sure we only ever load the data once...
 *
 * @author Dennis Soemers
 */
public class BestStartingHeuristics
{
	
	//-------------------------------------------------------------------------
	
	/** Map of entries (mapping from cleaned game names to entries of data) */
	private final Map<String, Entry> entries;
	
	//-------------------------------------------------------------------------
	
	/**
	 * @return Loads and returns the analysed data as stored so far.
	 */
	public static BestStartingHeuristics loadData()
	{
		final Map<String, Entry> entries = new HashMap<String, Entry>();
		final File file = new File("../AI/resources/Analysis/BestStartingHeuristics.csv");
		
		try (final BufferedReader reader = new BufferedReader(new FileReader(file)))
		{
			reader.readLine();	// headers line, which we don't use
			
			for (String line; (line = reader.readLine()) != null; /**/)
			{
				final String[] lineSplit = line.split(Pattern.quote(","));
				entries.put(lineSplit[0], new Entry
						(
							lineSplit[0],
							lineSplit[1],
							Float.parseFloat(lineSplit[2]),
							Long.parseLong(lineSplit[3])
						));
			}
		} 
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		
		return new BestStartingHeuristics(entries);
	}
	
	/**
	 * Constructor
	 * @param entries
	 */
	private BestStartingHeuristics(final Map<String, Entry> entries)
	{
		this.entries = entries;
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * @param cleanGameName
	 * @return Stored entry for given game name
	 */
	public Entry getEntry(final String cleanGameName)
	{
		return entries.get(cleanGameName);
	}
	
	/**
	 * @return Set of all game keys in our file
	 */
	public Set<String> keySet()
	{
		return entries.keySet();
	}
	
	//-------------------------------------------------------------------------
	
	/**
	 * An entry with data for one game in our collected data.
	 *
	 * @author Dennis Soemers
	 */
	public static class Entry
	{
		
		/** Name of game for which we stored data (cleaned for filepath-friendliness) */
		private final String cleanGameName;
		
		/** String description of top starting heuristic */
		private final String topHeuristic;
		
		/** Win percentage of the Alpha-Beta agent with the top starting heuristic */
		private final float topScore;
		
		/** 
		 * Time (in milliseconds since midnight, January 1, 1970 UTC) when we last 
		 * analysed Alpha-Beta agents for this game 
		 */
		private final long lastEvaluated;
		
		/**
		 * Constructor
		 * @param cleanGameName
		 * @param topHeuristic
		 * @param topScore
		 * @param lastEvaluated
		 */
		protected Entry
		(
			final String cleanGameName, 
			final String topHeuristic, 
			final float topScore, 
			final long lastEvaluated
		)
		{
			this.cleanGameName = cleanGameName;
			this.topHeuristic = topHeuristic;
			this.topScore = topScore;
			this.lastEvaluated = lastEvaluated;
		}
		
		/**
		 * @return Name of game for which we stored data (cleaned for filepath-friendliness)
		 */
		public String cleanGameName()
		{
			return cleanGameName;
		}
		
		/**
		 * @return String description of top starting heuristic
		 */
		public String topHeuristic()
		{
			return topHeuristic;
		}
		
		/**
		 * @return Win percentage of the Alpha-Beta agent with the top starting heuristic
		 */
		public float topScore()
		{
			return topScore;
		}
		
		/**
		 * @return Time (in milliseconds since midnight, January 1, 1970 UTC) when we last 
		 * 	analysed Alpha-Beta agents for this game 
		 */
		public long lastEvaluated()
		{
			return lastEvaluated;
		}
		
	}
	
	//-------------------------------------------------------------------------

}
