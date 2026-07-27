import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int minMeetingRooms(int[][] intervals) {
        // Base case: if there are no meetings, we need 0 rooms
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // 1. Sort the meetings by their START times
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // 2. Min-Heap to track the END times of active meetings
        PriorityQueue<Integer> allocator = new PriorityQueue<>();

        // 3. Allocate a room for the very first meeting
        allocator.add(intervals[0][1]);

        // 4. Iterate through the rest of the meetings
        for (int i = 1; i < intervals.length; i++) {
            int currentStart = intervals[i][0];
            int currentEnd = intervals[i][1];

            // If the room that frees up earliest is empty before/at the current meeting's start time
            if (currentStart >= allocator.peek()) {
                // We can reuse this room! Evict the old meeting's end time
                allocator.poll();
            }

            // Add the current meeting's end time to the heap (allocates a room)
            allocator.add(currentEnd);
        }

        // 5. The size of the heap represents the total rooms required
        return allocator.size();
    }
}
