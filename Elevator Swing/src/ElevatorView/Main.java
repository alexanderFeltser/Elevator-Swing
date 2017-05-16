package ElevatorView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.ElevatorsController;
import ElevatorModel.Elevator;
import ElevatorModel.ElevatorStatesQueue;

public class Main {
	JFrame frame;
	JPanel elevatorControllerJpanel;
	ElevatorStatesQueue elevatorsQueu;
	ControllerCommands controllerComands;
	ElevatorsController controller;
	int elevatorsNo;
	int MAX_FLOOR;

	private Main(int elevatorsNo, int MAX_FLOOR) {

		this.elevatorsNo = elevatorsNo;
		this.MAX_FLOOR = MAX_FLOOR;
		frame = new JFrame("Elevator System");
		elevatorControllerJpanel = new JPanel();

		// ControllerQueue controllerQueue = controller.getControllerOueue();
		elevatorsQueu = new ElevatorStatesQueue(elevatorsNo);
		controller = new ElevatorsController(MAX_FLOOR);
		Elevator elevator;
		for (int i = 0; i < elevatorsNo; i++) {
			elevator = new Elevator(i, MAX_FLOOR, 10, elevatorsQueu);
			controller.addElevator(elevator);
			elevatorsQueu.setElevatorState(elevator.getCurrentState(), i);
			// elevator.start();
		}

		controller.start();
		elevatorControllerJpanel.setLayout(new FlowLayout());
		controllerComands = new ControllerCommands(controller.getControllerOueue());
		controllerComands.refreshControllerCommands();
		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new FloorButtons(MAX_FLOOR, controller.getControllerOueue()), BorderLayout.SOUTH);

		elevatorControllerJpanel.add(new ElevatorsView(MAX_FLOOR, elevatorsNo, elevatorsQueu, controller
				.getControllerOueue()));
		elevatorControllerJpanel.add(controllerComands);

		frame.add(elevatorControllerJpanel, BorderLayout.NORTH);
		frame.setLocationRelativeTo(null);

		// 4. Size the frame.
		frame.pack();

		// 5. Show it.
		frame.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		// int elevatorsNo = 3;
		// int MAX_FLOOR = 12;
		new Main(3, 12);
		// JFrame frame = new JFrame("Elevator System");
		// JPanel elevatorControllerJpanel = new JPanel();
		// ElevatorsController controller = new ElevatorsController(MAX_FLOOR);
		//
		// ElevatorStatesQueue elevatorsQueu = new
		// ElevatorStatesQueue(elevatorsNo);
		// Elevator elevator;
		// for (int i = 0; i < elevatorsNo; i++) {
		// elevator = new Elevator(i, MAX_FLOOR, 10, elevatorsQueu);
		// controller.addElevator(elevator);
		// elevatorsQueu.setElevatorState(elevator.getCurrientState(), i);
		// }

		// elevatorControllerJpanel.setLayout(new FlowLayout());
		// ControllerCommandsJPanel controllerComands = new
		// ControllerCommandsJPanel(controller.getControllerOueue());

		// controllerComands.refreshControllerCommands();
		// // 2. Optional: What happens when the frame closes?
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.add(new FloorButtonsJPanel(MAX_FLOOR,
		// controller.getControllerOueue()), BorderLayout.SOUTH);
		//
		// elevatorControllerJpanel.add(new ElevatorsViewJPanel(MAX_FLOOR,
		// elevatorsNo, elevatorsQueu, controller
		// .getControllerOueue()));
		// elevatorControllerJpanel.add(controllerComands);
		//
		// frame.add(elevatorControllerJpanel, BorderLayout.NORTH);
		// frame.setLocationRelativeTo(null);
		//
		// // 4. Size the frame.
		// frame.pack();
		//
		// // 5. Show it.
		// frame.setVisible(true);

	}
}
