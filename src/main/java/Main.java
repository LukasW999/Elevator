import java.util.Scanner;

public class Main implements Runnable {

	private Elevator elevator;

	public void start() {

		elevator = new Elevator(10);
		new Thread(this).start();
		new Thread(elevator).start();
	}

	@Override
	/**
	 * This method takes care that the target floors can be entered with the
	 * console.
	 */
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		while (running) {

			String in = sc.next();
			System.out.println(in);
			// TODO
			if (in.equals("exit")) {
				running = false;
			} else if (in.charAt(0) == 'a') {
				elevator.call(Character.getNumericValue(in.charAt(1)));
			} else if (in.charAt(0) == 'i') {
				elevator.addTargetFloor(Character.getNumericValue(in.charAt(1)));
			} else {
				System.out.println("Falsche Eingabe. a für aussen i für innen und direkt dahinter die Etage bsp. i6");
			}
		}
		sc.close();
	}

	public static void main(String[] args) {
		new Main().start();
	}
}
