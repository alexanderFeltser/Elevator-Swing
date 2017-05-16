package Controller;

public class ElevatorOrder {
	int floor;
	int elevatoNumber;

	public ElevatorOrder(int floor, int elevatoNumber) {
		this.floor = floor;
		this.elevatoNumber = elevatoNumber;
	}

	@Override
	public String toString() {
		return "elevatoNumber " + elevatoNumber + ": floor=" + floor;
	}

}
