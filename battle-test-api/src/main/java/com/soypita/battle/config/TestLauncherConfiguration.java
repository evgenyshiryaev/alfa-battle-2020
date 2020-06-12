package com.soypita.battle.config;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestLauncherConfiguration {
    @Bean
    public SummaryGeneratingListener listener() {
        return new SummaryGeneratingListener();
    }

    @Bean
    public Launcher launcher(SummaryGeneratingListener listener) {
        var launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        return launcher;
    }
}
