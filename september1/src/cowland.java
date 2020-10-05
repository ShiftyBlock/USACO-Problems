/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class cowland {

    static long startTime = System.nanoTime();
    static ArrayList<Integer>[] adj;
    static int N;
    static int M;
    static int time;
    static int[] in;
    static int[] out;
    static int log2;
    static int[][] ancestor;
    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("cowland.in", debug);
        FastWriter pw = new FastWriter("cowland.out", debug);
        N= sc.ni();
        M= sc.ni();
        log2=(int) (Math.log10(N)/Math.log10(2));
        ancestor= new int[N][log2+1];
        int[] arr= new int[N];
        in= new int[N];
        out= new int[N];
        for (int i = 0; i < N; i++) {
            arr[i]= sc.ni();
        }
        adj= new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<Integer>();
        }
        for (int i = 0; i < N-1; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        dfs(0,0);
        int[] events= new int[N*2];
        for (int i = 0; i < N; i++) {
            events[in[i]]=arr[i];
            events[out[i]]=arr[i];
        }
        segtree st= new segtree(2*N);
        for (int i = 0; i < N*2; i++) {
            st.set(i,events[i]);
        }
        System.out.println(st.xor(0,2));
        while(M-->0){
            int type= sc.ni();
            int a= sc.ni();
            int b= sc.ni();
            if(type==1) {
                //st.set(in[a-1], arr[a-1]);
                //st.set(out[a-1], arr[a-1]);
                st.set(in[a-1], b);
                st.set(out[a-1], b);
                arr[a-1]=b;
            }
            if(type==2) {
                int num1= st.xor(0,in[a-1]+1);
                int num2= st.xor(0, in[b-1]+1);
                int lca= arr[lca(a-1,b-1)];
                pw.println(num1^num2^lca);
            }
        }




        long endTime = System.nanoTime();
        //System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class segtree{
        int N;
        int[] arr;
        public segtree(int N){
            this.N=N;
            int size=1;
            while(size<=N) size*=2;
            arr= new int[size*2];
        }
        public void set(int i, int val){
            set(i, val, 0, 0, N);
        }
        public void set(int i, int val, int cur, int lx, int rx){
            if(rx-lx==1) {
                arr[cur]=val;
                return;
            }
            int mid= (lx+rx)/2;
            if(mid>i) set(i,val, 2*cur+1, lx, mid);
            else set(i,val, 2*cur+2, mid, rx);
            arr[cur]= arr[cur*2+1]^arr[cur*2+2];
        }
        public int xor(int l, int r){
            return xor(l,r,0, 0, N);
        }
        public int xor(int l, int r, int cur, int lx, int rx){
            if(lx>=r || rx<=l) return 0;
            if(lx>=l && rx<=r) return arr[cur];
            int mid= (lx+rx)/2;
            return xor(l, r, cur*2+1, lx, mid)^xor(l,r,cur*2+2, mid, rx);
        }
    }

    static void dfs(int cur, int par){
        in[cur]=time++;
        ancestor[cur][0]=par;
        for (int i = 1; i <=log2; i++) {
            ancestor[cur][i]=ancestor[ancestor[cur][i-1]][i-1];
        }
        for (int next: adj[cur]) {
            if(next==par) continue;
            dfs(next, cur);
        }
        out[cur]= time++;
    }
    static boolean isAncestor(int parent, int child){
        return in[parent]<=in[child]&& out[parent]>=out[child];
    }
    static int lca(int a, int b){
        if(isAncestor(a,b)) return a;
        if(isAncestor(b,a)) return b;
        int node= a;
        for (int i = log2; i>=0; i--) {
            if(!isAncestor(ancestor[node][i], b)) node= ancestor[node][i];
        }
        return ancestor[node][0];
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