import java.util.ArrayList;

public class Elevator implements Runnable {

	private int maxFloor;
	private int currentFloor = 0;
	private ArrayList<Integer> targetFloors = new ArrayList<Integer>();
	private ArrayList<Integer> calledFloors = new ArrayList<Integer>();
	private boolean moveDirection;

	/**
	 * the constructor for an elevator. Also specifies how many floors the building
	 * has.
	 * 
	 * @param maxFloor The number of floors from the building.
	 */
	public Elevator(int maxFloor) {
		this.maxFloor = maxFloor;
		System.out.println("Das Gebäude hat " + this.maxFloor + " Etagen");
	}

	@Override
	/**
	 * Controls the elevator. When an input has been made run() executes the
	 * required methods to make the elevator move correctly.
	 */
	public void run() {
		while (!targetFloors.isEmpty() || !calledFloors.isEmpty()) {
			int targetFloor = calculateTarget();
			move(targetFloor);
			if (targetFloor != -1) {
				System.out.println("Der Aufzug befindent sich nun in der " + currentFloor + ". Etage");
				System.out.println(targetFloor);
			}
			if (arrivedAtDestination(targetFloor)) {
				while (calledFloors.contains(Integer.valueOf(targetFloor))) {
					calledFloors.remove(Integer.valueOf(targetFloor));
				}
				while (targetFloors.contains(Integer.valueOf(targetFloor))) {
					targetFloors.remove(Integer.valueOf(targetFloor));
				}

				System.out.println("Sie haben ihr Ziel erreicht. Bitte steigen sie aus!");
			}
		}
	}

	/**
	 * Moves the elevator 1 floor with a travel time of 5 seconds.
	 * 
	 * @param targetFloor The destination floor.
	 */
	private void move(int targetFloor) {
		if (targetFloor == -1) {
			return;
		}
		if (targetFloor > maxFloor || targetFloor < 0) {
			System.out.println("So eine Etage existiert nicht. Das Gebäude hat nur " + maxFloor + " Etagen");
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (targetFloor > currentFloor) {
			currentFloor++;
		} else {
			currentFloor--;
		}

	}

	/**
	 * Adds the floor to the list of called floors.
	 * 
	 * @param yourFloor The floor to be added.
	 */
	public void call(int yourFloor) {
		calledFloors.add(yourFloor);
		System.out.println("Sie haben den Aufzug zu Etage " + yourFloor + " gerufen");
		run();
	}

	/**
	 * Adds the floor to the list of destination floors.
	 * 
	 * @param yourTargetFloor The floor to be added.
	 */
	public void addTargetFloor(int yourTargetFloor) {
		targetFloors.add(yourTargetFloor);
		System.out.println("Sie haben Etage " + yourTargetFloor + " ausgewählt");
		run();
	}

	/**
	 * Calculates if a called or a destination floor will be the next floor where
	 * the elevator stops.
	 * 
	 * @return The destination Floor.
	 */
	private int calculateTarget() {
		int called = calculateCalledTarget();
		int target = calculateAimedTarget();
		if (target == -1 && called == -1) {
			return -1;
		} else if (target == -1) {
			return called;
		} else if (called == -1) {
			return target;
		} else {
			if (moveDirection) {
				if (target < called) {
					return target;
				} else {
					return called;
				}
			} else {
				if (target > called) {
					return target;
				} else {
					return called;
				}
			}
		}
	}

	/**
	 * Calculates the next possible destination floor.
	 * 
	 * @return The next possible destination Floor.
	 */
	private int calculateAimedTarget() {
		int targetFloor = -1;
		if (!targetFloors.isEmpty()) {
			targetFloor = targetFloors.get(0);
		}
		if (targetFloor != -1) {
			if (currentFloor < targetFloor) {
				moveDirection = true;
				for (Integer aimedFloor : targetFloors) {
					if (aimedFloor < targetFloor && aimedFloor > currentFloor) {
						targetFloor = aimedFloor;

					}
				}
			} else if (currentFloor > targetFloor) {
				moveDirection = false;
				for (Integer aimedFloor : targetFloors) {
					if (aimedFloor > targetFloor && aimedFloor < currentFloor) {
						targetFloor = aimedFloor;
					}
				}
			}
		}
		return targetFloor;
	}

	/**
	 * Calculates the next possible called floor.
	 * 
	 * @return The next possible destination Floor.
	 */
	private int calculateCalledTarget() {
		int targetFloor = -1;
		if (!calledFloors.isEmpty()) {
			targetFloor = calledFloors.get(0);
		}
		if (targetFloor != -1) {
			if (currentFloor < targetFloor) {
				for (Integer calledFloor : calledFloors) {
					if (calledFloor < targetFloor && calledFloor > currentFloor) {
						targetFloor = calledFloor;

					}
				}
			} else if (currentFloor > targetFloor) {
				for (Integer calledFloor : calledFloors) {
					if (calledFloor > targetFloor && calledFloor < currentFloor) {
						targetFloor = calledFloor;

					}
				}
			}
		}
		return targetFloor;
	}

	/**
	 * Checks if the elevator has reached its destination floor.
	 * 
	 * @param targetFloor The destination Floor.
	 * @return true for yes and false if not.
	 */
	private boolean arrivedAtDestination(int targetFloor) {
		if (currentFloor == targetFloor) {
			return true;
		} else {
			return false;
		}
	}

}
