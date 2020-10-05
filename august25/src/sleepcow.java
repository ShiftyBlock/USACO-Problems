/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class sleepcow {
    //Start Stub
    static long startTime = System.nanoTime();
    //Globals Go Here

    //Globals End
    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("sleepy.in", debug);
        FastWriter pw = new FastWriter("sleepy.out", debug);
        int N= sc.ni();
        int[] arr= new int[N];
        SegmentTree st= new SegmentTree(N);
        for (int i = 0; i < N; i++) {
            arr[i]= sc.ni()-1;
        }
        int idx=N-1;
        while(idx>0){
            if(arr[idx-1]<arr[idx]) {
                st.update(arr[idx]);
                idx--;
            }
            else break;
        }
        st.update(arr[idx]);
        int i=0;
        ArrayDeque<Integer> res= new ArrayDeque<>();
        while(i<idx){
            int sum= st.sum(arr[i]);
            st.update(arr[i]);
            int result= idx-i+sum-1;
            res.add(result);
            i++;
        }
        pw.println(res.size());
        while(res.size()>1){
            pw.print(res.poll()+" ");
        }
        if(res.size()==1) pw.println(res.poll());






        /* End Stub */
        long endTime = System.nanoTime();
       // System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class SegmentTree{
        int N;
        int[] arr;
        public SegmentTree(int N){
            int size=1;
            while(size<=N) size*=2;
            arr= new int[2*size];
            this.N=N;
        }
        public void update(int i){
            update(i, 0,0,N);
        }
        public void update(int i, int cur, int lx, int rx){
            if(rx-lx==1) {
                arr[cur]=1;
                return;
            }
            int mid= (lx+rx)/2;
            if(mid>i){
                update(i,2*cur+1, lx, mid);
            }
            else update(i,2*cur+2, mid, rx);
            arr[cur]= arr[2*cur+1]+arr[2*cur+2];
        }
        public int sum(int r){
            return sum(0, r, 0, 0, N);
        }
        public int sum(int l, int r, int cur, int lx, int rx){
            if(lx>=l && rx<=r) return arr[cur];
            if(lx>=r || rx<=l) return 0;
            int mid= (lx+rx)/2;
            return sum(l,r,2*cur+1, lx, mid)+ sum(l,r,2*cur+2, mid, rx);
        }
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