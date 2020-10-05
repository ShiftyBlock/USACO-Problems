/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class cowmbat {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("cowmbat.in", debug);
        FastWriter pw = new FastWriter("cowmbat.out", debug);
        //edge cases
        // 1 based indexing
        // min[1..K-1] should always be inf
        // dp[0..K-1][any j] should always be inf
        // after the first K-1 letters have passed, we can choose to convert the last
        // K letters into j
        // after the first K*2-1 letters have passed, min[i-K] starts to not become inf
        // this is because we dont want to be switching
        // in a way such that the first letters dont make a segment
        int N= sc.ni();
        int M =sc.ni();
        int K= sc.ni();
        int[] arr= new int[N];
        int idxtemp=0;
        for (char c: sc.next().toCharArray()) {
            arr[idxtemp++]=c-'a';
        }
        int[][] cost= new int[M][M];
        for (int i = 0; i < M; i++) {
            Arrays.fill(cost[i], 1<<30);
            cost[i][i]=0;
        }
        ArrayList<Pair>[] adj= new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                int cur= sc.ni();
                if(i==j) continue;
                adj[i].add(new Pair(cur,j));
            }
        }
        for (int i = 0; i < M; i++) {
            PriorityQueue<Pair> pq= new PriorityQueue<>();
            pq.offer(new Pair(0,i));
            while(pq.size()>0){
                Pair cur= pq.poll();
                for(Pair next: adj[cur.b]){
                    if(next.a+cur.a<cost[i][next.b]){
                        cost[i][next.b]=next.a+cur.a;
                        pq.offer(new Pair(cost[i][next.b], next.b));
                    }
                }
            }
        }
        int[][] prefixcost= new int[N+1][M];
        int[] min= new int[N+1];
        int[][] dp= new int[N+1][M];
        for (int i = 1; i < N+1; i++) {
            for (int j = 0; j < M; j++) {
                prefixcost[i][j]= prefixcost[i-1][j]+cost[arr[i-1]][j];
            }
        }
        Arrays.fill(min, 1<<30);
        min[0]=0;
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j]=1<<30;
            }
        }

        // dp section yay

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j]= dp[i-1][j]+ cost[arr[i-1]][j];
                if(i==K) dp[i][j]= Math.min(dp[i][j], prefixcost[i][j]);
                if(i>K && i<2*K) dp[i][j]= Math.min(dp[i][j], (1<<30)+ prefixcost[i][j]-prefixcost[i-K][j]);
                if(i>=2*K) dp[i][j]= Math.min(dp[i][j], min[i-K]+prefixcost[i][j]-prefixcost[i-K][j]);
                min[i]= Math.min(min[i],dp[i][j]);
            }
        }
        pw.println(min[N]);


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