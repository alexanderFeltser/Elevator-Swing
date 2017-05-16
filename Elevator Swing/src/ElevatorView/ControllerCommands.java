package ElevatorView;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Controller.ControllerOrder;
import Controller.ControllerQueue;
import Controller.ControllerQueueListener;
import ElevatorModel.ElevatorMovingDirections;

public class ControllerCommands extends JPanel implements ControllerQueueListener {
	/**
	 * Shows button pressed from the floors in JLable in View of MVC
	 *
	 */
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> jListControllerData;
	JList<String> controllerOrdersJlist;
	JLabel controllerOrdersJlable;
	private ControllerQueue controllerQueue;

	public ControllerCommands(ControllerQueue controllerQueue) {
		super();
		this.controllerQueue = controllerQueue;

		setLayout(new BorderLayout());

		jListControllerData = new DefaultListModel<>();
		// refreshControllerCommands();
		controllerOrdersJlable = new JLabel("Floor Button Orders       ");
		controllerOrdersJlist = new JList<>(jListControllerData);
		add(controllerOrdersJlable, BorderLayout.NORTH);
		add(controllerOrdersJlist, BorderLayout.SOUTH);
		this.controllerQueue.addListener(this);
	}

	public void refreshControllerCommands() {
		if (!jListControllerData.isEmpty()) {
			jListControllerData.removeAllElements();
		}
		if (controllerQueue.getControllerOrders().size() > 0) {
			for (int i = 0; i < controllerQueue.getControllerOrders().size(); i++) {
				jListControllerData.addElement(controllerQueue.getControllerOrders().get(i).toString());
			}

		}
		controllerOrdersJlist.setModel(jListControllerData);
		controllerOrdersJlist.repaint();

	}

	@Override
	public void onDataChanged(int dataAddedCount) {
		refreshControllerCommands();

	}

	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame("FrameDemo");

		ControllerQueue controllerQueue = new ControllerQueue();
		for (int i = 0; i < 4; i++) {
			controllerQueue.getControllerOrders().add(new ControllerOrder(i, ElevatorMovingDirections.UP));
		}
		ControllerCommands controllerComands = new ControllerCommands(controllerQueue);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(controllerComands, BorderLayout.NORTH);

		frame.setLocationRelativeTo(null);

		// 4. Size the frame.
		frame.pack();
		controllerComands.refreshControllerCommands();
		// 5. Show it.
		frame.setVisible(true);
		Thread.sleep(3000);
		for (int i = 0; i < 9; i++) {
			controllerQueue.getControllerOrders().add(new ControllerOrder(i, ElevatorMovingDirections.UP));
		}
		controllerComands.refreshControllerCommands();
	}
}
