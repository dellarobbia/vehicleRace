package vehicleRace;

public class Track 
{
	//Properties
	private double trackLength; //length of track in feet
	private double[] turnCoordinates; //array containing "coordinates" of turns
	
	//Constants
	private final double turnRate = 1000; //number of feet of straight track before a turn
	
	//Getters & Setters
	public double getTrackLength()
	{
		return trackLength;
	}
	
	public void setTrackLength(double trackLength)
	{
		this.trackLength = trackLength;
	}
	
	public double[] getTurnCoordinates()
	{
		return turnCoordinates;
	}
	
	public void setTurnCoordinates(double[] turnCoordinates)
	{
		this.turnCoordinates = turnCoordinates;
	}
	
	//Constructor
	public Track(double userTrackLength)
	{
		setTrackLength(userTrackLength);
		setTurnCoordinates(determineTurns());
	}
	
	//Private methods
	//determines the coordinates for each turn (every 1000 feet by default)
	private double[] determineTurns()
	{
		int numberOfTurns = (int)(getTrackLength()/turnRate);
		double[] turnPlacement = new double[numberOfTurns];
		
		double turnPosition = 0;
		
		for(int counter = 1; counter <= numberOfTurns; ++counter)
		{
			turnPosition = (counter * turnRate);
			turnPlacement[counter - 1] = turnPosition;
		}
		
		return turnPlacement;
	}
}
