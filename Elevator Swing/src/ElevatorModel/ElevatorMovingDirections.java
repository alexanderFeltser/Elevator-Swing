package ElevatorModel;
public enum ElevatorMovingDirections {
	DOWN("Moving down"), UP("Moving up");

	private String name;

	private ElevatorMovingDirections(String name) {
		this.name = name;
	}

	public static ElevatorMovingDirections getValue(boolean isUp) {
		if (isUp) {
			return UP;
		} else {
			return DOWN;
		}
	}

	public String getName() {
		return name;
	}
}
