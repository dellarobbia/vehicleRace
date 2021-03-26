package vehicleRace;

import java.util.Scanner;

public class RaceStarter 
{

	public static void main(String[] args) 
	{
		Track raceTrack = makeTrack(new Scanner(System.in));
		Vehicle[] racers = makeRacers(new Scanner(System.in), raceTrack);
		
		Race newRace = new Race(racers, raceTrack);
		System.out.println("On your marks...");
		System.out.println("Get set...");
		System.out.println("Go!");
		newRace.startRace();
		System.out.println("Racing...");
		newRace.raceMonitor();

	}
	
	private static Vehicle[] makeRacers(Scanner userInput, Track raceTrack)
	{
		//prompt for number of racers
		int numOfRacers;
		System.out.println("How many drivers are racing?");
		numOfRacers = userInput.nextInt();
		
		//construct each racer
		String driverName;
		Vehicle[] newVehicles = new Vehicle[numOfRacers];

		for(int counter = 1; counter <= numOfRacers; ++counter)
		{
			//prompt for driverName
			System.out.println("What is the name of racer #" + counter + " ?");
			driverName = userInput.next();
			
			//pass driver name to the vehicle select method and construct the selected vehicle
			newVehicles[counter - 1] = vehicleSelection(new Scanner(System.in), driverName, raceTrack.getTrackLength());
		}
		
		return newVehicles;
	}
	
	private static Vehicle vehicleSelection(Scanner userInput, String driverName, double trackLength)
	{
		Boolean selectionMade = false;
		int selection = 0;
		//initialized to Sedan; hope it can overwrite?
		Vehicle selectedVehicle = new Sedan(driverName, trackLength);
		
		//loop until valid selection is made
		while(selectionMade == false)
		{
			//print selection menu
			System.out.println("Select the vehicle for this racer");
			System.out.println("1: Sedan");
			System.out.println("2: Sport Car");
			System.out.println("3: Truck");
			System.out.println("4: Motorcycle");
			
			selection = userInput.nextInt();
			
			//check selection to make sure its valid
			switch(selection)
			{
			case 1:
				selectedVehicle = new Sedan(driverName, trackLength);
				selectionMade = true;
				break;
			case 2:
				selectedVehicle = new Sport(driverName, trackLength);
				selectionMade = true;
				break;
			case 3:
				selectedVehicle = new Truck(driverName, trackLength);
				selectionMade = true;
				break;
			case 4:
				selectedVehicle = new Motorbike(driverName, trackLength);
				selectionMade = true;
				break;
			default:
				selectionMade = false;
				System.out.println("Please make a different selection.");
			}
		}
		return selectedVehicle;
	}
	
	private static Track makeTrack(Scanner userInput)
	{
		//default tracklength
		double trackLength = 4000;
		System.out.println("How long (in meters) is the race track?");
		trackLength = userInput.nextDouble();
		
		return new Track(trackLength);
	}
}
