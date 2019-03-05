package com.zemian.hellojava.service;

import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.data.domain.Setting;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@ContextConfiguration(classes = CommonServiceConfig.class)
public class SettingServiceTest extends SpringTestBase {
    @Autowired
    private SettingService settingService;

    @Test
    public void category() {
        List<Setting> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String group = (i % 2 == 0) ? "A" : "B";
            Setting test = new Setting();
            test.setCategory("CATEGORY_TEST_" + group);
            test.setName("key" + i);
            test.setValue("" + i);
            test.setType(Setting.Type.INTEGER);

            settingService.create(test);
            list.add(test);
        }

        try {
            // Find by category.
            Map<String, Setting> list2 = settingService.getCategoryMap("CATEGORY_TEST_A");
            assertThat(list2.size(), is(5));
            list2.entrySet().stream().forEach(e -> {
                String name = e.getKey();
                Setting setting = e.getValue();
                assertThat(name, startsWith("key"));
                assertThat(setting.getCategory(), is("CATEGORY_TEST_A"));
                assertThat(setting.getName(), is(name));
                Integer n = setting.getValueByType();
                assertThat(n % 2, is(0));
            });

            list2 = settingService.getCategoryMap("CATEGORY_TEST_B");
            list2.entrySet().stream().forEach(e -> {
                String name = e.getKey();
                Setting setting = e.getValue();
                assertThat(name, startsWith("key"));
                assertThat(setting.getCategory(), is("CATEGORY_TEST_B"));
                assertThat(setting.getName(), is(name));
                Integer n = setting.getValueByType();
                assertThat(n % 2, is(1));
            });
            assertThat(list2.size(), is(5));
        } finally {
            for (Setting setting : list) {
                settingService.delete(setting.getSettingId());
            }
        }
    }

    //@Ignore
    @Test
    public void createSampleSettings() {
        if (!settingService.exists("TEST", "test")) {
            Setting test = new Setting();
            test.setCategory("TEST");
            test.setName("test");
            test.setValue("foo");
            test.setType(Setting.Type.STRING);
            settingService.create(test);

            for (int i = 0; i < 100; i++) {
                test = new Setting();
                test.setCategory("TEST");
                test.setName("test_" + i);
                test.setValue("foo");
                test.setType(Setting.Type.STRING);
                settingService.create(test);
            }
        }
    }
}