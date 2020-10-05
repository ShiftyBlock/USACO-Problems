/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class tracing {

    static int submit = 1;

    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("tracing.in", submit);
        FastWriter pw = new FastWriter("tracing.out", submit);

        int N= sc.ni(); int T= sc.ni();
        Pair[] arr= new Pair[T];
        int[] res = new int[N];
        int idxjk=0;
        for (char c: sc.next().toCharArray()) {
            res[idxjk++]=c-'0';
        }
        for (int i = 0; i < T; i++) {
            arr[i]= new Pair(sc.ni(), sc.ni()-1, sc.ni()-1);
        }
        Arrays.sort(arr);
        int possible[]= new int[N];
        int low=1<<30;
        int high=0;

        for (int i = 0; i < N; i++) {
            for (int k = 0; k <= T+1; k++) {
                int[] cur= new int[N];
                int[] shake= new int[N];
                Arrays.fill(shake, k);
                cur[i]=1;
                for (int j = 0; j < T; j++) {
                    Pair event= arr[j];
                    int a= cur[event.a]; int b= cur[event.b];
                    if(a==1) shake[event.a]--;
                    if(b==1) shake[event.b]--;
                    if(shake[event.a]>=0 && a==1) cur[event.b]=1;
                    if(shake[event.b]>=0 && b==1) cur[event.a]=1;
                }
                boolean valid= true;
                for (int j = 0; j < N; j++) {
                    valid&=cur[j]==res[j];
                }
                if(valid){
                    possible[i]=1;
                    low= Math.min(low, k);
                    high= Math.max(high, k);
                }
            }
        }
        int total=0;
        for (int i = 0; i < N; i++) {
            if(possible[i]==1) total++;
        }
        String max= high==T+1?"Infinity":""+high;
        pw.println(total+" "+low+" "+max);
        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }


    static class Pair implements Comparable<Pair> {
        int t;
        int a;
        int b;

        public Pair(int t, int a, int b) {
            this.t=t;
            this.a = a;
            this.b = b;
        }

        public int compareTo(Pair obj) {
            return this.t-obj.t;
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