package vehicleRace;

public class Sedan extends Vehicle {

	public Sedan(String userDriverName, double userTravelDistance) 
	{
		//user-set values
		super(userDriverName, userTravelDistance);
		
		//Sedan static values
		setMaxSpeed(120);
		setAccelRate(5);
		setMaxFuel(14);
		
		//static starter values
		setCurrentSpeed(0); //speed starts at zero
		setTurnSpeed(getMaxSpeed()*0.25); //turn speed is set to 1/4 maxSpeed by default
		setCurrentFuel(getMaxFuel()); //Vehicle starts with full tank
		setDistanceTraveled(0); //Vehicle distanceTraveled starts at zero
		setLogbook("Log Start\n");
	}

}
