package com.gusi.androidx.module.net;

import java.util.Arrays;

/**
 * @author Ylw
 * @since 2022/6/12 21:23
 */
public class Login {
    private boolean admin;
    private Object[] chapterTops;
    private int coinCount;
    private int[] collectIds;
    private String email;
    private String icon;
    private int id;
    private int type;
    private String nickname;
    private String password;
    private String publicName;
    private String token;
    private String username;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Object[] getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(Object[] chapterTops) {
        this.chapterTops = chapterTops;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int[] getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(int[] collectIds) {
        this.collectIds = collectIds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Login{" +
                "admin=" + admin +
                ", chapterTops=" + Arrays.toString(chapterTops) +
                ", coinCount=" + coinCount +
                ", collectIds=" + Arrays.toString(collectIds) +
                ", email='" + email + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", publicName='" + publicName + '\'' +
                ", token='" + token + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
