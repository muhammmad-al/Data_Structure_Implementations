public class SeamCarving {
    private int[] seam;

    /**
     * Method that performs the seam carving
     * @param image The input image represented as a 3D array.
     * @return The seam's weight.
     */
    public double compute(int[][][] image) {
        // do the seam carving
        double[][] energyMap = computeEnergy(image);
        int height = energyMap.length;
        int width = energyMap[0].length;
        double[][] dpMatrix = new double[height][width];

        // Initialize the bottom row of the dynamic programming matrix.
        for (int x = 0; x < width; x++) {
            dpMatrix[height - 1][x] = energyMap[height - 1][x];
        }

        // Fill in the dynamic programming matrix.
        for (int y = height - 2; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                double minEnergy = Double.MAX_VALUE;

                // Find the minimum energy path from the row below.
                for (int dx = -1; dx <= 1; dx++) {
                    int nx = x + dx;

                    if (nx >= 0 && nx < width) {
                        minEnergy = Math.min(minEnergy, dpMatrix[y + 1][nx]);
                    }
                }

                // Update the dynamic programming matrix.
                dpMatrix[y][x] = energyMap[y][x] + minEnergy;
            }
        }

        // Find the starting column of the seam with minimum energy.
        int startColumn = 0;
        double minSeamEnergy = Double.MAX_VALUE;

        for (int x = 0; x < width; x++) {
            if (dpMatrix[0][x] < minSeamEnergy) {
                minSeamEnergy = dpMatrix[0][x];
                startColumn = x;
            }
        }

        // Trace the seam path and calculate its weight.
        this.seam = new int[height];
        double seamWeight = 0.0;

        int x = startColumn;
        for (int y = 0; y < height; y++) {
            this.seam[y] = x;
            seamWeight += energyMap[y][x];

            if (y < height - 1) {
                double minEnergy = Double.MAX_VALUE;
                int nextX = x;

                // Find the next column for the seam.
                for (int dx = -1; dx <= 1; dx++) {
                    int nx = x + dx;
                    if (nx >= 0 && nx < width && dpMatrix[y + 1][nx] < minEnergy) {
                        minEnergy = dpMatrix[y + 1][nx];
                        nextX = nx;
                    }
                }

                x = nextX;
            }
        }

        return seamWeight;
    }

    /**
     * Get the seam, in order from top to bottom, where the top-left corner of the
     * image is denoted (0,0).
     *
     * Since the y-coordinate (row) is determined by the order, only return the x-coordinate
     *
     * @return The ordered list of x-coordinates (column number) of each pixel in the seam.
     */
    public int[] getSeam() {
        return this.seam;
    }

    /**
     * Compute the energy map of the input image.
     *
     * @param image The input image represented as a 3D array.
     * @return The energy map represented as a 2D array.
     */
    public double[][] computeEnergy(int[][][] image) {
        int height = image.length;
        int width = image[0].length;
        double[][] energyMap = new double[height][width];

        // Calculate the energy for each pixel in the image.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energyMap[i][j] = calculatePixelEnergy(i, j, image);
            }
        }

        return energyMap;
    }

    /**
     * Calculate the energy of a pixel at the specified coordinates.
     *
     * @param x     The x-coordinate (column) of the pixel.
     * @param y     The y-coordinate (row) of the pixel.
     * @param image The input image represented as a 3D array.
     * @return The energy of the pixel.
     */
    private double calculatePixelEnergy(int x, int y, int[][][] image) {
        double energy = 0;
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isWithinBounds(x + i, y + j, image.length, image[0].length)) {
                    if (!(i == 0 && j == 0)) {
                        energy += colorDistance(image[x][y], image[x + i][y + j]);
                        count++;
                    }
                }
            }
        }

        return energy / count;
    }

    /**
     * Check if a given coordinate is within the bounds of the image.
     *
     * @param x      The x-coordinate (column) to check.
     * @param y      The y-coordinate (row) to check.
     * @param height The height (number of rows) of the image.
     * @param width  The width (number of columns) of the image.
     * @return True if the coordinate is within bounds; false otherwise.
     */
    private boolean isWithinBounds(int x, int y, int height, int width) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    /**
     * Calculate the Euclidean color distance between two RGB colors.
     *
     * @param color1 The first RGB color represented as an array [R, G, B].
     * @param color2 The second RGB color represented as an array [R, G, B].
     * @return The Euclidean color distance between the two colors.
     */
    private double colorDistance(int[] color1, int[] color2) {
        int redDiff = color1[0] - color2[0];
        int greenDiff = color1[1] - color2[1];
        int blueDiff = color1[2] - color2[2];

        return Math.sqrt(redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
    }
}
