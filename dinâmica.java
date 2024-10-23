public class dinâmica {

    
    public static void main(String[] args) {
        System.out.println("Fibonacci Recursivo de 4: " + fibonacci(4));
        System.out.println("Fibonacci Recursivo de 8: " + fibonacci(8));
        System.out.println("Fibonacci Recursivo de 16: " + fibonacci(16));
        System.out.println("Fibonacci Recursivo de 32: " + fibonacci(32)); 

        System.out.println("----------------------------------------------" ); 
        
        System.out.println("Fibonacci PD de 4: " + fibonacciPD(4));
        System.out.println("Fibonacci PD de 8: " + fibonacciPD(8));
        System.out.println("Fibonacci PD de 16: " + fibonacciPD(16));
        System.out.println("Fibonacci PD de 32: " + fibonacciPD(32));
        System.out.println("Fibonacci PD de 128: " + fibonacciPD(128));
        System.out.println("Fibonacci PD de 1000: " + fibonacciPD(1000));

        System.out.println("----------------------------------------------" ); 
        
        System.out.println("Fibonacci Memoizado de 4: " + memoizedFibo(4));
        System.out.println("Fibonacci Memoizado de 8: " + memoizedFibo(8));
        System.out.println("Fibonacci Memoizado de 16: " + memoizedFibo(16));
        System.out.println("Fibonacci Memoizado de 32: " + memoizedFibo(32));
        System.out.println("Fibonacci Memoizado de 128: " + memoizedFibo(128));
        System.out.println("Fibonacci Memoizado de 1000: " + memoizedFibo(1000));

        System.out.println("----------------------------------------------" ); 
        
        
        int[] peso = {2, 3, 4, 5};
        int[] valor = {3, 4, 5, 6};
        int capacidade = 5;
        int num = peso.length;
        System.out.println("Mochila Força Bruta: " + mochilaFB(peso, valor, capacidade, num));
        System.out.println("Mochila Programação Dinâmica: " + mochilaPD(peso.length, capacidade, peso, valor));

        System.out.println("----------------------------------------------" ); 

        String s1 = "Casablanca";
        String s2 = "Portentoso";

        System.out.println("Distância de Edição Força Bruta: " + distFB(s1, s2));
        System.out.println("Distância de Edição Programação Dinâmica: " + distPD(s1, s2));
 
    }


    // Fibonacci recursivo
    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    // Fibonacci usando programação dinâmica
    public static int fibonacciPD(int n) {
        int[] f = new int[n + 1];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }

    // Fibonacci com memoização
    public static int memoizedFibo(int n) {
        int[] f = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            f[i] = -1;
        }
        return lookupFibo(f, n);
    }

    private static int lookupFibo(int[] f, int n) {
        if (f[n] >= 0) {
            return f[n];
        }
        if (n <= 1) {
            f[n] = n;
        } else {
            f[n] = lookupFibo(f, n - 1) + lookupFibo(f, n - 2);
        }
        return f[n];
    }

    // 2. Problema da Mochila

    // Mochila com força bruta 
    public static int mochilaFB(int[] peso, int[] valor, int capacidade, int num) {
        if (num == 0 || capacidade == 0) {
            return 0;
        }
        if (peso[num - 1] > capacidade) {
            return mochilaFB(peso, valor, capacidade, num - 1);
        } else {
            return Math.max(
                valor[num - 1] + mochilaFB(peso, valor, capacidade - peso[num - 1], num - 1),
                mochilaFB(peso, valor, capacidade, num - 1)
            );
        }
    }

    // Mochila com programação dinâmica
    public static int mochilaPD(int N, int C, int[] peso, int[] valor) {
        int[][] maxTab = new int[N + 1][C + 1];

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= C; j++) {
                if (i == 0 || j == 0) {
                    maxTab[i][j] = 0;
                } else if (peso[i - 1] <= j) {
                    maxTab[i][j] = Math.max(maxTab[i - 1][j], valor[i - 1] + maxTab[i - 1][j - peso[i - 1]]);
                } else {
                    maxTab[i][j] = maxTab[i - 1][j];
                }
            }
        }
        return maxTab[N][C];
    }

    // 3. Problema da Distância de Edição

    // Distância de edição 
    public static int distFB(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        if (m == 0) return n;
        if (n == 0) return m;

        if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
            return distFB(s1.substring(0, m - 1), s2.substring(0, n - 1));
        } else {
            return 1 + Math.min(
                distFB(s1, s2.substring(0, n - 1)), 
                Math.min(
                    distFB(s1.substring(0, m - 1), s2), 
                    distFB(s1.substring(0, m - 1), s2.substring(0, n - 1)) 
                )
            );
        }
    }

    // Distância de edição com programação dinâmica
    public static int distPD(String A, String B) {
        int m = A.length();
        int n = B.length();
        int[][] matriz = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            matriz[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            matriz[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int custoExtra = (A.charAt(i - 1) == B.charAt(j - 1)) ? 0 : 1;
                matriz[i][j] = Math.min(
                    Math.min(matriz[i - 1][j] + 1, matriz[i][j - 1] + 1), 
                    matriz[i - 1][j - 1] + custoExtra
                );
            }
        }
        return matriz[m][n];
    }


}
