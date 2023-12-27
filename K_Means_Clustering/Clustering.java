import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Clustering {
    private int totalItems;
    private int[] depth;
    private int[] parent;
    private PriorityQueue<Edge> edges;

    /**
     * Initializes a new instance of the Clustering class.
     * This class provides functionality for clustering data points.
     */
    public Clustering() {
        this.edges = new PriorityQueue<>();
    }

    /**
     * Perform clustering on data points.
     *
     * @param k          The desired number of clusters.
     * @param distances  A 2D array representing the distances between data points.
     * @return The minimum distance among unmerged clusters.
     */
    public double compute(int k, double[][] distances) {
        totalItems = distances.length;
        depth = new int[totalItems];
        parent = new int[totalItems];
        Arrays.fill(depth, 0);

        // Populate the priority queue with edges sorted by weight (distance).
        for (int i = 0; i < totalItems; i++) {
            parent[i] = i;
            for (int j = i + 1; j < totalItems; j++) {
                edges.add(new Edge(i, j, distances[i][j]));
            }
        }

        // Merge clusters until the desired number of clusters 'k' is reached.
        while (totalItems > k) {
            Edge edge = edges.poll();
            if (find(edge.nodeA) != find(edge.nodeB)) {
                union(edge.nodeA, edge.nodeB);
                totalItems--;
            }
        }

        // Collect the minimum distances among unmerged clusters.
        List<Double> minDistances = new ArrayList<>();
        while (!edges.isEmpty()) {
            Edge edge = edges.poll();
            if (find(edge.nodeA) != find(edge.nodeB)) {
                minDistances.add(edge.weight);
            }
        }

        return minDistances.isEmpty() ? 0.0 : minDistances.get(0);
    }

    // Helper method to find the root of an element's cluster.
    private int find(int element) {
        if (parent[element] != element) {
            parent[element] = find(parent[element]);
        }
        return parent[element];
    }

    // Helper method to union two clusters.
    private void union(int element1, int element2) {
        int root1 = find(element1);
        int root2 = find(element2);

        if (root1 != root2) {
            if (depth[root1] < depth[root2]) {
                parent[root1] = root2;
            } else {
                parent[root2] = root1;
                if (depth[root1] == depth[root2]) {
                    depth[root1]++;
                }
            }
        }
    }

    // Represents an edge between two data points, used for sorting.
    static class Edge implements Comparable<Edge> {
        int nodeA;
        int nodeB;
        double weight;

        public Edge(int nodeA, int nodeB, double weight) {
            this.nodeA = nodeA;
            this.nodeB = nodeB;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
}
