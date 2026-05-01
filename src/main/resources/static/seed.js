const questions = [
    {
        title: "Two Sum",
        description: "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target. You may assume that each input would have exactly one solution, and you may not use the same element twice.",
        difficulty: "EASY",
        constraints: "2 <= nums.length <= 10^4, -10^9 <= nums[i] <= 10^9, -10^9 <= target <= 10^9",
        sampleInput: "nums = [2,7,11,15], target = 9",
        sampleOutput: "[0,1]",
        timeLimit: 600,
        testCases: [
            { input: "[2,7,11,15] 9", expectedOutput: "[0,1]", isHidden: false, isSample: true },
            { input: "[3,2,4] 6", expectedOutput: "[1,2]", isHidden: false },
            { input: "[3,3] 6", expectedOutput: "[0,1]", isHidden: true },
            { input: "[1,2,3,4] 7", expectedOutput: "[2,3]", isHidden: true }
        ]
    },
    {
        title: "Valid Palindrome",
        description: "A phrase is a palindrome if it reads the same forward and backward after converting to lowercase and removing non-alphanumeric characters. Given a string s, return true if it is a palindrome, or false otherwise.",
        difficulty: "EASY",
        constraints: "1 <= s.length <= 2 * 10^5",
        sampleInput: "A man, a plan, a canal: Panama",
        sampleOutput: "true",
        timeLimit: 500,
        testCases: [
            { input: "Was car a race? Yes", expectedOutput: "true", isHidden: false, isSample: true },
            { input: "race a car", expectedOutput: "false", isHidden: false },
            { input: " ", expectedOutput: "true", isHidden: false },
            { input: "a", expectedOutput: "true", isHidden: true }
        ]
    },
    {
        title: "Reverse String",
        description: "Write a function that reverses a string. The input string is given as an array of characters s. You must do this by modifying the input array in-place with O(1) extra memory.",
        difficulty: "EASY",
        constraints: "1 <= s.length <= 10^4",
        sampleInput: "hello",
        sampleOutput: "olleh",
        timeLimit: 500,
        testCases: [
            { input: "hello", expectedOutput: "olleh", isHidden: false, isSample: true },
            { input: "Python", expectedOutput: "nohtyP", isHidden: false },
            { input: "a", expectedOutput: "a", isHidden: true },
            { input: "ab", expectedOutput: "ba", isHidden: true }
        ]
    },
    {
        title: "Longest Substring Without Repeating Characters",
        description: "Given a string s, find the length of the longest substring without repeating characters.",
        difficulty: "MEDIUM",
        constraints: "0 <= s.length <= 5 * 10^4",
        sampleInput: "abcabcbb",
        sampleOutput: "3",
        timeLimit: 800,
        testCases: [
            { input: "abcabcbb", expectedOutput: "3", isHidden: false, isSample: true },
            { input: "bbbbb", expectedOutput: "1", isHidden: false },
            { input: "pwwkew", expectedOutput: "3", isHidden: false },
            { input: "", expectedOutput: "0", isHidden: true },
            { input: "abcdefgh", expectedOutput: "8", isHidden: true }
        ]
    },
    {
        title: "Container With Most Water",
        description: "You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]). Find two lines that together with the x-axis form a container, such that the container contains the most water.",
        difficulty: "MEDIUM",
        constraints: "n == height.length, 2 <= n <= 10^5, 0 <= height[i] <= 10^4",
        sampleInput: "[1,8,6,2,5,4,8,3,7]",
        sampleOutput: "49",
        timeLimit: 800,
        testCases: [
            { input: "[1,8,6,2,5,4,8,3,7]", expectedOutput: "49", isHidden: false, isSample: true },
            { input: "[1,1]", expectedOutput: "1", isHidden: false },
            { input: "[4,3,2,1,4]", expectedOutput: "16", isHidden: false },
            { input: "[1,2]", expectedOutput: "1", isHidden: true }
        ]
    },
    {
        title: "3Sum",
        description: "Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0. Notice that the solution set must not contain duplicate triplets.",
        difficulty: "MEDIUM",
        constraints: "0 <= nums.length <= 3000, -10^5 <= nums[i] <= 10^5",
        sampleInput: "[-1,0,1,2,-1,-4]",
        sampleOutput: "[[-1,-1,2],[-1,0,1]]",
        timeLimit: 1000,
        testCases: [
            { input: "[-1,0,1,2,-1,-4] 0", expectedOutput: "2", isHidden: false, isSample: true },
            { input: "[] 0", expectedOutput: "0", isHidden: false },
            { input: "[0] 0", expectedOutput: "0", isHidden: false },
            { input: "[0,0,0] 0", expectedOutput: "1", isHidden: true }
        ]
    },
    {
        title: "Merge Two Sorted Lists",
        description: "You are given the heads of two sorted linked lists list1 and list2. Merge the two lists in a one sorted list. The list should be made by splicing together the nodes of the first two lists. Return the head of the merged linked list.",
        difficulty: "EASY",
        constraints: "The number of nodes in both lists is in the range [0, 50]. -100 <= Node.val <= 100",
        sampleInput: "list1 = [1,2,4], list2 = [1,3,4]",
        sampleOutput: "[1,1,2,3,4,4]",
        timeLimit: 600,
        testCases: [
            { input: "1,2,4 1,3,4", expectedOutput: "1,1,2,3,4,4", isHidden: false, isSample: true },
            { input: "  ", expectedOutput: "[]", isHidden: false },
            { input: "1  ", expectedOutput: "1", isHidden: false }
        ]
    },
    {
        title: "Maximum Subarray",
        description: "Given an integer array nums, find the subarray with the largest sum, and return its sum.",
        difficulty: "MEDIUM",
        constraints: "1 <= nums.length <= 10^5, -10^4 <= nums[i] <= 10^4",
        sampleInput: "[-2,1,-3,4,-1,2,1,-5,4]",
        sampleOutput: "6",
        timeLimit: 800,
        testCases: [
            { input: "[-2,1,-3,4,-1,2,1,-5,4]", expectedOutput: "6", isHidden: false, isSample: true },
            { input: "[1]", expectedOutput: "1", isHidden: false },
            { input: "[5,4,-1,7,8]", expectedOutput: "23", isHidden: false },
            { input: "[-1]", expectedOutput: "-1", isHidden: true }
        ]
    },
    {
        title: "Climbing Stairs",
        description: "You are climbing a staircase. It takes n steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?",
        difficulty: "EASY",
        constraints: "1 <= n <= 45",
        sampleInput: "2",
        sampleOutput: "2",
        timeLimit: 500,
        testCases: [
            { input: "2", expectedOutput: "2", isHidden: false, isSample: true },
            { input: "3", expectedOutput: "3", isHidden: false },
            { input: "1", expectedOutput: "1", isHidden: false },
            { input: "5", expectedOutput: "8", isHidden: true }
        ]
    }
];

async function seedQuestions() {
    for (const q of questions) {
        try {
            const res = await fetch('http://localhost:8081/api/v1/questions', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(q)
            });
            const result = await res.json();
            console.log(result.success ? `Created: ${q.title}` : result.message);
        } catch(e) {
            console.error(e);
        }
    }
}

seedQuestions();