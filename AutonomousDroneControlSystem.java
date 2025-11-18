
// Autonomous Drone Control and Object Tracking System
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


abstract class DroneComponent {
    protected String componentName;

    public DroneComponent(String name) {
        this.componentName = name;
    }

    public abstract void statusCheck();
    public String getComponentName() {
        return componentName;
    }
}

class Sensor extends DroneComponent {
    private boolean active;

    public Sensor(String name) {
        super(name);
        this.active = true;
    }

    @Override
    public void statusCheck() {
        System.out.println("Sensor " + componentName + " status: " + (active ? "Active" : "Inactive"));
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}


class Drone {
    private String droneID;
    private double batteryLevel;
    private boolean isFlying;
    private List<Sensor> sensors;
    private int x, y;

    public Drone(String droneID) {
        this.droneID = droneID;
        this.batteryLevel = 100.0;
        this.isFlying = false;
        this.sensors = new ArrayList<>();
        this.x = 0;
        this.y = 0;
    }


    public double getBatteryLevel() {
        return batteryLevel;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addSensor(Sensor s) {
        sensors.add(s);
    }

    public void showSensors() {
        sensors.forEach(Sensor::statusCheck);
    }


    public void startFlight() throws Exception {
        if (batteryLevel < 20) {
            throw new Exception("Battery too low to start flight!");
        }
        isFlying = true;
        System.out.println("Drone " + droneID + " started flying...");
    }

    public void landDrone() {
        isFlying = false;
        System.out.println("Drone landed safely.");
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
        System.out.println("Drone moved to coordinates: (" + x + ", " + y + ")");
    }

    public void reduceBattery(double amount) {
        batteryLevel -= amount;
        if (batteryLevel < 0) batteryLevel = 0;
    }
}

class DroneTrackerThread extends Thread {
    private Drone drone;
    private GUI gui;
    private boolean running;

    public DroneTrackerThread(Drone drone, GUI gui) {
        this.drone = drone;
        this.gui = gui;
        this.running = true;
    }

    @Override
    public void run() {
        Random rand = new Random();
        while (running) {
            try {
                if (drone.isFlying()) {
                    // Simulate random movement
                    int newX = drone.getX() + rand.nextInt(20) - 10;
                    int newY = drone.getY() + rand.nextInt(20) - 10;
                    drone.moveTo(newX, newY);
                    drone.reduceBattery(0.5);
                    gui.updateDronePosition(newX, newY, drone.getBatteryLevel());
                }

                Thread.sleep(1000); // update every second
            } catch (InterruptedException e) {
                System.out.println("Tracker thread interrupted.");
            }
        }
    }

    public void stopTracking() {
        running = false;
    }
}


class GUI extends JFrame {
    private JLabel lblBattery;
    private JLabel lblStatus;
    private Drone drone;
    private DroneTrackerThread trackerThread;
    private JPanel canvas;

    public GUI(Drone drone) {
        this.drone = drone;
        setTitle("Autonomous Drone Control & Object Tracking System");
        setSize(600, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.setLayout(new GridLayout(1, 3));

        lblBattery = new JLabel("Battery: 100%", SwingConstants.CENTER);
        lblBattery.setForeground(Color.WHITE);

        lblStatus = new JLabel("Status: Idle", SwingConstants.CENTER);
        lblStatus.setForeground(Color.WHITE);

        JButton btnStart = new JButton("Start Flight");
        JButton btnLand = new JButton("Land");
        JButton btnExit = new JButton("Exit");

        topPanel.add(lblBattery);
        topPanel.add(lblStatus);
        topPanel.add(btnExit);


        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillOval(280 + drone.getX(), 200 + drone.getY(), 20, 20); // draw drone
            }
        };
        canvas.setBackground(Color.LIGHT_GRAY);

        // ----------- BOTTOM PANEL -------------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.GRAY);
        bottomPanel.add(btnStart);
        bottomPanel.add(btnLand);

        add(topPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> {
            try {
                drone.startFlight();
                lblStatus.setText("Status: Flying...");
                trackerThread = new DroneTrackerThread(drone, this);
                trackerThread.start();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLand.addActionListener(e -> {
            drone.landDrone();
            lblStatus.setText("Status: Landed");
            if (trackerThread != null) trackerThread.stopTracking();
        });

        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    // Method called from DroneTrackerThread to refresh UI
    public void updateDronePosition(int x, int y, double battery) {
        SwingUtilities.invokeLater(() -> {
            lblBattery.setText(String.format("Battery: %.1f%%", battery));
            canvas.repaint();
        });
    }
}


public class AutonomousDroneControlSystem {
    public static void main(String[] args) {
        // Create drone and sensors
        Drone drone = new Drone("DRN-01");
        drone.addSensor(new Sensor("Camera"));
        drone.addSensor(new Sensor("GPS"));
        drone.addSensor(new Sensor("Infrared"));

        // Show sensor status
        drone.showSensors();

        // Launch GUI
        SwingUtilities.invokeLater(() -> new GUI(drone));
    }
}
