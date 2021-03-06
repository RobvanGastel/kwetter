/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.robvangastel.kwetter.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Rob
 */

@Entity
public class User implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String role;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    
    @JsonIgnore
    @Column(nullable = false, length = 20)
    private String password;
    private String location;
    private String websiteUrl;
    
    @Column(length = 160)
    private String bio;

    @JsonBackReference
    @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<Tweet> tweets = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
    private List<User> following = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "following")
    private List<User> followers = new ArrayList<>();
    
    public User(Role role, String email, String username, String password) {
        this.role = role.toString();
        this.email = email;

        this.username = username;
        this.password = password;

        this.location = "";
        this.websiteUrl = "";
        this.bio = "";
    }
      
    public User() {}

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the role
     */
    public Role getRole() {
        if(role.equalsIgnoreCase(Role.ADMINISTRATOR.toString())) {
			return Role.ADMINISTRATOR;
        } else if(role.equalsIgnoreCase(Role.MODERATOR.toString())) {
		    return Role.MODERATOR;
	    } else if(role.equalsIgnoreCase(Role.USER.toString())) {
		    return Role.USER;
	    }
	    return Role.USER;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role.toString();
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
       this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the websiteUrl
     */
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    /**
     * @param websiteUrl the websiteUrl to set
     */
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * @param bio the bio to set
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * @return the tweets
     */
    public List<Tweet> getTweets() {
        return tweets;
    }
    
    /**
     * Add a Tweet to an User
     * @param tweet Tweet to add
     */
    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    /**
     * @param tweets the tweets to set
     */
    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
    
    /**
     * @return the following
     */
    public List<User> getFollowing() {
        return following;
    }

    /**
     * Add a following User
     * @param user Following User to add
     */
    public void addFollowing(User user) {
        following.add(user);
    }
    
    /**
     * Remove a following User
     * @param user 
     */
    public void removeFollowing(User user) {
        following.remove(user);
    }
    
    /**
     * @param following the following to set
     */
    public void setFollowing(List<User> following) {
        this.following = following;
    }

    /**
     * @return the followers
     */
    public List<User> getFollowers() {
        return followers;
    }

    /**
     * Add a Follower to a User
     * @param follower Follower to add
     */
    public void addFollower(User follower) {
        followers.add(follower);
    }
    
    /**
     * Remove a Follower of a User
     * @param follower 
     */
    public void removeFollower(User follower) {
        followers.remove(follower);
    }
    
    /**
     * @param followers the followers to set
     */
    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

	/**
	 * Deserializes an Object of class User from its JSON representation
	 * @param jsonRepresentation JSON String
	 * @return User object or null when an error occurs
	 */
	public static User fromString(String jsonRepresentation) {
		ObjectMapper mapper = new ObjectMapper(); //Jackson's JSON marshaller
		User o= null;
		try {
			o = mapper.readValue(jsonRepresentation, User.class );
		} catch (IOException e) {
			throw new WebApplicationException();
		}
		return o;
	}
}
