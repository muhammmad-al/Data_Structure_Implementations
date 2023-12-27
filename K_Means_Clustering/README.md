# Clustering Algorithm

## Problem

Clustering is a fundamental unsupervised machine learning technique used to group similar data points together. Given a set of data points and a distance matrix representing the dissimilarity between data points, the goal is to partition the data into 'k' clusters in such a way that the intra-cluster distances are minimized.

## Solution

This Java project provides an implementation of a clustering algorithm that aims to find 'k' clusters in a set of data points. The algorithm takes as input a 2D distance matrix representing the pairwise distances between data points. It then proceeds to merge clusters iteratively until the desired number of clusters ('k') is reached.

### Algorithm Steps:
1. Initialize clusters for each data point.
2. Populate a priority queue with edges sorted by weight (distance).
3. Merge clusters until the number of clusters is reduced to 'k':
   - Dequeue the edge with the smallest weight.
   - If the nodes connected by the edge belong to different clusters, merge the clusters.
   - Repeat until 'k' clusters remain.
4. Collect the minimum distances among unmerged clusters.

## Usage

To use the clustering algorithm provided by the code, follow these steps:

1. **Prepare Input Data**: Create a text file named "input.txt" containing the necessary input data. This file should include the desired number of clusters (`k`), the number of data points (`n`), and a 2D array representing the distances between data points. Each row of the distance matrix should contain space-separated values.

2. **Execute the Code**: Run the program by executing the `Main` class. Ensure that you have specified the correct path to the input file within the code.

3. **Results**: The algorithm will read the input, perform the clustering process, and display the minimum distance among unmerged clusters. Additionally, it will provide the execution time in seconds.

By following these steps and providing the appropriate input file, you can utilize the clustering algorithm to group data points into clusters while minimizing inter-cluster distances.

## Efficiency

The efficiency of this algorithm primarily depends on the number of data points and the choice of 'k'. Here are some considerations:
- The algorithm has a time complexity of O(n^2 log n) for sorting the edges.
- Merging clusters has a time complexity of O(n) with the union-find data structure.
- The final complexity depends on the number of clusters 'k' desired.
- The space complexity is O(n^2) for storing the distance matrix.
  
Therefore, the overall time complexity is O(n^2 * log(n)).
