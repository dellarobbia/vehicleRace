package vehicleRace;

import java.io.*;
import java.util.ArrayList;

public class Race 
{
	//Properties
	private Vehicle[] racers;
	private Track raceTrack;
	private double timer;
	private ArrayList<Vehicle> activeRacers;
	private Boolean raceFinished;
	private ArrayList<Vehicle> racerPosition;
	
	//Constants
	private final double maxTime = 120000; //max time allowed for race before forcing DNF
	
	//Getters & Setters
	public Vehicle[] getRacers() 
	{
		return racers;
	}

	public void setRacers(Vehicle[] racers) 
	{
		this.racers = racers;
	}

	public Track getRaceTrack() 
	{
		return raceTrack;
	}

	public void setRaceTrack(Track raceTrack) 
	{
		this.raceTrack = raceTrack;
	}
	
	public double getTimer()
	{
		return timer;
	}
	
	public void setTimer(double timer)
	{
		this.timer = timer;
	}
	
	public Boolean isRaceFinished()
	{
		return raceFinished;
	}
	
	public void setRaceFinished(Boolean raceFinished)
	{
		this.raceFinished = raceFinished;
	}
	
	public ArrayList<Vehicle> getActiveRacers()
	{
		return activeRacers;
	}
	
	public void setActiveRacers(ArrayList<Vehicle> activeRacers)
	{
		this.activeRacers = activeRacers;
	}
	
	public ArrayList<Vehicle> getRacerPosition()
	{
		return racerPosition;
	}
	
	public void setRacerPosition(ArrayList<Vehicle> racerPosition)
	{
		this.racerPosition = racerPosition;
	}

	
	//Constructor
	public Race(Vehicle[] userRacers, Track userRaceTrack)
	{
		//user-set properties
		setRacers(userRacers);
		setRaceTrack(userRaceTrack);
		//default initialized properties
		setTimer(0); //race timer always starts at zero
		setRaceFinished(false); //the race starts not finished
	}
	
	//Methods
	//starts each Vehicle's thread
	public void startRace()
	{
		int numOfRacers = getRacers().length;
		Thread[] raceThreads = new Thread[numOfRacers];
		
		//set up each racer as a thread
		for(int counter = 1; counter <= numOfRacers; ++counter)
		{
			raceThreads[counter - 1] = new Thread(getRacers()[counter - 1]);
			raceThreads[counter - 1].setDaemon(false);
		}
		
		//run each thread
		for(int counter = 1; counter <= numOfRacers; ++counter)
		{
			raceThreads[counter - 1].start();
		}
	}
	
	//montior the race in progress
	public void raceMonitor()
	{
		makeActiveRacers();
		setRacerPosition(new ArrayList<Vehicle>());
		
		while(isRaceFinished() == false)
		{
			//cutting for time
			//increment timer
			//setTimer(getTimer() + 1);
			//check if there are any finished racers
			setRaceFinished(checkFinished());
		}
		
		endRace();
		System.out.print(raceReport());
	}
	
	private void makeActiveRacers()
	{
		setActiveRacers(new ArrayList<Vehicle>());
		for(int counter = 1; counter <= getRacers().length; ++counter)
		{
			activeRacers.add(getRacers()[counter - 1]);
		}
	}
	
	//checks each racer to see if it finished
	private Boolean checkFinished()
	{
		Boolean allFinished = false;
		int counter = 1;
		
		if(activeRacers.size() > 0)
		{
			while(activeRacers.size() > 0 && counter <= activeRacers.size())
			{
				if(activeRacers.get(counter - 1).getDistanceTraveled() >= raceTrack.getTrackLength())
				{
					racerPosition.add(activeRacers.get(counter - 1));
					activeRacers.remove(counter - 1);
					counter -= 1;
				}
				if(activeRacers.size() == 0)
					return true;
				++counter;
			}
		}
		if(activeRacers.size() == 0)
			allFinished = true;
		//if all racers return finished, this should return true
		return allFinished;
	}
	
	public void endRace()
	{
		for(int counter = 1; counter <= getRacers().length; ++counter)
			racers[counter - 1].stop();
		postLog();
	}
	
	private void postLog()
	{
		try 
		{
			FileWriter logWriter = new FileWriter("RaceLog.txt");
			logWriter.write(raceReport());
			logWriter.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String raceReport()
	{
		String report;
		
		report = "The results are in!\n";
		
		for(int counter = 1; counter <= racerPosition.size(); ++counter)
		{
			report = report + counter + ": " + racerPosition.get(counter - 1).getDriverName() + "\n";
		}
		
		return report;
	}
	
	//check if any racers need to turn
	//cut for time
	private void turnRacers()
	{
		int numOfRacers = getRacers().length;
		int numOfTurns = raceTrack.getTurnCoordinates().length;
		
		Vehicle checkRacer;
		double[] checkCoordinates = new double[2];
		
		//loop through each racer to determine if they need to turn
		for(int racerCounter = 1; racerCounter <= numOfRacers; ++racerCounter)
		{
			checkRacer = racers[racerCounter - 1];
			for(int turnCounter = 1; turnCounter <= numOfTurns; ++turnCounter)
			{
				checkCoordinates[0] = raceTrack.getTurnCoordinates()[turnCounter - 1];
				checkCoordinates[1] = raceTrack.getTurnCoordinates()[turnCounter];
				if((checkRacer.getDistanceTraveled() >= checkCoordinates[0]) && (checkRacer.getDistanceTraveled() <= checkCoordinates[1]))
				{
					checkRacer.turn();
				}
			}
		}
	}
	
	
}
