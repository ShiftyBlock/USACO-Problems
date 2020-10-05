/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;


//fake fortmoo
public class fortmoo {

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("fortmoo.in", debug);
        FastWriter pw = new FastWriter("fortmoo.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        int[][] arr= new int[N][M];
        boolean ver[][][];
        boolean hor[][][];
        for (int i = 0; i < N; i++) {
            char temp[]= sc.next().toCharArray();
            for (int j = 0; j < M; j++) {
                if(temp[j]=='X') arr[i][j]=1;
            }
        }

        hor= new boolean[N][M][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = j; k < M; k++) {
                    hor[i][j][k]= arr[i][k]==0;
                    if(k!=j) hor[i][j][k]&=hor[i][j][k-1];
                }
            }
        }
        ver= new boolean[M][N][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = j; k < N; k++) {
                    ver[i][j][k]= arr[k][i]==0;
                    if(k!=j) ver[i][j][k]&=ver[i][j][k-1];
                }
            }
        }
        int best=0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                for (int k = i; k < N; k++) {
                    for (int l = j; l < M; l++) {
                        if (hor[i][j][l]&& ver[j][i][k] &&ver[l][i][k] && hor[k][j][l]) best= Math.max(best, (k-i+1)*(l-j+1));
                    }
                }
            }
        }
        pw.println(best);

        pw.flush();
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
                pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(name))), false);
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