package com.technion.coolie.tecmind.server;

import java.util.List;

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
   * @param user
   *          - the user to add with all the necessary fields initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addUser(TecUser user);

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
   * 
   * @param topBestPost
   *          - the topBestPost to add with all the necessary fields initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTopBestPost(TecTopBestPost topBestPost);

  /**
   * 
   * @param topBestPost
   *          - the topBestPost to remove with the id field initialized
   *          correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeTopBestPost(TecTopBestPost topBestPost);

  /**
   * @return - top 10 TopBestPost by their technionValue field
   */
  public List<TecPost> getTopBestPosts();

  /**
   * 
   * @param topBestComment
   *          - the topBestComment to add with all the necessary fields
   *          initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTopBestComment(TecTopBestComment topBestComment);

  /**
   * 
   * @param topBestComment
   *          - the topBestComment to remove with the id field initialized
   *          correctly
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode removeTopBestComment(TecTopBestComment topBestComment);

  /**
   * @return - top 10 TecTopBestComment by their technionValue field
   */
  public List<TecComment> getTopBestComments();

  /**
   * 
   * @param tecPost
   *          - the tecPost to add with all the necessary fields initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecPost(TecPost tecPost);

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
   * @param tecComment
   *          - the tecComment to add with all the necessary fields initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecComment(TecComment tecComment);

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
   * @param tecLike
   *          - the tecLike to add with all the necessary fields initialized
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecLike(TecLike tecLike);

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
   * @param posts
   *          - the list of TecPost to add
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecPostList(List<TecPost> posts);

  /**
   * 
   * @param comments
   *          - the list of TecComment to add
   * @return - SUCCESS if went well, error code otherwise
   */
  public ReturnCode addTecCommentList(List<TecComment> comments);
}
