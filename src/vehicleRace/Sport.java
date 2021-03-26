package vehicleRace;

public class Sport extends Vehicle 
{
	public Sport(String userDriverName, double userTravelDistance)
	{
		//user-set values
		super(userDriverName, userTravelDistance);
		
		//Sedan static values
		setMaxSpeed(110);
		setAccelRate(5);
		setMaxFuel(10);
		setTurnSpeed(getMaxSpeed()*0.50); //Sport overrides default turning speed
		
		//static starter values
		setCurrentSpeed(0); //speed starts at zero
		setCurrentFuel(getMaxFuel()); //Vehicle starts with full tank
		setDistanceTraveled(0); //Vehicle distanceTraveled starts at zero
		setLogbook("Log Start\n");
	}
}
