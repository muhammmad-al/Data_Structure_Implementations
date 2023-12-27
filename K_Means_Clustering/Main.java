import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("/example_file_path/input.txt"))) {

            // Read the input

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }

            // Parse the input

            int k = Integer.parseInt(lines.get(0));
            int n = Integer.parseInt(lines.get(1));
            double[][] distances = new double[n][n];

            for (int i = 0; i < n; i++) {
                Scanner s = new Scanner(lines.get(i+2));
                for (int j = 0; j < n; j++) {
                    distances[i][j] = s.nextDouble();
                }
            }

            // Call method and print the result
            Long start = System.currentTimeMillis();
            Clustering c = new Clustering();
            System.out.println(c.compute(k, distances));
            Long end = System.currentTimeMillis();
            System.out.println("time: " + ((end - start) / 1000.0));

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred when reading file");
        }
    }

}

