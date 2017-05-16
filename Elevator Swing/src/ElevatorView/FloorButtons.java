package ElevatorView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.ControllerOrder;
import Controller.ControllerQueue;
import ElevatorModel.ElevatorMovingDirections;

public class FloorButtons extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int floorNumber;
	private ControllerQueue controllerQueue;

	public FloorButtons(int floorNumber, ControllerQueue controllerQueue) {
		super();
		this.controllerQueue = controllerQueue;
		this.floorNumber = floorNumber;
		setLayout(new FlowLayout());
		for (int i = 0; i < this.floorNumber; i++) {
			add(new OneFloorButtons(i));
		}
	}

	private class OneFloorButtons extends JPanel implements ActionListener {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		JButton buttonUp;
		JButton buttonDown;
		private int floor;

		private OneFloorButtons(int floor) {
			super();
			this.floor = floor;
			buttonUp = new JButton(floor + " Up");
			buttonDown = new JButton(floor + " Down");
			setLayout(new BorderLayout());
			add(buttonUp, BorderLayout.NORTH);
			add(buttonDown, BorderLayout.SOUTH);
			buttonUp.addActionListener(this);
			buttonDown.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ControllerOrder order;
			if (e.getSource().equals(buttonUp)) {
				order = new ControllerOrder(floor, ElevatorMovingDirections.UP);
				controllerQueue.addButtonOrder(order);
				// System.out.println(order);
			} else {
				order = new ControllerOrder(floor, ElevatorMovingDirections.DOWN);
				controllerQueue.addButtonOrder(order);
				// System.out.println(order);
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		ControllerQueue controllerQueue = new ControllerQueue();
		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new FloorButtons(7, controllerQueue), BorderLayout.SOUTH);
		// frame.add(floor_o.getButtonPanel());
		frame.setLocationRelativeTo(null);

		// 4. Size the frame.
		frame.pack();

		// 5. Show it.
		frame.setVisible(true);
	}

}
