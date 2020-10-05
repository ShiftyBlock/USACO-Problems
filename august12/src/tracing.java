/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class tracing {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here

    //Globals End
    public static void main(String[] args) throws IOException {
        boolean debug =! (true);
        FastScanner sc = new FastScanner("tracing.in", debug);
        FastWriter pw = new FastWriter("tracing.out", debug);
        int N= sc.ni();
        int T= sc.ni();
        int[] arr= new int[N];
        char[] temp=sc.next().toCharArray();
        for (int i = 0; i < N; i++) {
            arr[i]=temp[i]-'0';
        }
        Edge events[] = new Edge[T];
        for (int i = 0; i < T; i++) {
            events[i]= new Edge(sc.ni(), sc.ni()-1, sc.ni()-1);
        }
        Arrays.sort(events);
        int possible[]= new int[N];
        int minK=1<<30;
        int maxK=0;
        for (int i = 0; i <N ; i++) {
            for (int k = 0; k <N+2; k++) {
                if(valid(i,k,arr, events)){
                    minK= Math.min(k,minK);
                    maxK= Math.max(maxK, k);
                    possible[i]=1;
                }
            }
        }
        int resp=0;
        for (int num:possible) {
            if(num==1) resp++;
        }
        pw.print(resp+" ");
        pw.print(minK+" ");
        if(maxK>N) pw.println("Infinity");
        else pw.println(maxK);





        /* End Stub */
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    public static boolean valid(int p0, int K, int[] target, Edge events[]){
        int N= target.length;
        int T= events.length;
        int[] hoof= new int[N];
        int[] res=new int[N];
        res[p0]=1;
        Arrays.fill(hoof,K);
        for (int i = 0; i < events.length; i++) {
            int a= events[i].a;
            int b= events[i].b;
            if(res[a]==1 && res[b]==1){
                if(hoof[a]>0) hoof[a]--;
                if(hoof[b]>0) hoof[b]--;
            }
            if (res[a]==1 && res[b]==0){
                if(hoof[a]>0){
                    hoof[a]--;
                    res[b]=1;
                }
            }
            if(res[a]==0 && res[b]==1){
                if(hoof[b]>0){
                    hoof[b]--;
                    res[a]=1;
                }
            }
            if(res[a]==0 && res[b]==0) continue;
        }
        for (int i = 0; i < target.length; i++) {
            if(res[i]!=target[i]) return false;
        }
        return true;
    }


    static class Edge implements Comparable<Edge> {
        int t;
        int a;
        int b;
        public Edge(int t, int a, int b){
            this.t=t; this.a=a;
            this.b=b;
        }
        public int compareTo(Edge obj){
            return t-obj.t;
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