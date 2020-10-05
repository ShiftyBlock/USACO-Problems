/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;

public class cowpatidibility {

    public static void main(String[] args) throws Exception {
        long start= System.nanoTime();
        boolean debug =! (true);
        FastScanner sc = new FastScanner("cowpatibility.in", debug);
        FastWriter pw = new FastWriter("cowpatibility.out", debug);
        int N = sc.ni();
        int tcs = N;
        HashMap<String, Integer> map = new HashMap<>();
        while (tcs-- > 0) {
            int[] arr = new int[5];
            for (int i = 0; i < 5; i++) {
                arr[i] = sc.ni();
            }
            Arrays.sort(arr);
            for (int i = 0; i < 5; i++) {
                String key = arr[i] + "";
                if (!map.containsKey(key)) map.put(key, 0);
                map.put(key, map.get(key) + 1);
            }
            for (int i = 0; i < 5; i++) {
                for (int j = i + 1; j < 5; j++) {
                    String key = arr[i] + ":" + arr[j];
                    if (!map.containsKey(key)) map.put(key, 0);
                    map.put(key, map.get(key) + 1);
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = i + 1; j < 5; j++) {
                    for (int k = j + 1; k < 5; k++) {
                        String key = arr[i] + ":" + arr[j] + ":" + arr[k];
                        if (!map.containsKey(key)) map.put(key, 0);
                        map.put(key, map.get(key) + 1);
                    }
                }
            }

            for (int i = 0; i < 5; i++) {
                for (int j = i + 1; j < 5; j++) {
                    for (int k = j + 1; k < 5; k++) {
                        for (int l = k + 1; l < 5; l++) {
                            String key = arr[i] + ":" + arr[j] + ":" + arr[k] + ":" + arr[l];
                            if (!map.containsKey(key)) map.put(key, 0);
                            map.put(key, map.get(key) + 1);
                        }
                    }
                }
            }
            String key5 = arr[0] + ":" + arr[1] + ":" + arr[2] + ":" + arr[3] + ":" + arr[4];
            if (!map.containsKey(key5)) map.put(key5, 0);
            map.put(key5, map.get(key5) + 1);
        }

        long res = 0;
        int[] mul= new int[]{-1,1,-1,1,-1,1};
        for (String key : map.keySet()) {
            long sum = map.get(key);
            if (sum == 1) continue;
            int length = key.split(":").length;
            res += (long) mul[length]* ((sum * (sum - 1)) / 2);
        }


        pw.println(((long) (N) * (long) (N - 1)) / 2 - res);
        pw.close();
        System.out.println((System.nanoTime() - start) / 1e9 + " s");
    }
    static class FastScanner {
        InputStream dis;
        byte[] buffer = new byte[1 << 17];
        int pointer = 0;

        public FastScanner(String fileName, boolean debug) throws Exception {
            if(debug) dis= System.in;
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
                pw = new PrintWriter(System.out, false);
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