package leetcode.heap;

import java.util.*;

/**
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：
 * <p>
 * postTweet(userId, tweetId): 创建一条新的推文
 * getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * follow(followerId, followeeId): 关注一个用户
 * unfollow(followerId, followeeId): 取消关注一个用户
 */

class Twitter {
    private static int timestamp = 0;
    private static Map<Integer, User> userMap = new HashMap<>();

    public static class User {
        private Set<Integer> follower;
        private int userId;
        private Tweet head;

        public User(int userId) {
            this.userId = userId;
            this.head = null;
            follower = new HashSet<>();
            follow(userId);
        }

        public Set<Integer> getFollower() {
            return follower;
        }

        public User(Set<Integer> follower, int userId, Tweet head) {
            this.follower = follower;
            this.userId = userId;
            this.head = head;
        }

        public void follow(int userId) {
            follower.add(userId);
        }

        public void unfollow(int userId) {
            if (userId != userId) {
                follower.remove(userId);
            }
            follower.remove(userId);
        }

        public void post(int tweetId) {
            Tweet tw = new Tweet(tweetId, timestamp);
            timestamp++;
            tw.next = head;
            head = tw;
        }
    }

    public static class Tweet {
        private int id;
        private int time;
        private Tweet next;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    /**
     * Initialize your data structure here.
     */
    public Twitter() {

    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        User user = userMap.get(userId);
        if (null == user) {
            user = new User(userId);
            userMap.put(userId, user);
        }
        user.post(tweetId);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        // 获取帖子链表
        List<Integer> res = new ArrayList<>();

        Set<Integer> followers = userMap.get(userId).getFollower();

        //　合并多条链表
        PriorityQueue<Tweet> queue = new PriorityQueue<>((o1, o2) -> o2.time - o1.time);

        for (Integer follower : followers) {
            Tweet tw = userMap.get(follower).head;
            if (tw == null) continue;
            queue.add(tw);
        }

        while (!queue.isEmpty()) {
            if (res.size() == 10) break;
            Tweet tw = queue.poll();
            res.add(tw.id);
            if (tw.next != null) {
                queue.add(tw.next);
            }
        }
        return res;

    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        User followee = userMap.get(followeeId);
        if (followee == null) {
            User user = new User(followeeId);
            userMap.put(followeeId, user);
        }
        User follow = userMap.get(followerId);
        if (follow == null) {
            User user = new User(followerId);
            userMap.put(followerId, user);
        }
        userMap.get(followerId).follow(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        userMap.get(followerId).unfollow(followeeId);
    }
}

public class No355DesignTwitter {
    public static void main(String[] args) {
        Twitter obj = new Twitter();
        obj.postTweet(1, 1);

        print(obj, 1);

        obj.follow(2, 1);
        print(obj, 2);

        obj.unfollow(2,1);
        obj.getNewsFeed(2);

    }


    public static void print(Twitter obj, int useId) {
        List<Integer> param_2 = obj.getNewsFeed(useId);
        param_2.stream().forEach(System.out::println);
        System.out.println("--------------------");
    }
}
