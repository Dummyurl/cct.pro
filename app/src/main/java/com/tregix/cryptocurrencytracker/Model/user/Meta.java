
package com.tregix.cryptocurrencytracker.Model.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("nickname")
    @Expose
    private List<String> nickname = null;
    @SerializedName("first_name")
    @Expose
    private List<String> firstName = null;
    @SerializedName("last_name")
    @Expose
    private List<String> lastName = null;
    @SerializedName("description")
    @Expose
    private List<String> description = null;
    @SerializedName("rich_editing")
    @Expose
    private List<String> richEditing = null;
    @SerializedName("syntax_highlighting")
    @Expose
    private List<String> syntaxHighlighting = null;
    @SerializedName("comment_shortcuts")
    @Expose
    private List<String> commentShortcuts = null;
    @SerializedName("admin_color")
    @Expose
    private List<String> adminColor = null;
    @SerializedName("use_ssl")
    @Expose
    private List<String> useSsl = null;
    @SerializedName("show_admin_bar_front")
    @Expose
    private List<String> showAdminBarFront = null;
    @SerializedName("locale")
    @Expose
    private List<String> locale = null;
    @SerializedName("wp_capabilities")
    @Expose
    private List<String> wpCapabilities = null;
    @SerializedName("wp_user_level")
    @Expose
    private List<String> wpUserLevel = null;
    @SerializedName("session_tokens")
    @Expose
    private List<String> sessionTokens = null;

    public List<String> getNickname() {
        return nickname;
    }

    public void setNickname(List<String> nickname) {
        this.nickname = nickname;
    }

    public List<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(List<String> firstName) {
        this.firstName = firstName;
    }

    public List<String> getLastName() {
        return lastName;
    }

    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getRichEditing() {
        return richEditing;
    }

    public void setRichEditing(List<String> richEditing) {
        this.richEditing = richEditing;
    }

    public List<String> getSyntaxHighlighting() {
        return syntaxHighlighting;
    }

    public void setSyntaxHighlighting(List<String> syntaxHighlighting) {
        this.syntaxHighlighting = syntaxHighlighting;
    }

    public List<String> getCommentShortcuts() {
        return commentShortcuts;
    }

    public void setCommentShortcuts(List<String> commentShortcuts) {
        this.commentShortcuts = commentShortcuts;
    }

    public List<String> getAdminColor() {
        return adminColor;
    }

    public void setAdminColor(List<String> adminColor) {
        this.adminColor = adminColor;
    }

    public List<String> getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(List<String> useSsl) {
        this.useSsl = useSsl;
    }

    public List<String> getShowAdminBarFront() {
        return showAdminBarFront;
    }

    public void setShowAdminBarFront(List<String> showAdminBarFront) {
        this.showAdminBarFront = showAdminBarFront;
    }

    public List<String> getLocale() {
        return locale;
    }

    public void setLocale(List<String> locale) {
        this.locale = locale;
    }

    public List<String> getWpCapabilities() {
        return wpCapabilities;
    }

    public void setWpCapabilities(List<String> wpCapabilities) {
        this.wpCapabilities = wpCapabilities;
    }

    public List<String> getWpUserLevel() {
        return wpUserLevel;
    }

    public void setWpUserLevel(List<String> wpUserLevel) {
        this.wpUserLevel = wpUserLevel;
    }

    public List<String> getSessionTokens() {
        return sessionTokens;
    }

    public void setSessionTokens(List<String> sessionTokens) {
        this.sessionTokens = sessionTokens;
    }

}
