/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class poetry {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here
    static final long MOD=(long)1e9+7;
    //Globals End
    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("poetry.in", debug);
        FastWriter pw = new FastWriter("poetry.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        int K= sc.ni();
        long[] dp= new long[K+1];
        long[] rc= new long[N];
        int[] len= new int[N];
        int[] rhyme = new int[N];
        for (int i = 0; i <N ; i++) {
            len[i]= sc.ni();
            rhyme[i]= sc.ni()-1;
        }
        int[] freq= new int[26];
        for (int i = 0; i < M; i++) {
            freq[sc.next().charAt(0)-'A']++;
        }
        int i=0;
        dp[0]=1;
        while(i<K){
            if(dp[i]==0) {
                i++;
                continue;
            }
            for(int j=0; j<N; j++){
                if(i+len[j]>K) continue;
                if(i+len[j]==K){
                    rc[rhyme[j]]+=dp[i];
                    rc[rhyme[j]]%=MOD;
                }
                dp[i+len[j]]+=dp[i];
                dp[i+len[j]]%=MOD;
            }
            i++;
        }
        long res=1;
        for (int j = 0; j < 26; j++) {
            long temp=0;
            if(freq[j]==0) continue;
            for(long num: rc){
                temp+=exp(num,freq[j]);
                temp%=MOD;
            }
            res*=temp;
            res%=MOD;
        }
        pw.println(res);

        /* End Stub */
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    public static long slowexp(long base, int power){
        long res=1;
        while(power-->0){
            res*=base;
            res%=MOD;
        }
        return res%MOD;
    }
    public static long exp(long base, int power){
        long res=1;
        long[] dp= new long[35];
        dp[0]=base%MOD;
        for (int i = 1 ;i < 35; i++) {
            dp[i]=dp[i-1] *dp[i-1] ;
            dp[i]%=MOD;
        }
        while(power!=0){
            int index=0;
            long sum=1;
            while(sum*2<=power){
                sum*=2;
                index++;
            }
            power-=sum;
            res*=dp[index];
            res%=MOD;
        }
        res%=MOD;
        return res;
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