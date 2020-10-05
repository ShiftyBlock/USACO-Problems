/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class timeline {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("timeline.in", debug);
        FastWriter pw = new FastWriter("timeline.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        int C= sc.ni();
        int[] time= new int[N];
        adj= new ArrayList[N];
        visited= new boolean[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
        }
        cost= new int[N];
        for (int i = 0; i < N; i++) {
            cost[i]= sc.ni();
        }
        for (int i = 0; i < C; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            int w= sc.ni();
            adj[a].add(new Pair(w,b));
        }
        for (int i = 0; i < N; i++) {
            if(!visited[i]) dfs(i,-1);
        }
        res= new int[N];
        while(topo.size()>0) {
            int cur= topo.poll();
            if(res[cur]!=0) continue;
            res[cur]=cost[cur];
            ans(cur,-1);
        }
        for (int i = 0; i < N; i++) {
            pw.println(res[i]);
        }


        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static ArrayDeque<Integer> topo= new ArrayDeque<>();
    static ArrayList<Pair>[] adj;
    static boolean visited[];
    static int[] res, cost;
    static void ans(int cur, int par){
        for (Pair next : adj[cur]) {
            if (next.b == par ) continue;
            if(res[next.b]==0) {
                res[next.b]= Math.max(cost[next.b], res[cur]+next.a);
                ans(next.b, cur);
            }
            else if(res[next.b]<res[cur]+next.a){
                res[next.b]= res[cur]+next.a;
                ans(next.b, cur);
            }
        }
    }
    static void dfs(int cur, int par) {
        if (visited[cur]) return;
        visited[cur] = true;
        for (Pair next : adj[cur]) {
            if (next.b == par || visited[next.b]) continue;
            dfs(next.b, cur);
        }
        topo.addFirst(cur);
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