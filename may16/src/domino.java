/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class domino {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here

    //Globals End
    public static void main(String[] args) throws IOException {
        FastScanner sc = new FastScanner("domino.in", true);
        FastWriter pw = new FastWriter("domino.out", true);
        int rows= sc.ni();
        int cols= sc.ni();
        // 1 based indexing
        int[][] matrix= new int[rows+1][cols+1];
        int[][] hor= new int[rows+1][cols+1];
        int[][] ver= new int[rows+1][cols+1];
        int[][] dp= new int[rows+1][cols+1];
        for (int i = 1; i <= rows; i++) {
            String line= sc.next();
            int j=1;
            for (char c : line.toCharArray()) {
                if (c=='#') matrix[i][j]=-1;
                else matrix[i][j]=1;
                j++;
            }
        }
        for (int i = 1; i <=rows; i++) {
            for (int j = 1; j <=cols ; j++) {
                dp[i][j]= dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1];
                if (matrix[i][j]==1){
                    if (matrix[i-1][j]==1){
                        dp[i][j]++;
                        ver[i][j]=1;
                    }
                    if (matrix[i][j-1]==1){
                        dp[i][j]++;
                        hor[i][j]=1;
                    }
                }
            }
        }
        for (int i = 1; i <=rows; i++) {
            for (int j = 1; j <=cols ; j++) {
                ver[i][j]= ver[i][j]+ ver[i-1][j]+ ver[i][j-1]- ver[i-1][j-1];
                hor[i][j]= hor[i][j]+ hor[i-1][j] + hor[i][j-1]- hor[i-1][j-1];
            }
        }


        int tcs=sc.ni();
        while (tcs-->0){
            int a1= sc.ni();
            int b1= sc.ni();
            int a2= sc.ni();
            int b2= sc.ni();
            int res=0;
            res+= dp[a2][b2]- dp[a1-1][b2]- dp[a2][b1-1] + dp[a1-1][b1-1];
           /*
            // x1, y1 -> x2, y1
            for (int row=a1;  row<=a2 ; row++) {
                if (matrix[row][b1]==1 && matrix[row][b1-1]==1) res--;
            }
            // x1, y1 -> x1, y2
            for (int col=b1;  col<=b2 ; col++) {
                if (matrix[a1][col]==1 && matrix[a1][col-1]==1) res--;
            }
            */
            // x1, y1 -> x2, y1
            res-= hor[a2][b1]- hor[a1-1][b1]- hor[a2][b1-1] + hor[a1-1][b1-1];
            // x1, y1-> x1, y2
            res-= ver[a1][b2]- ver[a1-1][b2]- ver[a1][b1-1] + ver[a1-1][b1-1];
            pw.println(res);
        }





        /* End Stub */
        long endTime = System.nanoTime();
        //System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }


    static class Edge implements Comparable<Edge> {
        int w;
        int a;
        int b;

        public Edge(int w, int a, int b) {
            this.w = w;
            this.a = a;
            this.b = b;

        }

        public int compareTo(Edge obj) {
            if (obj.w == w) {
                if (obj.a == a) {
                    return b - obj.b;
                }
                return a - obj.a;
            } else return w - obj.w;
        }
    }

    static class Pair implements Comparable<Pair> {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int compareTo(Pair obj) {
            if (obj.a == a) {
                return b - obj.b;
            } else return a - obj.a;
        }
    }

    static class SegmentTree {
        public long[] arr;
        public long[] tree;
        public int N;

        //Zero initialization
        public SegmentTree(int n) {
            N = n;
            arr = new long[N];
            tree = new long[4 * N + 1];
        }

        public long query(int treeIndex, int lo, int hi, int i, int j) {
            // query for arr[i..j]
            if (lo > j || hi < i)
                return 0;
            if (i <= lo && j >= hi)
                return tree[treeIndex];
            int mid = lo + (hi - lo) / 2;

            if (i > mid)
                return query(2 * treeIndex + 2, mid + 1, hi, i, j);
            else if (j <= mid)
                return query(2 * treeIndex + 1, lo, mid, i, j);

            long leftQuery = query(2 * treeIndex + 1, lo, mid, i, mid);
            long rightQuery = query(2 * treeIndex + 2, mid + 1, hi, mid + 1, j);

            // merge query results
            return merge(leftQuery, rightQuery);
        }

        public void update(int treeIndex, int lo, int hi, int arrIndex, long val) {
            if (lo == hi) {
                tree[treeIndex] = val;
                arr[arrIndex] = val;
                return;
            }

            int mid = lo + (hi - lo) / 2;

            if (arrIndex > mid)
                update(2 * treeIndex + 2, mid + 1, hi, arrIndex, val);
            else if (arrIndex <= mid)
                update(2 * treeIndex + 1, lo, mid, arrIndex, val);

            // merge updates
            tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]);
        }

        public long merge(long a, long b) {
            return (a + b);
        }
    }

    static class djset {
        int N;
        int[] parent;

        // Creates a disjoint set of size n (0, 1, ..., n-1)
        public djset(int n) {
            parent = new int[n];
            N = n;
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        public int find(int v) {

            // I am the club president!!! (root of the tree)
            if (parent[v] == v) return v;

            // Find my parent's root.
            int res = find(parent[v]);

            // Attach me directly to the root of my tree.
            parent[v] = res;
            return res;
        }

        public boolean union(int v1, int v2) {

            // Find respective roots.
            int rootv1 = find(v1);
            int rootv2 = find(v2);

            // No union done, v1, v2 already together.
            if (rootv1 == rootv2) return false;

            // Attach tree of v2 to tree of v1.
            parent[rootv2] = rootv1;
            return true;
        }


    }

    static class FastScanner {
        public BufferedReader br;
        public StringTokenizer st;
        public InputStream is;

        public FastScanner(String name, boolean debug) throws IOException {
            if (debug) {
                is = System.in;
            } else {
                is = new FileInputStream(name);
            }
            br = new BufferedReader(new InputStreamReader(is), 32768);
            st = null;
        }


        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int ni() {
            return Integer.parseInt(next());
        }

        public long nl() {
            return Long.parseLong(next());
        }

        public double nd() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return str;
        }
    }

    static class FastWriter {
        public PrintWriter pw;

        public FastWriter(String name, boolean debug) throws IOException {
            if (debug) {
                pw = new PrintWriter(System.out);
            } else {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(name))));
            }
        }

        public void println(Object text) {
            pw.println(text);
        }

        public void print(Object text) {
            pw.print(text);
        }

        public void close() {
            pw.close();
        }

        public void flush() {
            pw.flush();
        }
    }

}