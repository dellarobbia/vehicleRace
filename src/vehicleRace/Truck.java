package vehicleRace;

public class Truck extends Vehicle 
{
	public Truck(String userDriverName, double userTravelDistance)
	{
		//user-set values
		super(userDriverName, userTravelDistance);
		
		//Sedan static values
		setMaxSpeed(130);
		setAccelRate(3);
		setMaxFuel(20);
		
		//static starter values
		setCurrentSpeed(0); //speed starts at zero
		setTurnSpeed(getMaxSpeed()*0.25); //turn speed is set to 1/4 maxSpeed by default
		setCurrentFuel(getMaxFuel()); //Vehicle starts with full tank
		setDistanceTraveled(0); //Vehicle distanceTraveled starts at zero
		setLogbook("Log Start\n");
	}
}
