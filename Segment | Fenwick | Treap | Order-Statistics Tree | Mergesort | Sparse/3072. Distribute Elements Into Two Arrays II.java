class Solution {

    class Treap {

        static class Node {
            int val;
            int priority;
            int size;
            Node left, right;

            Node(int val) {
                this.val = val;
                this.priority = new Random().nextInt();
                this.size = 1;
            }
        }

        Node root;

        // ---------------- SIZE ----------------

        int size(Node node) {
            return node == null ? 0 : node.size;
        }

        void updateSize(Node node) {
            if (node != null) {
                node.size = 1 + size(node.left) + size(node.right);
            }
        }

        // ---------------- ROTATIONS ----------------

        Node rotateRight(Node root) {
            Node newRoot = root.left;

            root.left = newRoot.right;
            newRoot.right = root;

            updateSize(root);
            updateSize(newRoot);

            return newRoot;
        }

        Node rotateLeft(Node root) {
            Node newRoot = root.right;

            root.right = newRoot.left;
            newRoot.left = root;

            updateSize(root);
            updateSize(newRoot);

            return newRoot;
        }

        // ---------------- INSERT ----------------

        Node insert(Node root, int val) {

            if (root == null) {
                return new Node(val);
            }

            if (val < root.val) {

                root.left = insert(root.left, val);

                // Heap property violated
                if (root.left.priority > root.priority) {
                    root = rotateRight(root);
                }

            } else {

                root.right = insert(root.right, val);

                // Heap property violated
                if (root.right.priority > root.priority) {
                    root = rotateLeft(root);
                }
            }

            updateSize(root);

            return root;
        }

        void insert(int val) {
            root = insert(root, val);
        }

        // ---------------- COUNT GREATER ----------------

        int countGreaterThan(Node root, int x) {

            if (root == null) {
                return 0;
            }

            if (root.val <= x) {
                // root and everything on left are <= x
                return countGreaterThan(root.right, x);
            }

            // root.val > x
            // root itself + entire right subtree
            return 1
                + size(root.right)
                + countGreaterThan(root.left, x);
        }

        int countGreaterThan(int x) {
            return countGreaterThan(root, x);
        }
    }
    int[] sol(int[] nums) {

        Treap ost_l = new Treap();
        Treap ost_r = new Treap();
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();
        
        arr1.add(nums[0]);
        ost_l.insert(nums[0]);

        arr2.add(nums[1]);
        ost_r.insert(nums[1]);

        int n = nums.length;

        for(int i=2; i<n; i++){
            int curr = nums[i];

            if(ost_l.countGreaterThan(curr) > ost_r.countGreaterThan(curr)){
                arr1.add(curr);
                ost_l.insert(curr);
            }
            else if(ost_l.countGreaterThan(curr) < ost_r.countGreaterThan(curr)){
                arr2.add(curr);
                ost_r.insert(curr);
            }
            else if(arr2.size() < arr1.size()){
                arr2.add(curr);
                ost_r.insert(curr);
            }
            else{
                arr1.add(curr);
                ost_l.insert(curr);
            }
        }

        int i=0;
        for(int e: arr1) nums[i++] = e;
        for(int e: arr2) nums[i++] = e;

        return nums;

    }

    class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n + 1];
        }

        void add(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int query(int idx) {
            int sum = 0;

            while (idx > 0) {
                sum += bit[idx];
                idx -= idx & -idx;
            }

            return sum;
        }
    }
    int[] sol_OP(int[] nums) {

        int n = nums.length;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rank = new HashMap<>();

        int rankValue = 1;

        for (int x : sorted) {
            if (!rank.containsKey(x)) {
                rank.put(x, rankValue++);
            }
        }

        Fenwick bit1 = new Fenwick(rankValue);
        Fenwick bit2 = new Fenwick(rankValue);

        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();

        // First two operations
        arr1.add(nums[0]);
        bit1.add(rank.get(nums[0]), 1);

        arr2.add(nums[1]);
        bit2.add(rank.get(nums[1]), 1);

        for (int i = 2; i < n; i++) {

            int x = nums[i];
            int idx = rank.get(x);

            int greater1 =
                arr1.size() - bit1.query(idx);

            int greater2 =
                arr2.size() - bit2.query(idx);

            if (greater1 > greater2) {

                arr1.add(x);
                bit1.add(idx, 1);

            } else if (greater1 < greater2) {

                arr2.add(x);
                bit2.add(idx, 1);

            } else {

                if (arr1.size() <= arr2.size()) {

                    arr1.add(x);
                    bit1.add(idx, 1);

                } else {

                    arr2.add(x);
                    bit2.add(idx, 1);
                }
            }
        }

        // Concatenate
        int[] result = new int[n];

        int k = 0;

        for (int x : arr1)
            result[k++] = x;

        for (int x : arr2)
            result[k++] = x;

        return result;
    }

    int[] sol_OP_1(int[] nums) {

        int n = nums.length;

        // Coordinate compression
        int[] st = nums.clone();
        Arrays.sort(st);

        Fenwick bit1 = new Fenwick(n+1);
        Fenwick bit2 = new Fenwick(n+1);

        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();

        // First two operations
        arr1.add(nums[0]);
        // bit1.add(rank.get(nums[0]), 1);
        bit1.add(Arrays.binarySearch(st, nums[0]) + 1, 1);

        arr2.add(nums[1]);
        // bit2.add(rank.get(nums[1]), 1);
        bit2.add(Arrays.binarySearch(st, nums[1]) + 1, 1);

        for (int i = 2; i < n; i++) {

            int x = nums[i];
            int idx = Arrays.binarySearch(st, x) + 1;

            int greater1 =
                arr1.size() - bit1.query(idx);

            int greater2 =
                arr2.size() - bit2.query(idx);

            if (greater1 > greater2) {

                arr1.add(x);
                bit1.add(idx, 1);

            } else if (greater1 < greater2) {

                arr2.add(x);
                bit2.add(idx, 1);

            } else {

                if (arr1.size() <= arr2.size()) {

                    arr1.add(x);
                    bit1.add(idx, 1);

                } else {

                    arr2.add(x);
                    bit2.add(idx, 1);
                }
            }
        }

        // Concatenate
        int[] result = new int[n];

        int k = 0;

        for (int x : arr1)
            result[k++] = x;

        for (int x : arr2)
            result[k++] = x;

        return result;
    }

    public int[] resultArray(int[] nums) {

        // return sol(nums);

        // return sol_OP(nums);

        return sol_OP(nums);

    }
}












