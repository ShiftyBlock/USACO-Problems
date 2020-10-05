/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class rps {

    static int submit = 0;
   static int[] a= new int[3], b= new int[3];
   static Pair move[]= new Pair[6];
    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("rps.in", submit);
        FastWriter pw = new FastWriter("rps.out", submit);

        int N= sc.ni();
        a[0]= sc.ni(); a[1]= sc.ni(); a[2]= sc.ni();
        b[0]= sc.ni(); b[1]= sc.ni(); b[2]= sc.ni();
        int max= Math.min(a[0], b[1])+ Math.min(a[1], b[2])+Math.min(a[2], b[0]);
        move[0]= new Pair(0,0);
        move[1]= new Pair(0,2);
        move[2]= new Pair(1,1);
        move[3]= new Pair(1,0);
        move[4]= new Pair(2,2);
        move[5]= new Pair(2,1);
        int[] arr= new int[]{0,1,2,3,4,5};
        recurse(arr, 6);
        pw.println(res+" "+max);
        long endTime = System.nanoTime();
        //System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
   static int res=1<<30;
    static void recurse(int[] arr, int N){
        if(N==1){
            best(arr); return;
        }
        for (int i = 0; i < N-1; i++) {
            recurse(arr, N-1);
            if(N%2==0){
                swap(arr, i, N-1);
            }
            else swap(arr, 0, N-1);
        }
        recurse(arr, N-1);
    }
    static void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
    static void best(int[] arr){
        int[] tempA=Arrays.copyOf(a, a.length);
        int[] tempB=Arrays.copyOf(b, b.length);
        for (int i = 0; i < arr.length; i++) {
            Pair cur= move[arr[i]];
            int min= Math.min(tempA[cur.a], tempB[cur.b]);
            tempA[cur.a]-=min;
            tempB[cur.b]-=min;
        }
        res= Math.min(res,Math.min(tempA[0], tempB[1])+ Math.min(tempA[1], tempB[2])+Math.min(tempA[2], tempB[0]) );
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