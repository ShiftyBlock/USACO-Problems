/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class fire {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = (true);
        FastScanner sc = new FastScanner("fire.in", debug);
        FastWriter pw = new FastWriter("fire.out", debug);
        int N= sc.ni();
        Item[] arr= new Item[N];
        int[][] dp= new int[N+1][2001];
        ArrayList<Integer>[][] list= new ArrayList[N+1][2001];
        for (int i = 0; i < N+1; i++) {
            list[i]= new ArrayList[2001];
            for (int j = 0; j < 2001; j++) {
                list[i][j]= new ArrayList<>();
            }
        }
        for (int i = 0; i < N; i++) {
            arr[i]= new Item(sc.ni(), sc.ni(), sc.ni(), i+1);
        }
        Arrays.sort(arr);
        for (int i = 0; i < N+1; i++) {
            for (int j = 0; j < 2001; j++) {
                dp[i][j]=0;
            }
        }
        dp[0][0]=0;
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < 2001; j++) {
                dp[i][j]= dp[i-1][j];
                for(int num: list[i-1][j]) list[i][j].add(num);
                if(j>=arr[i-1].save && j<arr[i-1].burn){
                    if(dp[i-1][j-arr[i-1].save]+arr[i-1].val > dp[i][j]){
                        dp[i][j]= dp[i-1][j-arr[i-1].save]+arr[i-1].val;
                        list[i][j]= new ArrayList<>();
                        for(int num: list[i-1][j-arr[i-1].save]) list[i][j].add(num);
                        list[i][j].add(arr[i-1].i);
                    }
                }

               // System.out.print(dp[i][j]+" ");
            }
            //System.out.println();
        }
        int best= 0;
        int idxi=0;
        int idxj=0;
        for (int i = 1; i <N+1; i++) {
            for (int j = 0; j < 2001; j++) {
                if(dp[i][j]>=best){
                    best=dp[i][j];
                    idxi=i;
                    idxj=j;
                }
            }
        }
        pw.println(best);
        pw.println(list[idxi][idxj].size());
        for(int i: list[idxi][idxj]){
            pw.print(i+" ");
        }

        /*
        while(curval!=0){
            int notake= dp[idxi-1][idxj];
            if(idxj-arr[idxi-1].save<0) break;
            int take= dp[idxi-1][idxj-arr[idxi-1].save];
            if(curval==notake) {
                curval=notake;
                idxi=idxi-1;
            }
            else {
                curval = take;
                idxj = idxj - arr[idxi-1].save;
                idxi = idxi - 1;
                res.addFirst(arr[idxi].i);
            }
        }
        ArrayDeque<Integer>res= new ArrayDeque<>();
        pw.println(res.size());
        while(res.size()>0) pw.print(res.poll()+" ");
         */




        long endTime = System.nanoTime();
        //System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static class Item implements Comparable<Item>{
        int save;
        int burn;
        int val;
        int i;
        public Item(int a, int b, int c, int d){
            save=a; burn=b; val=c; i=d;
        }
        public int compareTo(Item obj){
            return this.burn-obj.burn;
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