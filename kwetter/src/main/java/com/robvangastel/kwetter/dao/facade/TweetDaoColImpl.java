/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robvangastel.kwetter.dao.facade;

import com.robvangastel.kwetter.dao.ITweetDao;
import com.robvangastel.kwetter.domain.Tweet;
import com.robvangastel.kwetter.domain.User;
import com.robvangastel.kwetter.exception.TweetException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

/**
 *
 * @author Rob
 */

@Default
@Stateful
public class TweetDaoColImpl implements ITweetDao {

    private final CopyOnWriteArrayList<Tweet> tweets = new CopyOnWriteArrayList<>();
    private static int INCREMENT = 0;


    private long getIncrement() {
        INCREMENT++;
        return (long) INCREMENT;
    }

    @Override
    public Tweet findById(long id) {
        for(Tweet tweet : tweets) {
            if(tweet.getId().equals(id)) {
                return tweet;
            }
        }
        return null;
    }

    @Override
    public List<Tweet> findByMessage(String message) {
        List<Tweet> tweetsFound = new ArrayList<>();

        for(Tweet tweet : tweets) {
            if(tweet.getMessage().equals(message)) {
                tweetsFound.add(tweet);
            }
        }
        return tweetsFound;
    }

    @Override
    public List<Tweet> findByUser(long id) {
        List<Tweet> tweetsFound = new ArrayList<>();

        for(Tweet tweet : tweets) {
            if(tweet.getUser().getId().equals(id)) {
                tweetsFound.add(tweet);
            }
        }
        return tweetsFound;
    }

	@Override
	public List<Tweet> findForUser(User user) {
		List<Tweet> tweetsFound = new ArrayList<>();
		List<Long> ids = new ArrayList<>();

		for (User follower : user.getFollowing()) {
			ids.add(follower.getId());
		}
		ids.add(user.getId());

		for(Tweet tweet : tweets) {
			if(ids.contains(tweet.getUser().getId())) {
				tweetsFound.add(tweet);
			}
		}

		return tweetsFound;
	}

	@Override
	public List<Tweet> findAllOrderedByDate() {
		return tweets;
	}

	@Override
    public Tweet create(Tweet entity) {
        if(!entity.getMessage().isEmpty()) {
            if(entity.getMessage().length() < 141 && entity.getMessage().length() > 0) {
                entity.setId(getIncrement());
                tweets.add(entity);
                return entity;
            }
        }
	    return null;
    }

    public Tweet update(Tweet entity) {
       for(Tweet tweet : tweets) {
            if(tweet.getId().equals(entity.getId())) {
                tweet = entity;
            }
        }
        return null;
    }

    @Override
    public void delete(Tweet entity) {
        tweets.remove(entity);
    }

    @Override
    public void deleteById(long id) throws TweetException {
        for(Tweet tweet : tweets) {
            if(tweet.getId().equals(id)) {
                tweets.remove(tweet);
	            return;
            }
        }
	    throw new TweetException("Tweet is not found.");
    }

    @Override
    public List<Tweet> findAll() {
        return tweets;
    }
}
