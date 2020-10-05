/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;

public class fcolor {

    static int submit = 0;
    static ArrayList<Integer>[] adj, rev;
    static djset dj;
    static ArrayDeque<Integer> dq;
    public static void solve() throws Exception {
        FastScanner sc = new FastScanner("fcolor.in", submit);
        FastWriter pw = new FastWriter("fcolor.out", submit);

        int N= sc.ni();
        int M= sc.ni();
        adj= new ArrayList[N];
        rev= new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
            rev[i]= new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            int a= sc.ni()-1;
            int b= sc.ni()-1;
            adj[a].add(b);
        }
        dj= new djset(N);
        dq= new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            if(adj[i].size()>1) dq.offer(i);
            rev[i].add(i);
        }
        while(dq.size()>0){
            int cur= dq.peek();
            if(adj[cur].size()<=1) {
                dq.poll(); continue;
            }
            int idx=adj[cur].size()-1;
            int a= adj[cur].get(idx); adj[cur].remove(idx);
            if(dj.find(a)==dj.find( adj[cur].get(idx))) continue;
            merge(a,adj[cur].get(idx));
        }
        int []par= new int[N];
        int cur=1;
        for (int i = 0; i < N; i++) {
            if(par[dj.find(i)]==0) par[dj.find(i)]=cur++;
            pw.println(par[dj.find(i)]);
        }



        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static void merge(int a, int b){
        a= dj.find(a); b= dj.find(b);
        if(rev[a].size()<rev[b].size()) {  int c= a; a=b; b=c; }
        for (int child: rev[b]) {
            dj.union(a, child); rev[a].add(child);
        }
        adj[a].addAll(adj[b]);
        adj[b].clear();
        if(adj[a].size()>1) dq.offer(a);
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