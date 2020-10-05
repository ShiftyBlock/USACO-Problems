/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class angry {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("angry.in", debug);
        FastWriter pw = new FastWriter("angry.out", debug);
        int N= sc.ni();
        int[] frw= new int[N];
        int[] rev= new int[N];
        for (int i = 0; i < N; i++) {
            frw[i]= sc.ni();
        }
        Arrays.sort(frw);
        for (int i = 0; i < N; i++) {
            rev[N-i-1]=frw[i];
        }
        double low=frw[0], high= frw[N-1];
        double res=1<<30;
        while(high-.1>low){
            double mid= (low+high)/2;
            int fidx=-1;
            while(fidx+1<N && frw[fidx+1]<mid) fidx++;
            int ridx=N;
            while(ridx-1>=0 && rev[ridx-1]<mid) ridx--;
            double left= bsearch(mid, frw, fidx);
            double right= bsearch(mid, rev, ridx-1);
            res= Math.min(res, Math.max(left, right));
            if(left<right) low=mid;
            else high=mid;
        }
        boolean add5= false;
        if(res%1>=.5) add5=true;
        res= Math.floor(res);
        if(add5) res+=.5;
        String print= String.format("%.1f", res);
        pw.println(print);



        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static double bsearch(double mid, int[] arr, int index){
        double small=.5;
        double big =Math.abs(mid-arr[0]);
        while(big-small>.01){
            double blast= (big+small)/2;
            double res=blast;
            int i=index;
            double cur=mid;
            while(i>0){
                int j=i;
                while(j>=0 && Math.abs(arr[j]-cur)<=blast)j--;
                if(j==i) break;
                i=j+1;
                blast--;
                cur=arr[i];
                i--;
            }
            if(i<=0) big=res;
            else small=res;
        }
        return  big;
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