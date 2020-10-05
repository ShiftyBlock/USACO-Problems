/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class snowboots {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("snowboots.in", debug);
        FastWriter pw = new FastWriter("snowboots.out", debug);
        int N= sc.ni();
        int K= sc.ni();
        int[] tiles= new int[N];
        Pair[] boots= new Pair[K];
        for (int i = 0; i < N; i++) {
            tiles[i]= sc.ni();
        }
        for (int i = 0; i < K; i++) {
            boots[i]= new Pair(sc.ni(), sc.ni());
        }
        int[][] dp= new int[K][N];
        for (int i = 0; i < K; i++) {
            dp[i][0]=1;
        }
        for (int i = 0; i < K; i++) {
            int j=0;
            while(j<N){
                if(dp[i][j]==0) {
                    j++;
                    continue;
                }
                for (int k = 1; k <= boots[i].b; k++) {
                    if(j+k>=N) break;
                    if(boots[i].a>=tiles[j+k]) dp[i][j+k]=1;
                }
                j++;
            }
        }
        for (int i = 1; i < K; i++) {
            for (int j = 0; j < N; j++) {
                if(dp[i-1][j]==1 ){
                    if(boots[i].a>=tiles[j]) {
                        dp[i][j] = 1;
                        int k = j;
                        while (k < N) {
                            if (dp[i][k] == 0) {
                                k++;
                                continue;
                            }
                            for (int l = 1; l <= boots[i].b; l++) {
                                if (k + l >= N) break;
                                //if(dp[i][k+l]==1) break;
                                if (boots[i].a >= tiles[k + l]) dp[i][k + l] = 1;
                            }
                            k++;
                        }
                    }
                    else dp[i][j]=1;
                }
            }
        }

        for (int i = 0; i < K; i++) {
            if(dp[i][N-1]==1) {
                pw.println(i);
                break;
            }
        }



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