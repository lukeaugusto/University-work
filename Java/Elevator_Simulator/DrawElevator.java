import java.sql.Time;
public class DrawElevator {
	private static final int SPEED = 50;
	int floors;
	Time currentTime;
	int xMax, yMax;
	int currentFloor;
	DumbElevatorV2 d;
	BasicElevator b;
	FastV5 f;
	
	int elevatorType;
	
	//Constructors for DrawElevator depending on the
	// Type of elevator that needs to be visualized
	public DrawElevator() {
		currentFloor = 1;
		floors = 9;
		new Time(8,0,0);
		xMax = floors * 100;
		yMax = floors * 100;
	}
	
	public DrawElevator(DumbElevatorV2 d) {
		this.d = d;
		currentFloor = 1;
		floors = d.amountOfFloors();
		currentTime = d.currentTime;
		xMax = 900;
		yMax = 900;
		elevatorType = 0;
	}

	public DrawElevator(BasicElevator e) {
		this.b = e;
		currentFloor = 1;
		floors = e.amountOfFloors();
		currentTime = e.currentTime;
		xMax = 900;
		yMax = 900;
		elevatorType = 1;
	}

	public DrawElevator(FastV5 e) {
		this.f = e;
		currentFloor = 1;
		floors = e.amountOfFloors();
		currentTime = e.currentTime;
		xMax = 900;
		yMax = 900;
		elevatorType = 2;
	}

	
	// draw the floors and the intial elevator position
	public void intitialize() {
		StdDraw.setCanvasSize(xMax, xMax);
		StdDraw.setXscale(0, xMax);
		StdDraw.setYscale(0, yMax);
		StdDraw.line(0, 1, xMax, 1);
		StdDraw.line(xMax/10, 0, xMax/10, yMax);
		for (int i = 1; i <= floors; i++) {
			StdDraw.line(xMax/10, i * yMax/floors, 7*xMax/10, i * yMax/floors);
		}
		StdDraw.line( 7 * xMax/10, 0, 7 * xMax/10, yMax);
		StdDraw.rectangle((15 * xMax/20) + 1, yMax/ ( 2 * floors), xMax/20, yMax/ ( 2 * floors));
		StdDraw.textLeft((15 * xMax/20), 50, "0");
		if (elevatorType == 2) {
			StdDraw.textLeft(800, 50, f.currentTime.toString());
		} else if (elevatorType == 1){
			StdDraw.textLeft(800, 50, b.currentTime.toString());
		} else if (elevatorType == 0){
			StdDraw.textLeft(800, 50, d.currentTime.toString());
		}
	}

	// update how many passengers are on the elevator
	public void updatePassengerCount() {
		StdDraw.show(0);
		StdDraw.setPenColor(StdDraw.WHITE);
		//instead of currentFloor * (xMax / floors) - (xMax / (2 * floors)) I had currentFloor * 100 - 50
		StdDraw.filledRectangle((15 * xMax/20) + 2, currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, yMax/ ( 2 * floors));
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle((15 * xMax/20) + 1,  currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, (xMax / (2 * floors)));
		if (elevatorType == 2) {
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(f.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, f.currentTime.toString());
		} else if (elevatorType == 1){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(b.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, b.currentTime.toString());
		} else if (elevatorType == 0){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(d.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, d.currentTime.toString());
		}
		StdDraw.show(0);
	}

	// animate the elevator moving up
	public void moveUp() {
		this.drawWeight();
		StdDraw.show(SPEED);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle((15 * xMax/20) + 2, currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, yMax/ ( 2 * floors) + 1);
		StdDraw.filledRectangle(850, 50, 50, 40);
		StdDraw.setPenColor(StdDraw.BLACK);
		currentFloor++;
		StdDraw.rectangle((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, (xMax / (2 * floors)));
		if (elevatorType == 2) {
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(f.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, f.currentTime.toString());
		} else if (elevatorType == 1){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(b.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, b.currentTime.toString());
		} else if (elevatorType == 0){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(d.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, d.currentTime.toString());
		}
		StdDraw.show(SPEED);

	}

	// draw the current elevator weight
	public void drawWeight() {
		// TODO Auto-generated method stub
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(810,125,80,40);
		StdDraw.setPenColor(StdDraw.BLACK);
		if (elevatorType == 2) {
			StdDraw.textLeft(750, 150, String.format("Current Weight: %d", f.getCurrentWeight()));
			StdDraw.textLeft(750, 100, String.format("MaxWeight: %d", f.capacity));
		} else if (elevatorType == 1){
			StdDraw.textLeft(750, 150, String.format("Current Weight: %d",b.getCurrentWeight()));
			StdDraw.textLeft(750, 100, String.format("MaxWeight: %d",b.capacity));
		} else if (elevatorType == 0){
			StdDraw.textLeft(750, 150, String.format("Current Weight: %d",d.getCurrentWeight()));
			StdDraw.textLeft(750, 100, String.format("MaxWeight: %d",d.capacity));
		}
	}

	// animate the elevator moving down
	public void moveDown() {
		this.drawWeight();
		StdDraw.show(SPEED);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle((15 * xMax/20) + 2, currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, yMax/ ( 2 * floors) + 1);
		StdDraw.filledRectangle(850, 50, 50, 40);
		StdDraw.setPenColor(StdDraw.BLACK);
		currentFloor--;
		StdDraw.rectangle((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), xMax/20, (xMax / (2 * floors)));
		if (elevatorType == 2) {
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(f.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, f.currentTime.toString());
		} else if (elevatorType == 1){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(b.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, b.currentTime.toString());
		} else if (elevatorType == 0){
			StdDraw.textLeft((15 * xMax/20) + 1, currentFloor * (xMax / floors) - (xMax / (2 * floors)), Integer.toString(d.currentNumberOfPassengersOnElevator()));
			StdDraw.textLeft(800, 50, d.currentTime.toString());
		}
		StdDraw.show(SPEED);
	}

	// draw the people waiting on each floor.
	public void drawRequests(int[] peoplePerFloor) {
		for (int i = 0; i < peoplePerFloor.length; i++) {
			StdDraw.setPenColor(StdDraw.WHITE);
			StdDraw.filledRectangle(4 * xMax / 10, i * (xMax / floors) - (xMax / (2 * floors)), 3*xMax/10 - 5, 10);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.textRight(450, i * (xMax / floors) - (xMax / (2 * floors)), String.format("People Waiting: %d",peoplePerFloor[i]));
		}
	}

}
