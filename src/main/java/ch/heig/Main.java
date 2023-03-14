// Authors: Fazlija Ylli, Escher Ian

package ch.heig;

public class Main {
    public static void main(String[] args) {

        // Verify that the first argument is an int
        if(!args[0].matches("\\d+")) {
            System.out.println("Usage: java -jar <jarfile> <number of timers>");
            return;
        }

        int numberOfTimers = Integer.parseInt(args[0]);

        if (args.length != 1 || numberOfTimers <= 0 || numberOfTimers > 9) {
            System.out.println("Usage: java -jar <jarfile> <number of timers>");
            return;
        }

        ControlFrame frame = new ControlFrame("Panneau de contrôle", Integer.parseInt(args[0]));
        frame.show();
    }
}