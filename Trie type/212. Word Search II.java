
class Solution {

    boolean dfs(int ind, int i, int j,
                char[][] board, String word,
                int row, int col) {

        if (ind == word.length())
            return true;

        if (i < 0 || i >= row || j < 0 || j >= col)
            return false;

        if (board[i][j] != word.charAt(ind))
            return false;

        char original = board[i][j];
        board[i][j] = '$';

        boolean ans =
                dfs(ind + 1, i + 1, j, board, word, row, col) ||
                dfs(ind + 1, i - 1, j, board, word, row, col) ||
                dfs(ind + 1, i, j + 1, board, word, row, col) ||
                dfs(ind + 1, i, j - 1, board, word, row, col);

        board[i][j] = original;

        return ans;
    }

    public List<String> findWords(char[][] board, String[] words) {

        int row = board.length;
        int col = board[0].length;

        List<String> ans = new ArrayList<>();

        // Store all positions for each character
        List<int[]>[] positions = new ArrayList[26];

        for (int i = 0; i < 26; i++)
            positions[i] = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                positions[board[i][j] - 'a'].add(new int[]{i, j});
            }
        }

        for (String word : words) {

            boolean reversed = false;

            // Start from the character which occurs
            // fewer times on the board.
            if (positions[word.charAt(word.length() - 1) - 'a'].size()
                    < positions[word.charAt(0) - 'a'].size()) {

                word = new StringBuilder(word).reverse().toString();
                reversed = true;
            }

            int startChar = word.charAt(0) - 'a';

            for (int[] pos : positions[startChar]) {

                if (dfs(0, pos[0], pos[1],
                        board, word, row, col)) {

                    if (reversed)
                        word = new StringBuilder(word).reverse().toString();

                    ans.add(word);
                    break;
                }
            }
        }

        return ans;
    }
}

