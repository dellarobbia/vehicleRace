package vehicleRace;

public class Motorbike extends Vehicle 
{
	public Motorbike(String userDriverName, double userTravelDistance)
	{
		//user-set values
		super(userDriverName, userTravelDistance);
		
		//Sedan static values
		setMaxSpeed(100);
		setAccelRate(10);
		setMaxFuel(5);
		setTurnSpeed(getMaxSpeed()*0.50); //Sport overrides default turning speed
		
		//static starter values
		setCurrentSpeed(0); //speed starts at zero
		setCurrentFuel(getMaxFuel()); //Vehicle starts with full tank
		setDistanceTraveled(0); //Vehicle distanceTraveled starts at zero
		setLogbook("Log Start\n");
	}
}
