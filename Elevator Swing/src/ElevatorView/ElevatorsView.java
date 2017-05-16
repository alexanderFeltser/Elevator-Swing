package ElevatorView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import Controller.ControllerQueue;
import Controller.ElevatorOrder;
import ElevatorModel.ElevatorMovingDirections;
import ElevatorModel.ElevatorState;
import ElevatorModel.ElevatorStateListener;
import ElevatorModel.ElevatorStatesQueue;

public class ElevatorsView extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<OneElevatorView> elavators;
	ElevatorStatesQueue elevatorsQueue;
	ControllerQueue controllerQueue;
	private final int MAX_FLOOR;

	ElevatorsView(int maxFloor, int elevetorsQtty, ElevatorStatesQueue elevatorsQueue,
			ControllerQueue controllerQueue) {
		super();
		this.MAX_FLOOR = maxFloor;
		setLayout(new FlowLayout());
		OneElevatorView elevator;
		elavators = new ArrayList<>();
		this.elevatorsQueue = elevatorsQueue;
		this.controllerQueue = controllerQueue;
		for (int i = 0; i < elevetorsQtty; i++) {
			elevator = new OneElevatorView(i, maxFloor);
			add(elevator);
			elavators.add(elevator);
		}
	}

	private class OneElevatorView extends JPanel implements ElevatorStateListener {
		private static final long serialVersionUID = 1L;
		JTable elevatorJTable;
		private int elevatorNo;

		MyTableModel tableModel;

		private OneElevatorView(int elevatorNo, int floor) {
			super();
			this.elevatorNo = elevatorNo;
			tableModel = new MyTableModel();
			elevatorJTable = new JTable(tableModel);
			elevatorJTable.setDefaultRenderer(Object.class, new MyCellRenderer());
			elevatorsQueue.addListener(this);
			setLayout(new BorderLayout());
			add(elevatorJTable, BorderLayout.CENTER);
		}

		@Override
		public void onDataChanged(int elevatorNo) {
			if (this.elevatorNo == elevatorNo) {
				tableModel.fireTableDataChanged();
				// controllerQueue.getElevatorOrders().notifyAll();
				// System.out.println("Elevator >>" +
				// elevatorsQueue.getElevatorState(elevatorNo).floor);
			}
		}

		private class MyTableModel extends AbstractTableModel {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;
			private String[] data;

			private MyTableModel() {
				super();
				data = new String[MAX_FLOOR];
				for (int i = 0; i < MAX_FLOOR; i++) {
					// System.out.println("elevator" + elevatorsQueue);
					// System.out.println("elevatorNo" + elevatorNo + " " +
					// elevatorsQueue.getElevatorState(elevatorNo));
					if (i == elevatorsQueue.getElevatorState(elevatorNo).getFloor()) {
						data[i] = Integer.valueOf(MAX_FLOOR - i - 1).toString();
					}
				}
			}

			@Override
			public int getColumnCount() {
				return 1;
			}

			@Override
			public int getRowCount() {
				return data.length;
			}

			@Override
			public String getColumnName(int col) {
				return data[col];
			}

			@Override
			public Object getValueAt(int row, int col) {
				return data[row];
			}
		}

		private class MyCellRenderer extends DefaultTableCellRenderer {
			/**
			 *
			 */
			private static final long serialVersionUID = 189787934;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
						row, column);
				int currientFloor = elevatorsQueue.getElevatorState(elevatorNo).getFloor();
				// System.out.println("In renderer row = " + row +
				// " currientFloor =" + currientFloor);
				if (MAX_FLOOR - currientFloor - 1 == row) {
					cellComponent.setForeground(Color.WHITE);
					cellComponent.setBackground(Color.RED);
				} else {
					cellComponent.setBackground(Color.LIGHT_GRAY);
					cellComponent.setForeground(Color.WHITE);
				}
				if (isSelected && hasFocus) {
					cellComponent.setBackground(Color.CYAN);
					controllerQueue.addElevatorOrder(new ElevatorOrder(MAX_FLOOR - row - 1, elevatorNo));

				}
				return cellComponent;
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		JPanel elevatorControllerJpanel = new JPanel();
		ControllerQueue controllerQueue = new ControllerQueue();
		elevatorControllerJpanel.setLayout(new FlowLayout());
		int elevatorsNo = 3;
		ElevatorStatesQueue elevatorsQueu = new ElevatorStatesQueue(elevatorsNo);
		ElevatorState elevatorstate = new ElevatorState(5, ElevatorMovingDirections.DOWN, 5, 7, 10);
		// ElevatorState elevatorstate1 = new ElevatorState(1,
		// ElevatorMovingDirections.DOWN, 5, 7, 10);
		elevatorsQueu.setElevatorState(elevatorstate, 2);
		elevatorsQueu.setElevatorState(elevatorstate, 1);
		elevatorsQueu.setElevatorState(elevatorstate, 0);

		ControllerCommands controllerComands = new ControllerCommands(controllerQueue);
		// controllerComands.setOrdersFromFloors(cCommands);
		controllerComands.refreshControllerCommands();
		// 2. Optional: What happens when the frame closes?
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new FloorButtons(7, controllerQueue), BorderLayout.SOUTH);

		elevatorControllerJpanel.add(new ElevatorsView(7, 3, elevatorsQueu, controllerQueue));
		elevatorControllerJpanel.add(controllerComands);

		frame.add(elevatorControllerJpanel, BorderLayout.NORTH);
		frame.setLocationRelativeTo(null);

		// 4. Size the frame.
		frame.pack();

		// 5. Show it.

		frame.setVisible(true);
	}
}
