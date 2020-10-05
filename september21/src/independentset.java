/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class independentset {

    static long startTime = System.nanoTime();
    static ArrayList<Integer>[] adj;
    static long[][] dp;
    public static void solve() throws Exception {
        boolean debug = (true);
        FastScanner sc = new FastScanner("independentset.in", debug);
        FastWriter pw = new FastWriter("independentset.out", debug);
        int N= sc.ni();
        adj= new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        dp= new long[N][2];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i],1);
        }
        dfs(0,-1);
        pw.println((dp[0][0]+dp[0][1])%MOD);

        long endTime = System.nanoTime();
     //   System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static int MOD= (int)1e9+7;
    static void dfs(int cur, int par){
        for (int next:adj[cur]) {
            if(next==par) continue;
            dfs(next, cur);
            dp[cur][0]*= dp[next][0]+dp[next][1];
            dp[cur][1]*=dp[next][0];
            dp[cur][0]%=MOD;
            dp[cur][1]%=MOD;
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

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(null, () -> {
            try {
                solve();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "", 1 << 28);
        thread.start();
        thread.join();
    }

}