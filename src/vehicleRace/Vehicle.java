package vehicleRace;

import java.io.*;

public abstract class Vehicle implements Runnable 
{
	//Static properties to be set by concrete subclasses
	private double maxSpeed; //sets a ceiling for currentSpeed
	private double accelRate; //determines how much currentSpeed increments
	private double maxFuel; //set a ceiling for currentFuel
	
	//Static properties determined by user
	private String driverName; //gives vehicle unique identifier
	private double travelDistance; //the total distance the Vehicle will travel
	
	//Flexible properties to be changed through method interaction
	private double currentSpeed; //Vehicle's current speed at a single point of time
	private double turnSpeed; //lowers currentSpeed when Vehicle encounters a turn
	private double currentFuel; //Vehicle's current fuel qty at a single point of time
	private double distanceTraveled; //total distance traveled by the Vehicle
	
	//Constants
	private final double fuelBurnRate = 0.01;
	
	//String to hold a running log of the Vehicle
	private String logbook;
	
	//Getters & Setters
	public String getDriverName() 
	{
		return driverName;
	}

	public void setDriverName(String driverName) 
	{
		this.driverName = driverName;
	}

	public double getMaxSpeed() 
	{
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) 
	{
		this.maxSpeed = maxSpeed;
	}

	public double getAccelRate() 
	{
		return accelRate;
	}

	public void setAccelRate(double accelRate) 
	{
		this.accelRate = accelRate;
	}

	public double getMaxFuel() 
	{
		return maxFuel;
	}

	public void setMaxFuel(double maxFuel) 
	{
		this.maxFuel = maxFuel;
	}
	
	public double getTravelDistance()
	{
		return travelDistance;
	}
	
	public void setTravelDistance(double travelDistance)
	{
		this.travelDistance = travelDistance;
	}

	public double getCurrentSpeed() 
	{
		return currentSpeed;
	}

	public void setCurrentSpeed(double currentSpeed) 
	{
		this.currentSpeed = currentSpeed;
	}

	public double getTurnSpeed() 
	{
		return turnSpeed;
	}

	public void setTurnSpeed(double turnSpeed) 
	{
		this.turnSpeed = turnSpeed;
	}

	public double getCurrentFuel() 
	{
		return currentFuel;
	}

	public void setCurrentFuel(double currentFuel) 
	{
		this.currentFuel = currentFuel;
	}

	public double getDistanceTraveled() 
	{
		return distanceTraveled;
	}

	public void setDistanceTraveled(double distanceTraveled) 
	{
		this.distanceTraveled = distanceTraveled;
	}

	public String getLogbook() 
	{
		return logbook;
	}

	public void setLogbook(String logbook) 
	{
		this.logbook = logbook;
	}
	
	//Constructor
	//all max values set by user
	public Vehicle (String userDriverName, double userMaxSpeed, double userAccelRate, double userMaxFuel,
			double userTravelDistance)
	{
		//user-set values
		setDriverName(userDriverName);
		setMaxSpeed(userMaxSpeed);
		setAccelRate(userAccelRate);
		setMaxFuel(userMaxFuel);
		setTravelDistance(userTravelDistance);
		
		//static starter values
		setCurrentSpeed(0); //speed starts at zero
		setTurnSpeed(getMaxSpeed()*0.25); //turn speed is set to 1/4 maxSpeed by default
		setCurrentFuel(getMaxFuel()); //Vehicle starts with full tank
		setDistanceTraveled(0); //Vehicle distanceTraveled starts at zero
		setLogbook("Log Start\n");
	}
	//max values are predetermined
	public Vehicle (String userDriverName, double userTravelDistance)
	{
		//user-set values
		setDriverName(userDriverName);
		setTravelDistance(userTravelDistance);
		
		//default static values
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

	//Start the thread
	@Override
	public void run() 
	{
		double currentDistance = getDistanceTraveled();
		while(getDistanceTraveled() <= getTravelDistance())
		{
			//increase distance based on currentSpeed
			currentDistance += getCurrentSpeed();
			setDistanceTraveled(currentDistance);
			//increase speed
			accelerate();
			//decrement fuel
			burnFuel();
			//log the state of this Vehicle
			writeLog();
			//thread should end once distanceTraveled meets or exceeds travelDistance
		}
		
		stop();
		writeLog("finished");
		postLog();
	}
	
	//Increment currentSpeed if it is less than maxSpeed
	public void accelerate()
	{
		//determine what the new speed would be
		double newSpeed = getCurrentSpeed() + getAccelRate();
		
		//if the newSpeed would be less than the max allowed, setCurrentSpeed to newSpeed
		if (newSpeed < getMaxSpeed())
		{
			setCurrentSpeed(newSpeed);
		}
		//if the newSpeed would exceed or equal the max allowed, seteCurrentSpeed to the maxSpeed
		else if(newSpeed >= getMaxSpeed())
		{
			setCurrentSpeed(getMaxSpeed());
		}
		
		
	}
	
	//Lower currentSpeed to match the Vehicle's turnSpeed
	public void turn()
	{
		//if the currentSpeed is higher than the turnSpeed, set the currentSpeed to turnSpeed
		if(getCurrentSpeed() >= getTurnSpeed())
		{
			setCurrentSpeed(getTurnSpeed());
		}
		
		//logs the turn
		writeLog("turn");
	}
	
	//Rest currentFuel to equal maxFuel; Vehicle sleeps during this process
	public void refuel()
	{
		double fuelTank = getCurrentFuel();
		while(fuelTank < getMaxFuel())
		{
			//sleep for 0.1 seconds while tank fills 0.1 gallons
			fuelTank += 0.1;
			try 
			{
				Thread.sleep(100);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		//transfer the fuelTank to the Vehicle
		setCurrentFuel(fuelTank);
		writeLog("refuel");
	}
	
	//Burn fuel relative to currentSpeed
	public void burnFuel()
	{
		//determine how much fuel gets burnt
		double fuelBurnt = fuelBurnRate * getCurrentSpeed();
		
		//calculate what the new fuel quantity will be after the burn
		double newFuel = getCurrentFuel() - fuelBurnt;
		
		//adjust the currentFuel as needed
		//if currentFuel would be <= 0, set it to zero and refuel
		if(newFuel <= 0)
		{
			setCurrentFuel(0);
			stop();
			refuel();
		}
		//if currentFuel is getting close to empty, adjust fuel then refuel
		else if(newFuel <= 0.25)
		{
			setCurrentFuel(newFuel);
			stop();
			refuel();
		}
		//if there are no problems, adjust the fuel level then continue driving
		else
		{
			setCurrentFuel(newFuel);
		}
	}
	
	//Set currentSpeed to zero
	public void stop()
	{
		setCurrentSpeed(0);
		writeLog("stop");
	}
	
	//Write the Vehicle's logbook
	//version with logType argument
	private void writeLog(String logType)
	{
		switch(logType)
		{
		case "refuel":
			logbook = logbook + driverName + " has refueled.\n";
			break;
		case "turn":
			logbook = logbook + driverName + " has executed a turn.\n";
			break;
		case "stop":
			logbook = logbook + driverName + " has stopped.\n";
			break;
		case "finished":
			logbook = logbook + driverName + " has finished driving after " + distanceTraveled + " feet.\n";
			break;
		default:
			logbook = logbook + driverName + " has driven " + distanceTraveled + " feet with speed " + 
					currentSpeed + " with " + currentFuel + " gallons of gas remaining.\n";
		}
	}
	
	//version without logType argument that outputs default log statement
	private void writeLog()
	{
		logbook = logbook + driverName + " has driven " + distanceTraveled + " feet  out of " + 
				travelDistance + " feet with speed " + currentSpeed + " with " + currentFuel + 
				" gallons of fuel remaining.\n";
	}
	
	//Create the Vehicle's log file
	private void postLog()
	{
		try 
		{
			FileWriter logWriter = new FileWriter(driverName + "Log.txt");
			logWriter.write(logbook);
			logWriter.close();
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
