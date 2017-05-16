package ElevatorModel;

//public class Elevator implements Runnable {
public class Elevator extends Thread {
	private int elevatorId;
	private final int BUILDING_MAX_FLOOR;
	private ElevatorStatesQueue elevatorStateQueue;
	private ElevatorState currentState;
	final int MAX_LOADING;
	private int nextFloor;

	public Elevator(int elevatorId, int houseMaxFloor, int maxLoading, ElevatorStatesQueue elevatorStateQueue) {
		this.elevatorId = elevatorId;
		BUILDING_MAX_FLOOR = houseMaxFloor;
		this.MAX_LOADING = maxLoading;
		this.elevatorStateQueue = elevatorStateQueue;
		initializeCurrientState();

		// start();
	}

	public void initializeCurrientState() {
		if (currentState == null) {
			currentState = new ElevatorState(0, ElevatorMovingDirections.UP, 0, BUILDING_MAX_FLOOR, MAX_LOADING);
			nextFloor = currentState.floor;
		}
		elevatorStateQueue.setElevatorState(currentState.clone(), elevatorId);
	}

	public void makeMove(int nextFloor) {

		if (nextFloor == currentState.floor) {
			return;
		}

		currentState.setDirection(nextFloor, currentState.floor);
		currentState.setFloor(nextFloor);

		currentState.loading = currentState.loading + (int) (Math.random() * 4) - 2;
		if (currentState.loading < 0) {
			currentState.loading = 0;
		}
		elevatorStateQueue.setElevatorState(currentState.clone(), elevatorId);
		try {
			Thread.sleep(100);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

	}

	public ElevatorState getCurrentState() {
		return currentState;
	}

	public boolean isOrderInElevatorList() {
		return false;

	}

	@Override
	public void run() {
		while (true) {
			// long threadId = Thread.currentThread().getId();
			// System.out.println("Elevator N" + elevatorId + ": " + threadId);
			makeMove(nextFloor);
		}
	}

	public void setNextFloor(int nextFloor) {
		this.nextFloor = nextFloor;
	}

	public int getElevatorNo() {

		return elevatorId;
	}
}
