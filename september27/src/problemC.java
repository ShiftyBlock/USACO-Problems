/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class problemC {

    static int submit = 0;

    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("problemC.in", submit);
        FastWriter pw = new FastWriter("problemC.out", submit);

        int tcs= sc.ni();
        while(tcs-->0){
            int N= sc.ni();
            int[] arr= new int[N];
            segtree st= new segtree(N);
            for (int i = 0; i < N; i++) {
                arr[i]= sc.ni();
                st.update(i, arr[i]);
            }
            int j=0; int k=N-1;
            ArrayDeque<Integer> res= new ArrayDeque<>();
            while(j<=k){
                if(arr[j]==arr[k]){
                    res.addFirst(st.min(j,k+1));
                    if(k%2==j%2) k--;
                    else j++;
                }
                else{
                    res.addFirst(st.min(j,k+1));
                    j++; k--;
                }
            }
            for (int i = 0; i < N; i++) {
                if(i<N-res.size()) pw.print(-1+" ");
                else pw.print(res.poll()+" ");
            }
            pw.println("");


        }

        long endTime = System.nanoTime();
       // System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static void checker(int arr[]){
        int size=1;
        while(size<arr.length) {

        }
    }
    static class segtree{
        int N;
        int[] arr;
        segtree(int N){
            this.N=N;
            arr= new int[4*N];
            Arrays.fill(arr, (int) 5e5);
        }
        void update(int i, int val) {
            update(i,val,0,0,N);
        }
        void update(int i, int val, int cur, int lx, int rx){
            if(rx-lx==1) {
                arr[cur]=val;
                return;
            }
            int mid=(lx+rx)/2;
            if(mid>i) update(i,val,2*cur+1, lx, mid);
            else update(i,val,2*cur+2, mid, rx);
            arr[cur]= Math.min(arr[2*cur+1], arr[2*cur+2]);
        }
        int min(int l, int r){
            return min(l,r,0,0,N);
        }
        int min(int l, int r, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return (int) 5e5;
            if(lx>=l && rx<=r) return arr[cur];
            int mid= (lx+rx)/2;
            return Math.min(min(l,r,2*cur+1, lx,mid),min(l,r,2*cur+2, mid, rx));
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