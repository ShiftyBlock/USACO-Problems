/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class delegation {

    static ArrayList<Integer>[] adj, dp;
    static int cur[];

    static int submit= 1;

    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("deleg.in", submit);
        FastWriter pw = new FastWriter("deleg.out", submit);
        int N= sc.ni();
        adj= new ArrayList[N]; dp= new ArrayList[N]; cur= new int[N]; Arrays.fill(cur, 1);
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
            dp[i]= new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            adj[a].add(b); adj[b].add(a);
        }
        dfs(0,-1);
        for (int i = 1; i < N; i++) {
            pw.print(valid(i));
        }

        long endTime = System.nanoTime();System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static void dfs(int c, int p){
        for (int n: adj[c]) {
           if(n==p) continue;
           dfs(n,c);
           cur[c]+=cur[n];
           dp[c].add(cur[n]);
        }
        if(cur[c]!=adj.length) dp[c].add(adj.length-cur[c]);
    }
    static int valid(int K){
        if((adj.length-1)%K!=0) return 0;
        int[] val= new int[adj.length];
        for (int i = 0; i < adj.length; i++) {
            int count=0;
            for(int next: dp[i]){
                int len= next%K;
                if(len==0) continue;
                if(val[K-len]!=0){
                    val[K-len]--; count--;
                }
                else {
                    val[len]++; count++;
                }
            }
            if(count!=0) return 0;
        }
        return 1;
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

        public FastScanner(String fileName, int s) throws Exception {
            if (s==0) dis = System.in;
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

        public FastWriter(String name, int s) throws IOException {
            if (s==0) {
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
    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        if (submit==0) {
            Thread thread = new Thread(null, () -> { try { solve(); } catch (Exception e) {e.printStackTrace(); } }, "", 1 << 28);thread.start();thread.join();
        }
        else solve();

    }
}