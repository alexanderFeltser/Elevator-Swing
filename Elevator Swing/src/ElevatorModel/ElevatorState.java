package ElevatorModel;

public class ElevatorState implements Cloneable {
	public int floor;
	public ElevatorMovingDirections direction;
	int loading;
	int maxLoading;
	int maxFloor;

	public ElevatorState(int floor, ElevatorMovingDirections direction, int loading, int maxFloor, int maxLoading) {
		super();
		this.floor = floor;
		this.direction = direction;
		this.loading = loading;
		this.maxFloor = maxFloor;
		this.maxLoading = maxLoading;
	}

	public void setDirection(int targetFloor, int currientFlor) {
		if (targetFloor > currientFlor) {
			direction = ElevatorMovingDirections.UP;
		} else if (targetFloor < currientFlor) {
			direction = ElevatorMovingDirections.DOWN;
		}

	}

	public void changeDirection() {
		if (direction == ElevatorMovingDirections.UP) {
			direction = ElevatorMovingDirections.DOWN;
		} else {
			direction = ElevatorMovingDirections.UP;
		}
	}

	@Override
	public ElevatorState clone() {
		try {
			return (ElevatorState) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Unable to copy ElevatorState " + this);
		}

	}

	public final void setFloor(int floor) {
		this.floor = floor;

	}

	public final int getFloor() {
		return floor;
	}

	public static void main(String[] args) {
		ElevatorState state = new ElevatorState(12, ElevatorMovingDirections.UP, 7, 12, 10);
		state.maxLoading = 9;
		ElevatorState state1 = state.clone();
		System.out.println(state1.floor + " " + state1.direction + " " + state1.loading + " " + state1.maxLoading);
	}

}
