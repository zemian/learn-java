package com.zemian.hellojava.spring.tx;

public class TestParam {
    private String username;
    private String settingName;
    private boolean generateExceptionOnUserDAO;
    private boolean generateExceptionOnSettingDAO;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public boolean isGenerateExceptionOnUserDAO() {
        return generateExceptionOnUserDAO;
    }

    public void setGenerateExceptionOnUserDAO(boolean generateExceptionOnUserDAO) {
        this.generateExceptionOnUserDAO = generateExceptionOnUserDAO;
    }

    public boolean isGenerateExceptionOnSettingDAO() {
        return generateExceptionOnSettingDAO;
    }

    public void setGenerateExceptionOnSettingDAO(boolean generateExceptionOnSettingDAO) {
        this.generateExceptionOnSettingDAO = generateExceptionOnSettingDAO;
    }
}
