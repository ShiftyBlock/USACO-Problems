/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class message {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here

    //Globals End
    public static void main(String[] args) throws Exception {
        boolean debug = (true);
        FastScanner sc = new FastScanner("message.in", debug);
        FastWriter pw = new FastWriter("message.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        ArrayList<Integer>[] adj= new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i]= new ArrayList<>();
        }
        for (int i = 0; i <M ; i++) {
            int a= sc.ni()-1; int b= sc.ni()-1;
            adj[a].add(b); adj[b].add(a);
        }
        ArrayDeque<Integer> dq= new ArrayDeque<>();
        Pair[] dist= new Pair[N];
        Arrays.fill(dist, new Pair(1<<30, -1));
        dq.offer(0);
        dist[0]= new Pair(1,-1);
        while(dq.size()>0){
            int cur= dq.poll();
            for (int next: adj[cur]) {
                if(dist[next].a> dist[cur].a+1){
                    dist[next]= new Pair(dist[cur].a+1, cur);
                    dq.offer(next);
                }
            }
        }
        if(dist[N-1].a==1<<30) pw.println("IMPOSSIBLE");
        else{
            pw.println(dist[N-1].a);
            ArrayDeque<Integer> path= new ArrayDeque<>();
            int i=N-1;
            while(dist[i].b!=-1) {
                path.addFirst(dist[i].b);
                i= dist[i].b;
            }
            while(path.size()>0) pw.print(path.poll()+1+" ");
            pw.println(N);
        }




        /* End Stub */
        long endTime = System.nanoTime();
      //  System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
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
        InputStream dis;
        byte[] buffer = new byte[1 << 17];
        int pointer = 0;

        public FastScanner(String fileName, boolean debug) throws Exception {
            if (debug) dis = System.in;
            else dis = new FileInputStream(fileName);
        }

        int ni() throws Exception {
            int ret = 0;

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            boolean negative = false;
            if (b == '-') {
                negative = true;
                b = nextByte();
            }
            while (b >= '0' && b <= '9') {
                ret = 10 * ret + b - '0';
                b = nextByte();
            }

            return (negative) ? -ret : ret;
        }

        long nl() throws Exception {
            long ret = 0;

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            boolean negative = false;
            if (b == '-') {
                negative = true;
                b = nextByte();
            }
            while (b >= '0' && b <= '9') {
                ret = 10 * ret + b - '0';
                b = nextByte();
            }

            return (negative) ? -ret : ret;
        }

        byte nextByte() throws Exception {
            if (pointer == buffer.length) {
                dis.read(buffer, 0, buffer.length);
                pointer = 0;
            }
            return buffer[pointer++];
        }

        String next() throws Exception {
            StringBuffer ret = new StringBuffer();

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            while (b > ' ') {
                ret.appendCodePoint(b);
                b = nextByte();
            }

            return ret.toString();
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