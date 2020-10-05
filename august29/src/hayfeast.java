/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class hayfeast {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("hayfeast.in", debug);
        FastWriter pw = new FastWriter("hayfeast.out", debug);
        int N= sc.ni();
        long M= sc.nl();
        Pair[] arr= new Pair[N];
        SegmentTree st= new SegmentTree(N);
        long prefix[]= new long[N+1];
        for (int i = 0; i < N; i++) {
            arr[i]= new Pair(sc.ni(), sc.ni());
            if(i==0) prefix[i]= arr[i].a;
            else prefix[i]=prefix[i-1]+arr[i].a;
            st.set(i,arr[i].b);
        }
        for(int i=N; i>0; i--){
            prefix[i]=prefix[i-1];
        }
        prefix[0]=0;
        int i=0;
        while(prefix[i]<M) i++;
        long best=(long) 1e18;
        int j=0;
        while(i<=N){
            while(j+1<=N && prefix[i]-prefix[j+1]>=M) j++;
            best= Math.min(best,  st.max(j,i) );
            i++;
        }
        pw.println(best);





        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class SegmentTree{
        int N;
        long[] arr;
        public SegmentTree(int N){
            this.N=N;
            int size=1;
            while(size<=N) size*=2;
            arr= new long[2*size];
            Arrays.fill(arr,(long) -1e18);
        }
        public void set(int i, int val){
            set(i,val, 0, 0, N);
        }
        public void set(int i, int val, int cur, int lx, int rx){
            if(rx-lx==1){
                arr[cur]=val;
                return;
            }
            int mid= (lx+rx)/2;
            if(mid>i){
                set(i,val,cur*2+1, lx,mid);
            }
            else set(i,val, cur*2+2, mid, rx);
            arr[cur]= Math.max(arr[cur*2+1], arr[cur*2+2]);
        }
        public long max(int l, int r){
            return max(l,r,0,0,N);
        }
        public long max(int l, int r, int cur, int lx, int rx){
            if(lx>=r || l>=rx) return (long) -1e18;
            if(lx>=l && r>=rx) return arr[cur];
            int mid= (lx+rx)/2;
            return Math.max(max(l,r,cur*2+1, lx, mid), max(l,r,cur*2+2, mid, rx));
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

}