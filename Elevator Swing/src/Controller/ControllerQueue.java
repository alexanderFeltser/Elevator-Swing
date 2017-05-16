package Controller;

import java.util.ArrayList;

public class ControllerQueue {
	private ArrayList<ControllerOrder> controllerOrders;
	private ArrayList<ControllerQueueListener> listeners;
	private ArrayList<ElevatorOrder> elevatorOrders;
	Object synchronizeObject;

	public ControllerQueue() {
		super();
		controllerOrders = new ArrayList<>();
		elevatorOrders = new ArrayList<>();
		listeners = new ArrayList<ControllerQueueListener>();
		synchronizeObject = new Object();
	}

	public synchronized void addButtonOrder(ControllerOrder controllerOrder) {
		// synchronized (controllerOrders) {
		controllerOrders.add(controllerOrder);
		// }
		fireListeners(1);
		synchronized (synchronizeObject) {
			synchronizeObject.notifyAll();
		}

	}

	public synchronized void removeControllerOrder(ControllerOrder controllerOrder) {
		// synchronized (controllerOrders) {
		controllerOrders.remove(controllerOrder);
		// }
		fireListeners(1);

	}

	public void removeElevatorOrder(ElevatorOrder elevatorOrder) {
		synchronized (elevatorOrders) {
			elevatorOrders.remove(elevatorOrder);

		}
	}

	public void addElevatorOrder(ElevatorOrder elevatorOrder) {
		synchronized (elevatorOrders) {
			elevatorOrders.add(elevatorOrder);
		}
		synchronized (synchronizeObject) {
			synchronizeObject.notifyAll();
		}
		// fireListeners(1);
		// System.out.println("Inside Elevator Order " + elevatorOrder);
	}

	public synchronized final ArrayList<ControllerOrder> getControllerOrders() {
		return controllerOrders;
	}

	public final ArrayList<ElevatorOrder> getElevatorOrders() {
		return elevatorOrders;
	}

	public void addListener(ControllerQueueListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ControllerQueueListener listener) {
		listeners.remove(listener);
	}

	private void fireListeners(int count) {
		for (ControllerQueueListener listener : listeners) {
			listener.onDataChanged(count);
		}

	}

	public synchronized void refreshControllerOrdersQueue() {
		fireListeners(1);
	}

	public synchronized void removeControllerOrders(ArrayList<ControllerOrder> conrollerOrdersToRemove) {
		if (!(controllerOrders.size() == 0)) {
			controllerOrders.removeAll(conrollerOrdersToRemove);

		}
	}

}
