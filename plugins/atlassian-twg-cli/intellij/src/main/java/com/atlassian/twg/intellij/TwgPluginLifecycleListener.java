package com.atlassian.twg.intellij;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import org.jetbrains.annotations.NotNull;

public final class TwgPluginLifecycleListener implements DynamicPluginListener {
    private static final PluginId PLUGIN_ID = PluginId.getId("com.atlassian.twg.cli");

    @Override
    public void pluginLoaded(@NotNull IdeaPluginDescriptor pluginDescriptor) {
        if (!PLUGIN_ID.equals(pluginDescriptor.getPluginId())) {
            return;
        }

        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            if (!project.isDisposed()) {
                TwgOnboardingActivity.showIfEligible(project);
                return;
            }
        }
    }
}
