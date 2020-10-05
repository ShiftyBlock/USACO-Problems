/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class haybales {

    static int submit = 0;

    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("haybales.in", submit);
        FastWriter pw = new FastWriter("haybales.out", submit);

        int N= sc.ni();
        int M= sc.ni();
        segtree st= new segtree(N);
        for (int i = 0; i < N; i++) {
            st.update(i, sc.ni());
        }
        for (int i = 0; i < M; i++) {
            char c= sc.next().charAt(0);
            int a= sc.ni()-1; int b= sc.ni();
            if(c=='M'){
                pw.println(st.min(a,b));
            }
            if(c=='S'){
                pw.println(st.sum(a,b));
            }
            if(c=='P'){
                int prop= sc.ni();
                st.range(a,b, prop);
            }
        }


        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class segtree{
        int N;
        long[] arr;
        long[] minarr;
        segtree(int N){
            this.N=N;
            arr= new long[4*N];
            minarr= new long[4*N];
        }
        void update(int i, int val) {
            update(i,val,0,0,N);
        }
        void update(int i, int val, int cur, int lx, int rx){
            if(rx-lx==1) {
                arr[cur]=val;
                minarr[cur]=val;
                return;
            }
            int mid=(lx+rx)/2;
            if(mid>i) update(i,val,2*cur+1, lx, mid);
            else update(i,val,2*cur+2, mid, rx);
        }
        long sum(int l, int r){
            return sum(l,r,0,0,N);
        }
        long sum(int l, int r, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return 0;
            if(lx>=l && rx<=r) return arr[cur];
            int mid= (lx+rx)/2;
            return sum(l,r,2*cur+1, lx,mid)+sum(l,r,2*cur+2, mid, rx);
        }
        void range(int l, int r, int v){
            range(l,r,v, 0,0,N);
        }
        void range(int l, int r, int v, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return;
            if(lx>=l && rx<=r) {
                arr[cur]+=v;
                minarr[cur]+=v;
                return;
            }
            int mid= (lx+rx)/2;
            range(l,r,v,2*cur+1, lx,mid);
            range(l,r,v, 2*cur+2, mid, rx);
        }
        long min(int l, int r){
            return min(l,r,0,0,N);
        }
        long min(int l, int r, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return 1<<30;
            if(lx>=l && rx<=r) return minarr[cur];
            int mid= (lx+rx)/2;
            return Math.min( min(l,r,2*cur+1, lx,mid), min(l,r, 2*cur+2, mid, rx));
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

        public FastScanner(String fileName, int s) throws Exception {
            if (s == 0) dis = System.in;
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
            if (s == 0) {
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
        if (submit == 0) {
            Thread thread = new Thread(null, () -> {
                try {
                    solve();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "", 1 << 28);
            thread.start();
            thread.join();
        } else solve();

    }
}