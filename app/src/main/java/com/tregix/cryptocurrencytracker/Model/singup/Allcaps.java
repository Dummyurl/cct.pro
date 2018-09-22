
package com.tregix.cryptocurrencytracker.Model.singup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Allcaps {

    @SerializedName("edit_posts")
    @Expose
    private Boolean editPosts;
    @SerializedName("read")
    @Expose
    private Boolean read;
    @SerializedName("level_1")
    @Expose
    private Boolean level1;
    @SerializedName("level_0")
    @Expose
    private Boolean level0;
    @SerializedName("delete_posts")
    @Expose
    private Boolean deletePosts;
    @SerializedName("vc_access_rules_post_types")
    @Expose
    private Boolean vcAccessRulesPostTypes;
    @SerializedName("vc_access_rules_backend_editor")
    @Expose
    private Boolean vcAccessRulesBackendEditor;
    @SerializedName("vc_access_rules_frontend_editor")
    @Expose
    private Boolean vcAccessRulesFrontendEditor;
    @SerializedName("vc_access_rules_post_settings")
    @Expose
    private Boolean vcAccessRulesPostSettings;
    @SerializedName("vc_access_rules_templates")
    @Expose
    private Boolean vcAccessRulesTemplates;
    @SerializedName("vc_access_rules_shortcodes")
    @Expose
    private Boolean vcAccessRulesShortcodes;
    @SerializedName("vc_access_rules_grid_builder")
    @Expose
    private Boolean vcAccessRulesGridBuilder;
    @SerializedName("vc_access_rules_presets")
    @Expose
    private Boolean vcAccessRulesPresets;
    @SerializedName("vc_access_rules_dragndrop")
    @Expose
    private Boolean vcAccessRulesDragndrop;
    @SerializedName("contributor")
    @Expose
    private Boolean contributor;

    public Boolean getEditPosts() {
        return editPosts;
    }

    public void setEditPosts(Boolean editPosts) {
        this.editPosts = editPosts;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getLevel1() {
        return level1;
    }

    public void setLevel1(Boolean level1) {
        this.level1 = level1;
    }

    public Boolean getLevel0() {
        return level0;
    }

    public void setLevel0(Boolean level0) {
        this.level0 = level0;
    }

    public Boolean getDeletePosts() {
        return deletePosts;
    }

    public void setDeletePosts(Boolean deletePosts) {
        this.deletePosts = deletePosts;
    }

    public Boolean getVcAccessRulesPostTypes() {
        return vcAccessRulesPostTypes;
    }

    public void setVcAccessRulesPostTypes(Boolean vcAccessRulesPostTypes) {
        this.vcAccessRulesPostTypes = vcAccessRulesPostTypes;
    }

    public Boolean getVcAccessRulesBackendEditor() {
        return vcAccessRulesBackendEditor;
    }

    public void setVcAccessRulesBackendEditor(Boolean vcAccessRulesBackendEditor) {
        this.vcAccessRulesBackendEditor = vcAccessRulesBackendEditor;
    }

    public Boolean getVcAccessRulesFrontendEditor() {
        return vcAccessRulesFrontendEditor;
    }

    public void setVcAccessRulesFrontendEditor(Boolean vcAccessRulesFrontendEditor) {
        this.vcAccessRulesFrontendEditor = vcAccessRulesFrontendEditor;
    }

    public Boolean getVcAccessRulesPostSettings() {
        return vcAccessRulesPostSettings;
    }

    public void setVcAccessRulesPostSettings(Boolean vcAccessRulesPostSettings) {
        this.vcAccessRulesPostSettings = vcAccessRulesPostSettings;
    }

    public Boolean getVcAccessRulesTemplates() {
        return vcAccessRulesTemplates;
    }

    public void setVcAccessRulesTemplates(Boolean vcAccessRulesTemplates) {
        this.vcAccessRulesTemplates = vcAccessRulesTemplates;
    }

    public Boolean getVcAccessRulesShortcodes() {
        return vcAccessRulesShortcodes;
    }

    public void setVcAccessRulesShortcodes(Boolean vcAccessRulesShortcodes) {
        this.vcAccessRulesShortcodes = vcAccessRulesShortcodes;
    }

    public Boolean getVcAccessRulesGridBuilder() {
        return vcAccessRulesGridBuilder;
    }

    public void setVcAccessRulesGridBuilder(Boolean vcAccessRulesGridBuilder) {
        this.vcAccessRulesGridBuilder = vcAccessRulesGridBuilder;
    }

    public Boolean getVcAccessRulesPresets() {
        return vcAccessRulesPresets;
    }

    public void setVcAccessRulesPresets(Boolean vcAccessRulesPresets) {
        this.vcAccessRulesPresets = vcAccessRulesPresets;
    }

    public Boolean getVcAccessRulesDragndrop() {
        return vcAccessRulesDragndrop;
    }

    public void setVcAccessRulesDragndrop(Boolean vcAccessRulesDragndrop) {
        this.vcAccessRulesDragndrop = vcAccessRulesDragndrop;
    }

    public Boolean getContributor() {
        return contributor;
    }

    public void setContributor(Boolean contributor) {
        this.contributor = contributor;
    }

}
