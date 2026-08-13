

def lcis_top_down(a, b):
    n, m = len(a), len(b)
    memo = {}

    def solve(i, j):
        # Base case: if we run out of elements in 'a' or 'b', length is 0
        if i < 0 or j < 0:
            return 0
            
        if (i, j) in memo:
            return memo[(i, j)]

        # Case 1: No match
        if a[i] != b[j]:
            memo[(i, j)] = solve(i - 1, j)
            
        # Case 2: Match! (a[i] == b[j])
        else:
            ans = 1 # A single matching element forms a sequence of length 1
            # Look back at all previous elements in b to find a smaller one to attach to
            for k in range(j):
                if b[k] < b[j]:
                    ans = max(ans, 1 + solve(i - 1, k))
            memo[(i, j)] = ans
            
        return memo[(i, j)]

    # The overall LCIS could end at any element in b, so we check all possible ending points j
    max_lcis = 0
    for j in range(m):
        max_lcis = max(max_lcis, solve(n - 1, j))
        
    return max_lcis


# Bottom up
def lcis_bottom_up(a, b):
    n, m = len(a), len(b)
    
    # dp[i][j] is the LCIS using first i elements of a, first j elements of b, ending at b[j-1]
    dp = [[0] * (m + 1) for _ in range(n + 1)]
    
    for i in range(1, n + 1):
        for j in range(1, m + 1):
            
            # Case 1: No match
            if a[i-1] != b[j-1]:
                dp[i][j] = dp[i-1][j]
                
            # Case 2: Match found
            else:
                max_prev = 0
                # Look backwards through b[] to find a smaller element to extend
                for k in range(1, j):
                    if b[k-1] < b[j-1]:
                        max_prev = max(max_prev, dp[i-1][k])
                
                dp[i][j] = 1 + max_prev
                
    # The answer is the maximum value in the entire DP table
    ans = 0
    for row in dp:
        ans = max(ans, max(row))
    return ans


#  optimized

for i in range(1, n + 1):
    current_max = 0  # Reset for each new element in 'a'
    
    for j in range(1, m + 1):
        
        # Case 1: Match found
        if a[i-1] == b[j-1]:
            dp[i][j] = current_max + 1
            
        # Case 2: No match
        else:
            dp[i][j] = dp[i-1][j]
            
        # THE LIS CONDITION: 
        # We ONLY update current_max if the element in 'b' is strictly smaller than 'a'.
        # We look at dp[i-1][j] because that's the best sequence ending at b[j-1] found so far.
        if b[j-1] < a[i-1]:
            current_max = max(current_max, dp[i-1][j])




# more optimized
def lcis_1d(a, b):
    n, m = len(a), len(b)
    
    # dp[j] stores the maximum length of LCIS ending exactly at b[j]
    dp = [0] * m
    
    for i in range(n):
        current_max = 0  # Reset for each element in 'a'
        
        for j in range(m):
            
            # Case 1: Match Found!
            if a[i] == b[j]:
                # Overwrite dp[j] with the best sequence length we've tracked + 1
                dp[j] = current_max + 1
                
            # Case 2: No Match, but valid LIS precursor
            elif b[j] < a[i]:
                # Update current_max using the value retained from the previous row
                current_max = max(current_max, dp[j])
                
            # Case 3 (Implicit): No Match, and b[j] >= a[i]
            # We do absolutely nothing. dp[j] keeps its old value.
            
    # The final answer is the largest value anywhere in our 1D array
    return max(dp) if dp else 0
