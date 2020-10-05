/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class boards {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("boards.in", debug);
        FastWriter pw = new FastWriter("boards.out", debug);
        int N= sc.ni();
        int P= sc.ni();
        Board arr[]= new Board[2*P];
        point[] temp= new point[2*P];
        Pair[] corcompress= new Pair[P];
        for (int i = 0; i < P; i++) {
            int x1= sc.ni(); int y1=sc.ni();
             arr[i*2]=new Board(x1, y1, true,i);
             int x2= sc.ni(); int y2= sc.ni();
             arr[i*2+1]= new Board(x2, y2, false,i);
             temp[i*2]= new point(y1, i, true);
             temp[i*2+1] = new point(y2, i, false);
        }
        Arrays.sort(arr);
        Arrays.sort(temp);
        for (int i = 0; i < 2*P;) {
            int j=i;
            while(j<2*P && temp[j].y==temp[i].y){
                if(temp[j].s) corcompress[temp[j].i]= new Pair(i, 0);
                else corcompress[temp[j].i].b=i;
                j++;
            }
            i=j;
        }
        long[] cost= new long[P];
        segtree st= new segtree(2*P);
        Arrays.fill(cost,(long) 1e10);
        for (int i = 0; i < 2*P; i++) {
            Board cur= arr[i];
            if(cur.s){
                cost[cur.i]= Math.min(cost[cur.i], cur.x+cur.y);
                cost[cur.i]= Math.min(cost[cur.i], (long)cur.x+(long)cur.y+st.query(0, corcompress[cur.i].a+1));
            }
            if(!cur.s){
                st.update(corcompress[cur.i].b, Math.min(st.query(corcompress[cur.i].b, corcompress[cur.i].b+1),cost[cur.i]-cur.x-cur.y));
            }
        }
        TreeMap<Integer, Integer> tm= new TreeMap<>();
        long res=2*N;
        res= Math.min(res, res+st.query(0,2*P) );
        pw.println(res);




        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class segtree{
        int N;
        long[] arr;
        segtree(int N){
            this.N=N;
            arr= new long[4*N];
        }
        void update(int i, long val) {
            update(i,val,0,0,N);
        }
        void update(int i, long val, int cur, int lx, int rx) {
            if (rx - lx == 1) {
                arr[cur] = val;
                return;
            }
            int mid = (lx + rx) / 2;
            if (mid > i) update(i, val, 2 * cur + 1, lx, mid);
            else update(i, val, 2 * cur + 2, mid, rx);
            arr[cur] = Math.min(arr[2 * cur + 1] , arr[2 * cur + 2]);
        }
        long query(int l, int r){
            return query(l,r,0,0,N);
        }
        long query(int l, int r, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return 0;
            if(lx>=l && rx<=r) return arr[cur];
            int mid= (lx+rx)/2;
            return Math.min(query(l,r,2*cur+1, lx,mid),query(l,r,2*cur+2, mid, rx));
        }
    }
    static class point implements Comparable<point>{
        int y, i;
        boolean s;
        public point(int a, int b, boolean c){
            y=a; i=b; s=c;
        }
        public int compareTo(point obj) {
            return y - obj.y;
        }
    }
    static class Board implements Comparable<Board>{
        int x, y,i;
        boolean s;
        public Board(int a, int b,boolean c, int i){
           this.x=a;
           this.y=b;
           this.s=c;
           this.i=i;
        }
        public int compareTo(Board obj){
            if(x==obj.x) return y-obj.y;
            return x-obj.x;
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
            return a-obj.a;
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