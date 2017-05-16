package Controller;

import java.util.ArrayList;

import ElevatorModel.Elevator;
import ElevatorModel.ElevatorMovingDirections;
import ElevatorModel.ElevatorState;

public class ElevatorsController extends Thread implements ControllerQueueListener {
	public int HOUS_MAX_FLOOR;
	ControllerQueue controllerOueue;
	ArrayList<Elevator> elevatorArray;
	ArrayList<ArrayList<ElevatorOrder>> elevatorsPlan;

	public ElevatorsController(int maxFloor) {
		HOUS_MAX_FLOOR = maxFloor;
		controllerOueue = new ControllerQueue();
		elevatorArray = new ArrayList<>();
		elevatorsPlan = new ArrayList<>();

	}

	public final ControllerQueue getControllerOueue() {
		return controllerOueue;
	}

	public void addElevator(Elevator elevator) {
		elevatorArray.add(elevator);
		elevatorsPlan.add(new ArrayList<>());
	}

	private void passOrdersToElevators() {
		passElevatorsOrders(controllerOueue.getElevatorOrders());
		ElevatorOrder elevatorOrder;
		// Orders From floor buttons
		passControllerOrders(controllerOueue.getControllerOrders());

		for (Elevator elevator : elevatorArray) {
			// System.out.println(" IN loop elevatorNo " +
			// elevator.getElevatorNo());
			if (!elevatorsPlan.get(elevator.getElevatorNo()).isEmpty()) {
				elevatorOrder = elevatorsPlan.get(elevator.getElevatorNo()).get(0);
				elevator.makeMove(elevatorOrder.floor);
				elevatorsPlan.get(elevator.getElevatorNo()).remove(elevatorOrder);
			}
		}
		// controllerOueue.refreshControllerOrdersQueue();
		// System.out.println("for ended");
	}

	private boolean isEmptyElevatorsPlan() {
		boolean isEmpty = true;

		for (Elevator elevator : elevatorArray) {

			if (!elevatorsPlan.get(elevator.getElevatorNo()).isEmpty()) {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}

	// Move orders from floor buttons to elevators Plan orders
	private void passControllerOrders(ArrayList<ControllerOrder> controleOrders) {
		ArrayList<ControllerOrder> controllerOrders2Remove = new ArrayList<>();
		int elevatorNo;
		for (ControllerOrder controllerOrder : controleOrders) {
			elevatorNo = findElevatorToGetOrder(controllerOrder);
			addControlleOrder2ElevatorPlan(elevatorNo, controllerOrder);
			controllerOrders2Remove.add(controllerOrder);
			// controllerOueue.refreshControllerOrdersQueue();
		}
		controllerOueue.removeControllerOrders(controllerOrders2Remove);

		controllerOueue.refreshControllerOrdersQueue();

	}

	private void addControlleOrder2ElevatorPlan(int elevatorNo, ControllerOrder controllerOrder) {
		elevatorsPlan.get(elevatorNo).add(controllerOrder.converToElevetorOrder(elevatorNo));
	}

	private int findElevatorToGetOrder(ControllerOrder controllerOrder) {
		int minDistanceToImplementOrder = Integer.MAX_VALUE;
		int curDistance = minDistanceToImplementOrder;
		int elevatorNo = 0;
		for (Elevator elevator : elevatorArray) {
			curDistance = countDistance2TargetFloor(controllerOrder, elevator.getCurrentState());
			if (curDistance < minDistanceToImplementOrder) {
				minDistanceToImplementOrder = curDistance;
				elevatorNo = elevator.getElevatorNo();
			}
		}
		return elevatorNo;
	}

	private void passElevatorsOrders(ArrayList<ElevatorOrder> elevatorOrders) {
		ArrayList<ElevatorOrder> elevatorOrders2Remove = new ArrayList<>();
		for (ElevatorOrder elevatorOrder : elevatorOrders) {
			elevatorsPlan.get(elevatorOrder.elevatoNumber).add(
					findOrderInElevatorPlan(elevatorOrder.elevatoNumber, elevatorOrder), elevatorOrder);
			elevatorOrders2Remove.add(elevatorOrder);
		}
		elevatorOrders.removeAll(elevatorOrders2Remove);
	}

	private int findOrderInElevatorPlan(int elevatoNumber, ElevatorOrder elevatorOrder) {
		ControllerOrder order = new ControllerOrder(elevatorOrder.floor, elevatorArray.get(elevatoNumber)
				.getCurrentState().direction);
		int distanceFromTargetFloor = countDistance2TargetFloor(order, elevatorArray.get(elevatoNumber)
				.getCurrentState());
		return firstElementIndexWithGraterDistance(distanceFromTargetFloor, elevatorsPlan.get(elevatoNumber),
				elevatorArray.get(elevatoNumber).getCurrentState());
	}

	private int firstElementIndexWithGraterDistance(int distanceFromTargetFloor,
			ArrayList<ElevatorOrder> elevatorOrdersPlan, ElevatorState elevatorState) {
		int index = 0;
		for (ElevatorOrder order : elevatorOrdersPlan) {
			if (distanceFromTargetFloor < countDistance2TargetFloor(new ControllerOrder(order.floor,
					elevatorState.direction), elevatorState)) {
				index = elevatorOrdersPlan.indexOf(order);
				break;
			}
		}
		return index;
	}

	public void printControlerOrderList() {
		System.out.println("Controller Orders:");
		for (ControllerOrder controllerOrder : controllerOueue.getControllerOrders()) {
			System.out.println(controllerOrder);
			// System.out.println(controllerOrder.floor + controllerOrder.);
		}
	}

	public void printElevatorsOrders() {
		System.out.println("Elevator " + " Orders:");
		for (ArrayList<ElevatorOrder> elevatorOrders : elevatorsPlan) {
			System.out.println("Elevator No " + elevatorsPlan.indexOf(elevatorOrders) + ":");
			for (ElevatorOrder elevatorOrder : elevatorOrders) {
				System.out.println(elevatorOrder);
			}
		}
	}

	public int countDistance2TargetFloor(ControllerOrder controllerOrder, ElevatorState elevatorState) {
		int distance = 0;
		if (controllerOrder.floor == elevatorState.floor) {
			return distance;
		}
		if (elevatorState.direction == controllerOrder.direction) {
			if (elevatorState.direction == ElevatorMovingDirections.UP) {
				if (elevatorState.floor <= controllerOrder.floor) {
					distance = controllerOrder.floor - elevatorState.floor;
				} else {
					distance = HOUS_MAX_FLOOR * 2 - controllerOrder.floor;
				}
			}
			// both directions moving Down
			else {
				if (elevatorState.floor >= controllerOrder.floor) {
					distance = Math.abs(elevatorState.floor - controllerOrder.floor);
				} else {
					distance = HOUS_MAX_FLOOR * 2 - elevatorState.floor;
				}
			}
		}
		// different directions
		else {
			if (elevatorState.direction == ElevatorMovingDirections.UP) {
				distance = HOUS_MAX_FLOOR * 2 - elevatorState.floor - controllerOrder.floor;
			} else {
				distance = elevatorState.floor + controllerOrder.floor;
			}
		}
		return distance;
	}

	@Override
	public void onDataChanged(int dataAddedCount) {
		// TODO Auto-generated method stub
	}

	@Override
	public void run() {
		// int count = 0;
		while (true) {

			try {
				synchronized (controllerOueue.synchronizeObject) {

					if (controllerOueue.getControllerOrders().size() == 0 && isEmptyElevatorsPlan()) {
						controllerOueue.synchronizeObject.wait();
					}

				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			passOrdersToElevators();
			// System.out.println("in run count = " + count++);
		}
	}
}
