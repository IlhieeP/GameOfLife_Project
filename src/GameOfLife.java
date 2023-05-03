// to compile :  javac GameOfLife.java
// to run : java GameOfLife.java w=20 h=40 g=2 s=1000  p=rnd
// Do not forget to use cd and the correct directory
import java.util.Random;

public class GameOfLife {

    public static void main(String[] args) {


        int width = 0;
        int height = 0;
        int generations = 0;
        int speed = 0;
        String population = "";

        //split the code variables for command line
        for (String arg : args) {
            String[] parts = arg.split("=");
            if (parts[0].equals("w")) {
                width = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("h")) {
                height = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("g")) {
                generations = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("s")) {
                speed = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("p")) {
                population = parts[1];
            }
        }
        if (width == 0 || height == 0 || generations == 0 || speed == 0 || population.equals("")) {
            System.out.println("Usage: java GameOfLife w=<width> h=<height> g=<generations> s=<speed> p=<population>");
            return;
        }
        if (width != 10 && width != 20 && width != 40 && width != 80) {
            System.out.println("Width must be 10, 20, 40, or 80");
            return;
        }
        if (height != 10 && height != 20 && height != 40) {
            System.out.println("Height must be 10, 20, or 40");
            return;
        }
        if (generations < 0) {
            System.out.println("Generations must be >= 0");
            return;
        }
        if (speed < 250 || speed > 1000) {
            System.out.println("Speed must be between 250 and 1000");
            return;
        }

        // Initialize grid
        int[][] grid = new int[height][width];
        if (population.equals("rnd")) {
            Random rand = new Random();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    grid[y][x] = rand.nextInt(2);
                }
            }
        }
        else {
            String[] sections = population.split("#");
            for (int i = 0; i < sections.length; i++) {
                String section = sections[i];
                for (int j = 0; j < section.length(); j++) {
                    grid[i][j] = Integer.parseInt(section.substring(j, j + 1));
                }
            }
        }
        // run the game
        int aliveCells = 0;
        for (int generation = 1; generation <= generations; generation++) {

            // print generation
            aliveCells = 0;
            System.out.print("+");
            for (int x = 0; x < width; x++) {
                System.out.print("-");
            }
            System.out.println("+");
            for (int y = 0; y < height; y++) {
                System.out.print("|");
                for (int x = 0; x < width; x++) {
                    if (grid[y][x] == 1) {
                        System.out.print("X");
                        aliveCells++;
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println("|");
            }
            System.out.print("+");
            for (int x = 0; x < width; x++) {
                System.out.print("-");
            }
            System.out.println("+");
            System.out.println("--- Generation " + generation + " ---");
            System.out.println("--- Alive cells: " + aliveCells + " ---");
            System.out.println();

            // update grid
            int[][] newGrid = new int[height][width];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int neighbors = 0;

                    for (int yy = -1; yy <= 1; yy++) {
                        for (int xx = -1; xx <= 1; xx++) {
                            if (xx == 0 && yy == 0) {

                                continue;
                            }
                            int nx = x + xx;
                            int ny = y + yy;
                            if (nx < 0 || nx >= width || ny < 0 || ny >= height) {
                                continue;
                            }
                            if (grid[ny][nx] == 1) {
                                neighbors++;
                            }
                        }
                    }
                    if (grid[y][x] == 1 && neighbors >= 2 && neighbors <= 3) {
                        newGrid[y][x] = 1;
                    } else if (grid[y][x] == 0 && neighbors == 3) {
                        newGrid[y][x] = 1;
                    }
                }
            }
            grid = newGrid;



            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
