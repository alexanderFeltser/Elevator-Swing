package Controller;

import ElevatorModel.ElevatorMovingDirections;

public class ControllerOrder {
	int floor;
	ElevatorMovingDirections direction;

	public ControllerOrder(int floor, ElevatorMovingDirections direction) {
		this.floor = floor;
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "floor=" + floor + ", direction=" + direction;
	}

	public ElevatorOrder converToElevetorOrder(int elevatorNo) {

		return new ElevatorOrder(floor, elevatorNo);
	}

}
