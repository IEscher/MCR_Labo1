package ch.heig;

public class Main {
    public static void main(String[] args) {
        // Verify that the first argument is an int
        if (args.length != 1 || !args[0].matches("\\d+")) {
            System.out.println("Usage: java -jar <jarfile> <number of timers>");
            return;
        }

        ControlFrame frame = new ControlFrame("Panneau de contrôle", Integer.parseInt(args[0]));
        frame.show();
    }
}