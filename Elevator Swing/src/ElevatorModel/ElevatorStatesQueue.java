package ElevatorModel;

import java.util.ArrayList;

import Controller.ControllerQueueListener;

//Queue from elevators to View Model (GUI - Elevators Panel)
public class ElevatorStatesQueue {
	private int elevatorsNumber;
	private ElevatorState[] elevatorStates;

	private ArrayList<ElevatorStateListener> listeners;

	private ArrayList<Integer>[] insideElevatorPressedButtonsQueue;

	public ElevatorStatesQueue(int elevatorsNumber) {
		super();
		this.elevatorsNumber = elevatorsNumber;
		elevatorStates = new ElevatorState[elevatorsNumber];
		listeners = new ArrayList<ElevatorStateListener>();
		insideElevatorPressedButtonsQueue = new ArrayList[elevatorsNumber];
		for (int i = 0; i < elevatorsNumber; i++) {
			insideElevatorPressedButtonsQueue[i] = new ArrayList<>();
		}
	}

	/**
	 * @return the elevatorsNumber
	 */
	public int getElevatorsNumber() {
		return elevatorsNumber;
	}

	public final void setElevatorState(int elevatorNo, ElevatorState elevatorState) {
		elevatorStates[elevatorNo] = elevatorState;
		fireListeners(elevatorNo);
	}

	/**
	 * @return the elevatorStates
	 */
	public ElevatorState getElevatorState(int index) {
		return elevatorStates[index];
	}

	/**
	 * @param elevatorStates
	 *            the elevatorStates to set
	 */
	public void setElevatorState(ElevatorState elevatorState, int index) {
		elevatorStates[index] = elevatorState;
		fireListeners(index);

	}

	public void addListener(ElevatorStateListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ControllerQueueListener listener) {
		listeners.remove(listener);
	}

	private void fireListeners(int elevatorNo) {
		for (ElevatorStateListener listener : listeners) {
			listener.onDataChanged(elevatorNo);
		}
	}

}
