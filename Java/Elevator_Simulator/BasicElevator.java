import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// Basic elevator runs like a normal elevator would.
// picks people up moving in the same direction
// continues to move in the same direction until
// the top or bottom floor is reached.

public class BasicElevator extends Elevator {

	// ArrayList used to store elevator Requests and what requests
	// the elevator is aware of.
	ArrayList<PassengerRequest> elevatorPassengers;
	ArrayList<PassengerRequest> knownRequests;
	public int currentWeight;
	boolean doorOpened;
	boolean goingUp;
	DrawElevator d;

	public BasicElevator(int capacity, int timeMoveOneFloor, int floors,
			int doorDelta, boolean verbose) {
		super(capacity, timeMoveOneFloor, floors,doorDelta, verbose);
		d = new DrawElevator(this);
		d.intitialize();
		currentWeight = 0;
		elevatorPassengers = new ArrayList<PassengerRequest>();
		knownRequests = new ArrayList<PassengerRequest>();
		doorOpened = false;
		goingUp = true;
	}

	public int amountOfFloors() {
		return floors;
	}

	public int currentNumberOfPassengersOnElevator() {
		return elevatorPassengers.size();
	}

	public int getCurrentWeight() {
		return currentWeight;
	}

	@Override
	public ArrayList<PassengerReleased> move() {
		ArrayList<PassengerReleased> releasedPassengers = new ArrayList<PassengerReleased>();
		ArrayList<PassengerRequest> toRemove = new ArrayList<PassengerRequest>();
		int[] peoplePerFloor = new int[floors + 1];
		// find the requests the elevator is aware of and make the elevator know them
		for (PassengerRequest i: servingQueue) {
			if (i.getTimePressedButton().compareTo(currentTime) <= 0) {
				if (!knownRequests.contains(i)) {
					i.setFloor_at(currentFloor);
					knownRequests.add(i);
					toRemove.add(i);
				}
			}
		}
		
		for (PassengerRequest i : knownRequests) {
			peoplePerFloor[i.getFloorFrom()]++;
		}

		for (PassengerRequest i: toRemove) {
			servingQueue.remove(i);
		}
		d.drawRequests(peoplePerFloor);
		toRemove.clear();

		//check if anyone wants to get off at this floor
		// if they do let them off.
		for (PassengerRequest i: elevatorPassengers) {
			if (i.getFloorTo() == currentFloor) {
				toRemove.add(i);
				if (!doorOpened) {
					doorOpened = true;
					this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
				}
				PassengerReleased releasedPassenger = new PassengerReleased(i, currentTime);
				releasedPassengers.add(releasedPassenger);
				d.updatePassengerCount();
				this.currentWeight -= i.getWeight();
				if (verbose) {
					StringBuilder sb = new StringBuilder();
					sb.append(i.getFloor_at()+" / " + i.getFloorFrom() + " / " +i.getFloorTo()+" | ");
					sb.append(i.getTimePressedButton() + " / " + currentTime);

					System.out.println(new String(sb));
				}
			}
		}

		//remove them from elevator
		for (PassengerRequest i: toRemove) {
			elevatorPassengers.remove(i);
		}
		toRemove.clear();

		// switch direction if on the top or bottom floor
		if(this.currentFloor == this.floors) {
			this.goingUp = false;
		} else if(this.currentFloor == 1) {
			this.goingUp = true;
		}

		// for the requests the elevator is aware of
		// we then check to see if the elevator can pick the person up.
		// the criteria used to pick up a passenger is make sure the elevator has space
		// and the passenger wants to go in the same direction the elevator is moving
		for (PassengerRequest i: knownRequests) {
			if(i.getFloorFrom() == currentFloor && hasSpace(i.getWeight())) {
				if (goingUp && i.getFloorFrom() < i.getFloorTo()) {
					if (!doorOpened) {
						doorOpened = true;
						this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
					}
					elevatorPassengers.add(i);
					currentWeight += i.getWeight();
					d.updatePassengerCount();
					toRemove.add(i);
				} else if (!goingUp && i.getFloorFrom() > i.getFloorTo()) {
					if (!doorOpened) {
						doorOpened = true;
						this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
					}
					elevatorPassengers.add(i);
					currentWeight += i.getWeight();
					d.updatePassengerCount();
					toRemove.add(i);
				} else if (i.getFloorFrom() == i.getFloorTo()) {
					// if the person wants to got to the floor they are already on
					// just let them off now without putting them on the elevator
					toRemove.add(i);
					if (!doorOpened) {
						doorOpened = true;
						this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
					}
					PassengerReleased releasedPassenger = new PassengerReleased(i, currentTime);
					releasedPassengers.add(releasedPassenger);
					if (verbose) {
						StringBuilder sb = new StringBuilder();
						sb.append(currentFloor+" / " + i.getFloorFrom() + " / " +i.getFloorTo()+" | ");
						sb.append(i.getTimePressedButton() + " / " + currentTime);

						System.out.println(new String(sb));
					}
				}
			}
		}

		if (doorOpened) {
			doorOpened = false;
			this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
		}

		for (PassengerRequest i: toRemove) {
			knownRequests.remove(i);
		}
		// if the elevator doesn't have any passengers, idle on the same floor
		// otherwise continue moving in the same direction.
		if (elevatorPassengers.size() == 0 && knownRequests.size() == 0) {
			this.currentTime = new Time(this.currentTime.getTime() + 1000);
		} else if(goingUp) {
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
	public void initialize(Queue<PassengerRequest> requests) {

		servingQueue = requests;
	}

	public boolean continueOperate() {
		if(this.servingQueue.isEmpty() && elevatorPassengers.isEmpty() && knownRequests.isEmpty()) {
			return false;
		} else{
			return true;
		}
	}

	public boolean hasSpace() {
		if (currentWeight < capacity) {
			return true;
		} else {
			return false;
		}
	}

	//Make sure there is space for a new passenger
	public boolean hasSpace(int weight) {
		if (currentWeight + weight < capacity) {
			return true;
		} else {
			return false;
		}
	}

	@Override
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