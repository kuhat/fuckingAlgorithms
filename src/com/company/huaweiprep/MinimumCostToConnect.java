package com.company.huaweiprep;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @projectName: algorithms
 * @package: com.company.huaweiprep
 * @className: MinimumCostToConnect
 * @author: Danny
 * @description: TODO
 * @date: 2023/2/10 16:21
 * @version: 1.0
 */
public class MinimumCostToConnect {
    /**
     * Given a 2D array houses[][] consisting of N 2D coordinates {x, y} where each coordinate represents the location
     * of each house, the task is to find the minimum cost to connect all the houses of the city.
     *
     * Cost of connecting two houses is the Manhattan Distance between the two points (xi, yi) and (xj, yj) i.e.,
     * |xi – xj| + |yi – yj|, where |p| denotes the absolute value of p.
     *
     * Input: houses[][] = [[0, 0], [2, 2], [3, 10], [5, 2], [7, 0]]
     * Output: 20
     *
     * Connect house 1 (0, 0) with house 2 (2, 2) with cost = 4
     * Connect house 2 (2, 2) with house 3 (3, 10) with cost =9
     * Connect house 2 (2, 2) with house 4 (5, 2) with cost =3
     * At last, connect house 4 (5, 2) with house 5 (7, 0) with cost 4.
     * All the houses are connected now.
     * The overall minimum cost is 4 + 9 + 3 + 4 = 20.
     */

    class DSU {

        int parent[];
        int rank[];

        // Constructor to initialize DSU
        DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = -1;
                rank[i] = 1;
            }
        }

        // Utility function to find set of an
        // element v using path compression
        // technique
        int find_set(int v) {
            // If v is the parent
            if (parent[v] == -1)
                return v;
            // Otherwise, recursively
            // find its parent
            return parent[v] = find_set(parent[v]);
        }

        // Function to perform union
// of the sets a and b
        void union_sets(int a, int b) {

            // Find parent of a and b
            int p1 = find_set(a);
            int p2 = find_set(b);

            // If parent are different
            if (p1 != p2) {

                // Swap Operation
                if (rank[p1] > rank[p2]) {
                    parent[p2] = p1;
                    rank[p1] += rank[p2];
                } else {
                    parent[p1] = p2;
                    rank[p2] += rank[p1];
                }
            }
        }
    }

    void MST(int houses[][], int n)
    {
        int ans = 0;
        ArrayList<int[]> edges = new ArrayList<>();

        // Traverse each coordinate
        for(int i = 0; i < n; i++)
        {
            for(int j = i + 1; j < n; j++)
            {

                // Find the Manhattan distance
                int p = Math.abs(houses[i][0] -
                        houses[j][0]);

                p += Math.abs(houses[i][1] -
                        houses[j][1]);

                // Add the edges
                edges.add(new int[]{ p, i, j });
            }
        }

        // Sorting arraylist using custome comparator
        // on the basis of weight i.e first element in
        // array object stored in Arraylist
        Collections.sort(edges, (a,b)-> a[0] - b[0]);

        // Calling DSU class
        DSU d = new DSU(n);
        for(int i = 0; i < edges.size(); i++)
        {
            int from = edges.get(i)[1];
            int to = edges.get(i)[2];
            // Checking if they lie in different component
            // or not i.e they have same parent or not in
            // DSU
            if (d.find_set(from) != d.find_set(to))
            {
                // Calling union_sets
                d.union_sets(from, to);
                ans += edges.get(i)[0];
            }
        }
        // Printing the minimum cost
        System.out.println(ans);
    }

    public static void main(String[] args) {

    }


}
