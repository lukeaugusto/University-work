import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// Very basic elevator implementation.
// moves up until top floor is reached
// then moves down until the first floor is reached.
// it stops on each floor and tries to let people on and off
public class DumbElevatorV2 extends Elevator {
	
	//fields were added to better keep track of elevator opperations and aid
	//in coding the algorithm
    ArrayList<PassengerRequest> elevatorPassengers;
    boolean goingUp = true;
    int currentWeight = 0;
    DrawElevator d;
    public DumbElevatorV2(int capacity, int timeMoveOneFloor, int floors,
            int doorDelta, boolean verbose) {
        super(capacity, timeMoveOneFloor, floors, doorDelta, verbose);
    	d = new DrawElevator(this);
    	d.intitialize();
    	this.elevatorPassengers = new ArrayList<PassengerRequest>();
    }
    
    public int getCurrentWeight() {
    	return currentWeight;
    }
    
    public ArrayList<PassengerRequest> getElevatorPassengers() {
        return elevatorPassengers;
    }
    
    public boolean getGoingUp() {
        return goingUp;
    }
    
    public int currentNumberOfPassengersOnElevator() {
    	return elevatorPassengers.size();
    }
    
    @Override
    public void initialize(Queue<PassengerRequest> requests) {
    	
        servingQueue = requests;
    }
    
    
    //Make sure there is space for a new passenger
    public boolean hasSpace(int weight) {
        if (currentWeight + weight < capacity) {
            return true;
        } else {
            return false;
        }
    }
    
    public int amountOfFloors() {
    	return this.floors;
    }
    
    @Override
    public ArrayList<PassengerReleased> move() {
    	
    	ArrayList<PassengerReleased> releasedPassengers = new ArrayList<PassengerReleased>();
    	// toRemove used to remove from priorityQueues and ArrayLists after processing them
    	ArrayList<PassengerRequest> toRemove = new ArrayList<PassengerRequest>();
    	// peoplePerFloor used to draw elevator requests.
    	// floors + 1 is used because the ground floor is floor 1
    	// not floor 0
    	int[] peoplePerFloor = new int[floors + 1];
		for (PassengerRequest i: servingQueue) {
			if (i.getTimePressedButton().compareTo(currentTime) <= 0) {
				peoplePerFloor[i.getFloorFrom()]++;
				if (i.getFloor_at() == null) {
					i.setFloor_at(currentFloor);
				}
			} else {
				break;
			}
		}
		
		// draw the passenger requests and open the doors
		d.drawRequests(peoplePerFloor);
    	this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
    	
    	// let anyone off who wants to go to this floor
    	for (PassengerRequest i: elevatorPassengers) {
        	if (i.getFloorTo() == currentFloor) {
        		toRemove.add(i);
        		currentWeight -= i.getWeight();
				PassengerReleased released = new PassengerReleased(i, currentTime);
				if (verbose) {
					StringBuilder sb = new StringBuilder();
					sb.append(i.getFloor_at()+" / " + i.getFloorFrom() + " / " +i.getFloorTo()+" | ");
					sb.append(i.getTimePressedButton() + " / " + currentTime);
					System.out.println(new String(sb));
				}
				releasedPassengers.add(released);
        	}
        }
    	
    	// remove the passengers from the elevator
    	for (PassengerRequest i: toRemove){
    		elevatorPassengers.remove(i);
    	}
    	
    	//pick up any passengers that can be fit on the elevator
    	for (PassengerRequest i: servingQueue) {
    		if (i.getTimePressedButton().compareTo(currentTime) <= 0 && i.getFloorFrom() == currentFloor) {
    			if (hasSpace(i.getWeight())) {
					elevatorPassengers.add(i);
					toRemove.add(i);
					currentWeight += i.getWeight();
    			}
    		}
    	}
    	
    	//remove picked up passengers from the serving queue
    	for (PassengerRequest i: toRemove){
    		servingQueue.remove(i);
    	}
    	
    	//close the doors
    	this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
    	
    	// if on top or ground floor change directions
		if(this.currentFloor == this.floors) {
			this.goingUp = false;
		} else if(this.currentFloor == 1) {
			this.goingUp = true;
		}
		
		//Continue moving in the same direction
		if(this.getGoingUp()) {
            currentFloor++;
            d.moveUp();
            this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
        } else {
            currentFloor--;
            d.moveDown();
            this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
        }
		
        return releasedPassengers;
    }

    @Override
    public boolean continueOperate() {
    	//Continue to run the elevator while passengers haven't been served or there are passengers still on the elevator
    	if (!servingQueue.isEmpty() || !elevatorPassengers.isEmpty()) {
            return true;
        } {
            return false;
        }
    }

    public ArrayList<PassengerReleased> operate() {
        ArrayList<PassengerReleased> released = new ArrayList<PassengerReleased>();
        if (verbose) {
            System.out.println("Floor at / floor from / floor to | Requested / arrived");
        }

        while (this.continueOperate()) {
            ArrayList<PassengerReleased> moved = this.move();
            released.addAll(moved);
        }
        return released;
    }

}
