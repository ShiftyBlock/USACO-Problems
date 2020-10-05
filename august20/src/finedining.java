/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class finedining {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here

    //Globals End
    public static void main(String[] args) throws IOException {
        boolean debug =!(true);
        FastScanner sc = new FastScanner("dining.in", debug);
        FastWriter pw = new FastWriter("dining.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        int K= sc.ni();
        ArrayList<Pair>[] adj= new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i]= new ArrayList<>();
        }
        for (int i = 0; i <M; i++) {
            int a= sc.ni()-1; int b= sc.ni()-1;
            int w= sc.ni();
            adj[a].add(new Pair(w,b));
            adj[b].add(new Pair(w,a));
        }
        int[] distN= new int[N];
        Arrays.fill(distN,1<<30);
        PriorityQueue<Pair> pq= new PriorityQueue<>();
        distN[N-1]=0;
        pq.offer(new Pair(0,N-1));
        while(!pq.isEmpty()){
            Pair cur= pq.poll();
            if(distN[cur.b]<cur.a) continue;
            for(Pair next: adj[cur.b]){
                if(next.a+distN[cur.b]<distN[next.b]){
                    distN[next.b]= next.a+distN[cur.b];
                    pq.offer(new Pair(distN[next.b], next.b));
                }
            }
        }
        //read in haybales

        int[] distH= new int[N];
        Arrays.fill(distH,1<<30);
        Pair[] cheap= new Pair[K];
        for (int i = 0; i < K; i++) {
            int idx=sc.ni()-1;
            int w= sc.ni();
            cheap[i]= new Pair(distN[idx]-w,idx);
        }
        Arrays.sort(cheap);
        for (int i = 0; i < K; i++) {
            if(distH[cheap[i].b]!=1<<30) continue;
            distH[cheap[i].b]=cheap[i].a;
            pq.offer(cheap[i]);
        }
        while(!pq.isEmpty()){
            Pair cur= pq.poll();
            if(distH[cur.b] < cur.a) continue;
            for(Pair next: adj[cur.b]){
                if(next.a+distH[cur.b]<distH[next.b]){
                    distH[next.b]= next.a+distH[cur.b];
                    pq.offer(new Pair(distH[next.b],next.b));
                }
            }
        }

        for (int i = 0; i < N-1; i++) {
            if(distH[i]<=distN[i]) pw.println(1);
            else pw.println(0);
        }



        /* End Stub */
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
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