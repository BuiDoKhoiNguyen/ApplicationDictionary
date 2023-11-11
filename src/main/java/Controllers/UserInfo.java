package Controllers;

import java.util.Arrays;

public class UserInfo {
    private int userId;
    private String name;
    private String username;
    private byte[] profileImage;

    public UserInfo() {
        
    }

    public UserInfo(int userId, String name, String username, byte[] profileImage) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", username='" + username ;
    }
}
