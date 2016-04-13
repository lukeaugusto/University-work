import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class FastV5 extends Elevator {

	// ArrayList used to store elevator passengers 
	// Priority Queue is used for known requests so that the oldest request
	// is always moved to first
	ArrayList<PassengerRequest> elevatorPassengers;
	PriorityQueue<PassengerRequest> knownRequests;
	public int currentWeight;
	boolean doorOpened;
	boolean goingUp;
	DrawElevator d;
	public FastV5(int capacity, int timeMoveOneFloor, int floors,
			int doorDelta, boolean verbose) {
		super(capacity, timeMoveOneFloor, floors,doorDelta, verbose);
		d = new DrawElevator(this);
		d.intitialize();
		currentWeight = 0;
		elevatorPassengers = new ArrayList<PassengerRequest>();
		knownRequests = new PriorityQueue<PassengerRequest>(5, new Comparator<PassengerRequest>() {

			@Override
			public int compare(PassengerRequest o1, PassengerRequest o2) {
				// TODO Auto-generated method stub
				return Math.abs(o1.getFloorFrom() - currentFloor) > Math.abs(o2.getFloorFrom() - currentFloor) ? 1:-1;
			}
		});
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
		// make the elevator aware of requests if the time
		// for the request is before the current time
		for (PassengerRequest i: servingQueue) {
			if (i.getTimePressedButton().compareTo(currentTime) <= 0) {
				if (!knownRequests.contains(i)) {
					i.setFloor_at(currentFloor);
					knownRequests.offer(i);
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

		// check if anyone wants to get off on this floor
		// and let them off if they want to
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

		// if on the top or bottom floor change direction
		if(this.currentFloor == this.floors) {
			this.goingUp = false;
		} else if(this.currentFloor == 1) {
			this.goingUp = true;
		}

		// the logic for picking up passengers is the main change
		// between the fast and basic elevator.
		// if the elevator has passengers on it we pick up any passengers
		// wanting to move in the same direction that we can fit
		// if the elevator has no passengers and someone on the current floor
		// and somone wants to move in the opposite direction the elevator
		// changes direction and picks them up.
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
				} else if (elevatorPassengers.isEmpty() && goingUp && i.getFloorTo() < currentFloor) {
					if (!doorOpened) {
						doorOpened = true;
						this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
					}
					goingUp = false;;
					elevatorPassengers.add(i);
					currentWeight += i.getWeight();
					d.updatePassengerCount();
					toRemove.add(i);
				} else if (elevatorPassengers.isEmpty() && !goingUp && i.getFloorTo() > currentFloor) {
					if (!doorOpened) {
						doorOpened = true;
						this.currentTime = new Time(this.currentTime.getTime() +   500 * this.doorDelta);
					}
					goingUp = false;;
					elevatorPassengers.add(i);
					currentWeight += i.getWeight();
					d.updatePassengerCount();
					toRemove.add(i);
				} else if (i.getFloorFrom() == i.getFloorTo()) {
					toRemove.add(i);
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

		// if the elevator has no passengers and is aware of no requests it idles on the current floor
		// the elevator has passengers it continues to move in the same direction.
		// if the elevator has no passengers but is aware of requests
		// it moves to the oldest request in the priority queue
		if (elevatorPassengers.size() == 0 && knownRequests.size() == 0) {
			this.currentTime = new Time(this.currentTime.getTime() + 1000);
		} else if(elevatorPassengers.size() != 0 && goingUp) {
			currentFloor++;
			d.moveUp();
			this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
		} else if(elevatorPassengers.size() != 0 && !goingUp) {
			currentFloor--;
			d.moveDown();
			this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
		} else if (knownRequests.peek().getFloorFrom() > currentFloor) {
			goingUp = true;
			currentFloor++;
			d.moveUp();
			this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
		} else if(knownRequests.peek().getFloorFrom() < currentFloor) {
			goingUp = false;
			currentFloor--;
			d.moveDown();
			this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
		} else if(goingUp) {
			goingUp = true;
			currentFloor++;
			d.moveUp();
			this.currentTime = new Time(this.currentTime.getTime() +   1000 * this.timeMoveOneFloor);
		} else {
			goingUp = false;
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

