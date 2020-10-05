/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class pump {

    static long startTime = System.nanoTime();
    static ArrayList<Pair>[] adj;

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("pump.in", debug);
        FastWriter pw = new FastWriter("pump.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        adj=  new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
        }
        int[] arr= new int[N];
        Pipe[] pipes= new Pipe[M];
        for (int i = 0; i < M; i++) {
            int a= sc.ni()-1; int b= sc.ni()-1; int c=sc.ni(); int f= sc.ni();
            pipes[i]= new Pipe(a,b,c,f);
        }
        Arrays.sort(pipes);
        int flow=1000;
        int i=0;
        long best=-1<<30;
        while(flow>=1){
            while(i<M && pipes[i].f>=flow) {
                adj[pipes[i].a].add(new Pair(pipes[i].c, pipes[i].b));
                adj[pipes[i].b].add(new Pair(pipes[i].c, pipes[i].a));
                i++;
            }
            int[] cost= new int[N];
            Arrays.fill(cost, 1<<30);
            cost[0]=0;
            PriorityQueue<Pair> pq= new PriorityQueue<>();
            pq.offer(new Pair(0,0));
            while(pq.size()>0){
                Pair cur= pq.poll();
                //if(cur.b==N-1) break;
                if(cost[cur.b]!=cur.a) continue;
                for(Pair next: adj[cur.b]){
                    if(cost[next.b]> cost[cur.b]+next.a){
                        cost[next.b]= cost[cur.b]+next.a;
                        pq.offer(new Pair(cost[next.b], next.b));
                    }
                }
            }
            best= Math.max(best, (long) ((long)1e6* ((double)flow/(double) cost[N-1])));
            flow--;
        }
        pw.println(best);


        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
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
    static class Pipe implements Comparable<Pipe>{
        int a;
        int b;
        int c;
        int f;
        public Pipe(int a, int b, int c, int f){
            this.a=a; this.b=b; this.c=c; this.f=f;
        }
        // sort by descending flow rate
        public int compareTo(Pipe obj){
            return obj.f-f;
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