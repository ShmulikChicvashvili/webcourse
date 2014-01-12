package com.technion.coolie.server.techmine;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.technion.coolie.server.techmine.manager.TecGroup;

/**
 * 
 * Created on 8/12/2013
 * 
 * @author Omer Shpigelman <omer.shpigelman@gmail.com>
 * 
 */
public interface ITechmineAPI {

  /**
   * 
   * @param users
   *          - the list of users to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode addUsers(List<TecUser> users) throws IOException;

  /**
   * 
   * @param users
   *          - the list of users to update with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode updateUsers(List<TecUser> users) throws IOException;

  /**
   * 
   * @param user
   *          - the user to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeUser(TecUser user) throws IOException;

  /**
   * @param user
   *          - the user to get with the id field initialized correctly
   * @return - the requested user
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public TecUser getUser(TecUser user) throws JsonSyntaxException, IOException;

  /**
   * @return - top 10 TecPost by their technionValue field
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecPost> getTopBestPosts() throws JsonSyntaxException,
      IOException;

  /**
   * @return - top 10 TecComment by their technionValue field
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecComment> getTopBestComments() throws JsonSyntaxException,
      IOException;

  /**
   * @return - top 10 TecUser by their totalTechoins field
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecUser> getTopBestMiners() throws JsonSyntaxException,
      IOException;

  /**
   * @return - top 10 TecUser of the week by their totalTechoins field
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecUser> getTopBestMinersOfTheWeek() throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param tecPosts
   *          - the list of tecPosts to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode addTecPosts(List<TecPost> tecPosts) throws IOException;

  /**
   * 
   * @param tecPost
   *          - the tecPost to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeTecPost(TecPost tecPost) throws IOException;

  /**
   * @param tecPost
   *          - the tecPost to get with the id field initialized correctly
   * @return - the requested tecPost
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public TecPost getTecPost(TecPost tecPost) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param tecComments
   *          - the list of tecComments to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode addTecComments(List<TecComment> tecComments)
      throws IOException;

  /**
   * 
   * @param tecComment
   *          - the tecComment to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeTecComment(TecComment tecComment) throws IOException;

  /**
   * @param tecComment
   *          - the tecComment to get with the id field initialized correctly
   * @return - the requested tecComment
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public TecComment getTecComment(TecComment tecComment)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @param tecLikes
   *          - the list of tecLikes to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode addTecLikes(List<TecLike> tecLikes) throws IOException;

  /**
   * 
   * @param tecLike
   *          - the tecLike to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   * @throws IOException
   */
  public ReturnCode removeTecLike(TecLike tecLike) throws IOException;

  /**
   * @param tecLike
   *          - the tecLike to get with the id field initialized correctly
   * @return - the requested tecLike
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public TecLike getTecLike(TecLike tecLike) throws JsonSyntaxException,
      IOException;

  /**
   * 
   * @param user
   *          - the user look for his posts
   * @return - List<TecPost> which includes all user's posts
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecPost> getAllUserPosts(TecUser user)
      throws JsonSyntaxException, IOException;

  /**
   * 
   * @return - List<TecGroup> which includes all valid goups
   * @throws IOException
   * @throws JsonSyntaxException
   */
  public List<TecGroup> getValidGroups() throws JsonSyntaxException,
      IOException;

}
