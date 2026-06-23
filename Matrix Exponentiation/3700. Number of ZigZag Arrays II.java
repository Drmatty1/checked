class Solution {

    static final int MOD = 1_000_000_007;

    private long[][] multiply(long[][] A, long[][] B) {
        int sz = A.length;

        long[][] C = new long[sz][sz];

        for (int i = 0; i < sz; i++) {
            for (int k = 0; k < sz; k++) {

                if (A[i][k] == 0) continue;

                for (int j = 0; j < sz; j++) {

                    if (B[k][j] == 0) continue;

                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }

        return C;
    }

    private long[][] power(long[][] base, long exp) {

        int sz = base.length;

        long[][] result = new long[sz][sz];

        for (int i = 0; i < sz; i++) {
            result[i][i] = 1;
        }

        while (exp > 0) {

            if ((exp & 1) == 1) {
                result = multiply(result, base);
            }

            base = multiply(base, base);
            exp >>= 1;
        }

        return result;
    }

    private int id(int val, int dir, int m) {
        return dir * m + val;
    }

    public int zigZagArrays(int n, int l, int r) {

        if (n == 1) {
            return (r - l + 1);
        }

        int m = r - l + 1;
        int states = 2 * m;

        long[][] T = new long[states][states];

        for (int x = 0; x < m; x++) {

            for (int y = 0; y < x; y++) {
                // T[id(y, 0, m)][id(x, 1, m)]++;
                T[y][id(x, 1, m)]++;
            }

            for (int y = x + 1; y < m; y++) {
                T[id(y, 1, m)][id(x, 0, m)]++;
            }
        }

        long [] start = new long[2*m];
        Arrays.fill(start,1);


        // This Version initialzes our Vbase for len 2, alt -> len1
        /* 
        long[] start = new long[states];
        for (int a = 0; a < m; a++) {
            for (int b = 0; b < m; b++) {

                if (a == b) continue;

                if (a < b) {
                    start[id(b, 1, m)]++;
                } else {
                    start[id(b, 0, m)]++;
                }
            }
        }
        */

        // if we use start that initialzed to len 2 -> T^(n-2);
        long[][] P = power(T, n - 1);


        long ans = 0;

        for (int i = 0; i < states; i++) {

            long cur = 0;

            for (int j = 0; j < states; j++) {
                // cur = (cur + P[i][j] * start[j]) % MOD;
                cur = (cur + P[i][j] ) % MOD;
            }

            ans = (ans + cur) % MOD;
        }

        return (int) ans;
    }
}
/*
Example: l = 1, r = 3

Values = {1,2,3}
States = 2 * 3 = 6

Index mapping:

0 -> 1U   (current value = 1, last move was UP)
1 -> 2U
2 -> 3U
3 -> 1D   (current value = 1, last move was DOWN)
4 -> 2D
5 -> 3D

---------------------------------------------------
Transition Rule
---------------------------------------------------

To reach xU:
    previous state must be yD where y < x

To reach xD:
    previous state must be yU where y > x

Transition Matrix T

        From
        1U 2U 3U 1D 2D 3D

1U      0  0  0  0  0  0
2U      0  0  0  1  0  0
3U      0  0  0  1  1  0
1D      0  1  1  0  0  0
2D      0  0  1  0  0  0
3D      0  0  0  0  0  0

---------------------------------------------------
V1 : length = 1
---------------------------------------------------

Any single value can be treated as either U or D.

V1 =
[
 1,   // 1U
 1,   // 2U
 1,   // 3U
 1,   // 1D
 1,   // 2D
 1    // 3D
]

---------------------------------------------------
V2 = T * V1
(length = 2)
---------------------------------------------------

1U = 0

2U = 1D
    = 1

3U = 1D + 2D
    = 1 + 1
    = 2

1D = 2U + 3U
    = 1 + 1
    = 2

2D = 3U
    = 1

3D = 0

V2 =
[
 0,   // 1U
 1,   // 2U
 2,   // 3U
 2,   // 1D
 1,   // 2D
 0    // 3D
]

Interpretation:

3U = 2 means:
    [1,3]
    [2,3]

2D = 1 means:
    [3,2]

---------------------------------------------------
V3 = T * V2
(length = 3)
---------------------------------------------------

1U = 0

2U = 1D
    = 2

3U = 1D + 2D
    = 2 + 1
    = 3

1D = 2U + 3U
    = 1 + 2
    = 3

2D = 3U
    = 2

3D = 0

V3 =
[
 0,   // 1U
 2,   // 2U
 3,   // 3U
 3,   // 1D
 2,   // 2D
 0    // 3D
]

Total valid ZigZag arrays of length 3

= sum(V3)
= 0 + 2 + 3 + 3 + 2 + 0
= 10

---------------------------------------------------
Matrix Exponentiation View
---------------------------------------------------

V2 = T * V1
V3 = T * V2
V4 = T * V3

...

Vk = T^(k-1) * V1

So for length n:

Vn = T^(n-1) * V1

The code uses an equivalent compressed starting vector
(start = all valid length-2 states), therefore:

Answer = sum( T^(n-2) * start )
*/
