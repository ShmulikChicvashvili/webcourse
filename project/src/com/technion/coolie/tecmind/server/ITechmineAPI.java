package com.technion.coolie.tecmind.server;
import java.util.List;

import com.technion.coolie.tecmind.server.manager.TecGroup;
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
   */
  public ReturnCode addUsers(List<TecUser> users);

  /**
   * 
   * @param users
   *          - the list of users to update with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode updateUsers(List<TecUser> users);

  /**
   * 
   * @param user
   *          - the user to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeUser(TecUser user);

  /**
   * @param user
   *          - the user to get with the id field initialized correctly
   * @return - the requested user
   */
  public TecUser getUser(TecUser user);

  /**
   * @return - top 10 TecPost by their technionValue field
   */
  public List<TecPost> getTopBestPosts();

  /**
   * @return - top 10 TecComment by their technionValue field
   */
  public List<TecComment> getTopBestComments();

  /**
   * @return - top 10 TecUser by their totalTechoins field
   */
  public List<TecUser> getTopBestMiners();

  /**
   * @return - top 10 TecUser of the week by their totalTechoins field
   */
  public List<TecUser> getTopBestMinersOfTheWeek();

  /**
   * 
   * @param tecPosts
   *          - the list of tecPosts to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecPosts(List<TecPost> tecPosts);

  /**
   * 
   * @param tecPost
   *          - the tecPost to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeTecPost(TecPost tecPost);

  /**
   * @param tecPost
   *          - the tecPost to get with the id field initialized correctly
   * @return - the requested tecPost
   */
  public TecPost getTecPost(TecPost tecPost);

  /**
   * 
   * @param tecComments
   *          - the list of tecComments to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecComments(List<TecComment> tecComments);

  /**
   * 
   * @param tecComment
   *          - the tecComment to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeTecComment(TecComment tecComment);

  /**
   * @param tecComment
   *          - the tecComment to get with the id field initialized correctly
   * @return - the requested tecComment
   */
  public TecComment getTecComment(TecComment tecComment);

  /**
   * 
   * @param tecLikes
   *          - the list of tecLikes to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecLikes(List<TecLike> tecLikes);

  /**
   * 
   * @param tecLike
   *          - the tecLike to remove with the id field initialized correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeTecLike(TecLike tecLike);

  /**
   * @param tecLike
   *          - the tecLike to get with the id field initialized correctly
   * @return - the requested tecLike
   */
  public TecLike getTecLike(TecLike tecLike);

  /**
   * 
   * @param user
   *          - the user look for his posts
   * @return - List<TecPost> which includes all user's posts
   */
  public List<TecPost> getAllUserPosts(TecUser user);

  /**
   * 
   * @return - List<TecGroup> which includes all valid goups
   */
  public List<TecGroup> getValidGroups();

}
