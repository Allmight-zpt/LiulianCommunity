package com.zhupeiting.bisheproject.model;

public class Users {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.ACCOUNT_ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private String accountId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.NAME
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.TOKEN
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private String token;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.GMT_CREATE
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.GMT_MODIFIED
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.BIO
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private String bio;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column MY_USER.AVATAR_URL
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    private String avatarUrl;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.ID
     *
     * @return the value of MY_USER.ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.ID
     *
     * @param id the value for MY_USER.ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.ACCOUNT_ID
     *
     * @return the value of MY_USER.ACCOUNT_ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.ACCOUNT_ID
     *
     * @param accountId the value for MY_USER.ACCOUNT_ID
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.NAME
     *
     * @return the value of MY_USER.NAME
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.NAME
     *
     * @param name the value for MY_USER.NAME
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.TOKEN
     *
     * @return the value of MY_USER.TOKEN
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.TOKEN
     *
     * @param token the value for MY_USER.TOKEN
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.GMT_CREATE
     *
     * @return the value of MY_USER.GMT_CREATE
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.GMT_CREATE
     *
     * @param gmtCreate the value for MY_USER.GMT_CREATE
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.GMT_MODIFIED
     *
     * @return the value of MY_USER.GMT_MODIFIED
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.GMT_MODIFIED
     *
     * @param gmtModified the value for MY_USER.GMT_MODIFIED
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.BIO
     *
     * @return the value of MY_USER.BIO
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public String getBio() {
        return bio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.BIO
     *
     * @param bio the value for MY_USER.BIO
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setBio(String bio) {
        this.bio = bio == null ? null : bio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column MY_USER.AVATAR_URL
     *
     * @return the value of MY_USER.AVATAR_URL
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column MY_USER.AVATAR_URL
     *
     * @param avatarUrl the value for MY_USER.AVATAR_URL
     *
     * @mbg.generated Sat Nov 26 21:36:33 CST 2022
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }
}