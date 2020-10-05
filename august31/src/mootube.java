/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class mootube {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("mootube.in", debug);
        FastWriter pw = new FastWriter("mootube.out", debug);
        int N = sc.ni();
        int M = sc.ni();
        Edge[] arr= new Edge[N-1];
        for (int i = 0; i < N-1; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            int w= sc.ni();
            arr[i]= new Edge(w,a,b);
        }
        Arrays.sort(arr);
        Pair[] query= new Pair[M];
        for (int i = 0; i < M; i++) {
            int w= sc.ni();
            int a= sc.ni()-1;
            query[i]= new Pair(w,a, i);
        }
        Arrays.sort(query);
        int i=0;
        djset dj= new djset(N);
        int[] res= new int[M];
        for(Pair cur: query){
            while(i<N-1 && arr[i].w>=cur.a){
                dj.union(arr[i].a, arr[i].b);
                i++;
            }
            res[cur.i]=(dj.get(dj.find(cur.b))-1);
        }

        for (int j = 0; j < M; j++) {
            pw.println(res[j]);
        }

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class Edge implements Comparable<Edge>{
        int w;
        int a;
        int b;

        public Edge(int w, int a, int b) {
            this.w=w;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Edge obj) {
            return obj.w-this.w;
        }
    }


    static class Pair implements Comparable<Pair> {
        int a;
        int b;
        int i;

        public Pair(int a, int b, int i) {
            this.a = a;
            this.b = b;
            this.i=i;
        }

        public int compareTo(Pair obj) {
            if (obj.a == a) {
                return obj.b-b;
            } else return obj.a-a;
        }
    }


    static class djset {
        int N;
        int[] parent;
        int[] freq;

        // Creates a disjoint set of size n (0, 1, ..., n-1)
        public djset(int n) {
            parent = new int[n];
            N = n;
            freq= new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
            Arrays.fill(freq,1);
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
            freq[rootv1]+=freq[rootv2];
            freq[rootv2]=0;
            // Attach tree of v2 to tree of v1.
            parent[rootv2] = rootv1;
            return true;
        }
        public int get(int i) {
            return freq[i];
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