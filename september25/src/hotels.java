/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class hotels {

    static long startTime = System.nanoTime();
    static ArrayList<Integer>[] adj, dp;
    static int[] parent;
    public static void main(String[] args) throws Exception {
        boolean debug = (true);
        FastScanner sc = new FastScanner("hotels.in", debug);
        FastWriter pw = new FastWriter("hotels.out", debug);
        int N= sc.ni();
        adj= new ArrayList[N];
        parent= new int[N];
        dp= new ArrayList[N];
        for(int i=0; i<N; i++){
            adj[i]= new ArrayList<>();
            dp[i]= new ArrayList<>();
        }
        for (int i = 0; i < N-1; i++) {
            int a=sc.ni()-1;
            int b= sc.ni()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        ArrayList<Integer> arr1= new ArrayList<>();
        arr1.add(1);
        arr1.add(2);
        arr1.add(3);
        arr1.add(4);

        count(arr1);
        long ans=0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dp[j].clear();
            }
            for (int j=0; j<adj[i].size(); j++) {
                dfs(adj[i].get(j), i, j, 1);
            }
            for (int j = 0; j < N; j++) {
                if(dp[j].size()==0) continue;
                Collections.sort(dp[j]);
                int idx=0;
                ArrayList<Integer> arr= new ArrayList<>();
                int temp=idx;
                while(idx<dp[j].size()){
                    while(temp+1<dp[j].size() && dp[j].get(temp+1).equals(dp[j].get(idx)))temp++;
                    arr.add(temp-idx+1);
                    idx=temp+1;
                }
                ans+=count(arr);
            }
        }
        pw.println(ans);


        long endTime = System.nanoTime();
        //System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }
    static void dfs(int cur, int par, int branch, int selfdep){
        dp[selfdep].add(branch);
        for(int next: adj[cur]){
            if(next==par) continue;
            dfs(next,cur, branch, selfdep+1);
        }
    }


    static long count(ArrayList<Integer> arr){
        long[][] dp= new long[3][arr.size()];
        long[][] prefix= new long[3][arr.size()+1];
        int N= arr.size();
        for (int i = 0; i < N; i++) {
            dp[0][i]=arr.get(i);
            prefix[0][i]=dp[0][i];
        }
        for (int i = N-1; i>=0; i--) {
            prefix[0][i]+=prefix[0][i+1];
        }
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j]= arr.get(j);
                dp[i][j]*=prefix[i-1][j+1];
                prefix[i][j]=dp[i][j];
            }
            for (int j = N-1; j>=0; j--) {
                prefix[i][j]+=prefix[i][j+1];
            }
        }
        return prefix[2][0];
        /*
        long res=0;
        long pairs=0;
        long sum=0;
        int N= arr.size();
        for (int i = 0; i < N; i++) {
            res+=pairs*arr.get(i);
            pairs+=sum*arr.get(i);
            sum+=arr.get(i);
        }
        return res;
         */
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