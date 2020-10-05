/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class milkvisits {

    static long startTime = System.nanoTime();
    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("milkvisits.in", debug);
        FastWriter pw = new FastWriter("milkvisits.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        Pair[] arr= new Pair[N];
        for (int i = 0; i < N; i++) {
            arr[i]= new Pair(sc.ni(), i);
        }
        Arrays.sort(arr);
        adj =new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int a= sc.ni()-1; int b= sc.ni()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        log= (int) (Math.log(N)/Math.log(2));
        anc= new int[N][log+1];
        in= new int[N];
        out= new int[N];
        dfs(0,0,0);
        event[] queries= new event[M];
        for (int i = 0; i < M; i++) {
            int a= sc.ni()-1; int b=sc.ni()-1;
            int color= sc.ni();
            queries[i]= new event(a,b,color, i);
        }
        Arrays.sort(queries);
        segtree st = new segtree(2*N);
        int qpos=0;
        int arrpos=0;
        int[] res=new int[M];
        int curcolor=0;
        while(++curcolor<=N){
            ArrayDeque<Integer>todo= new ArrayDeque<>();
            while(arrpos<N && arr[arrpos].a==curcolor){
                st.update(in[arr[arrpos].b], 1);
                st.update(out[arr[arrpos].b], -1);
                todo.add(arr[arrpos].b);
                arrpos++;

            }
            while(qpos<M && queries[qpos].c==curcolor) {
                int a= queries[qpos].a;
                int b= queries[qpos].b;
                int lca= lca(a,b);
                int count1= st.sum(0,in[a]+1)-st.sum(0,in[lca]);
                int count2= st.sum(0,in[b]+1)-st.sum(0,in[lca]);
                res[queries[qpos].i]= ((count1>0)|| (count2>0))?1:0;
                qpos++;
            }
            while(todo.size()>0){
                int cur=todo.poll();
                st.update(in[cur], 0);
                st.update(out[cur], 0);
            }
        }
        for (int j = 0; j < M; j++) {
            pw.print(res[j]);
        }





        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class segtree{
        int N;
        int[] arr;
        public segtree(int N){
            this.N=N;
            arr= new int[N*4+1];
        }
        void update(int i, int v){
            update(i,v,0,0,N);
        }
        void update (int i, int v, int c, int lx, int rx){
            if(rx-lx==1){
                arr[c]=v;
                return;
            }
            int mid=(lx+rx)/2;
            if(mid>i) update(i,v,2*c+1, lx, mid);
            else update(i,v,2*c+2, mid, rx);
            arr[c]= arr[2*c+1]+arr[2*c+2];
        }
        int sum(int l, int r) {
            return sum(l,r,0,0,N);
        }
        int sum(int l, int r, int c, int lx, int rx){
            if(rx<=l || lx>=r) return 0;
            if(lx>=l && rx<=r) return arr[c];
            int mid= (lx+rx)/2;
            return sum(l,r,2*c+1, lx,mid)+sum(l,r,2*c+2, mid, rx);
        }
    }
    static int[] in;
    static int[] out;
    static int time=0;
    static int[][] anc;
    static ArrayList<Integer>[] adj;
    static int log;

    static void dfs(int cur, int par, int depth){

        in[cur] = time++;
        anc[cur][0] = par;
        for (int i = 1; i < log; i++) {
            anc[cur][i] = anc[anc[cur][i - 1]][i - 1];
        }
        for (int next : adj[cur]) {
            if (next == par) continue;
            dfs(next, cur, depth++);
        }
        out[cur] = time++;


    }
    static boolean isanc(int u, int v){
        return in[u]<=in[v] && out[u]>=out[v];
    }
    static int lca(int u, int v){
        if(isanc(u,v)) return u;
        if(isanc(v,u)) return v;
        int l;
        for (l=log; l>=0; l--) {
            if(!isanc(anc[u][l], v) ) u=anc[u][l];
        }
        return anc[u][0];
    }

    static class event implements Comparable<event>{
        int a; int b; int c;
        int i;
        public event(int d, int e, int f, int g){
            a=d; b=e; c=f; i=g;
        }
        public int compareTo(event obj){
            return c-obj.c;
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