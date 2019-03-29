package com.zemian.hellojava.data.dao;

import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.data.CommonDataConfig;
import com.zemian.hellojava.data.domain.Setting;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ContextConfiguration(classes = CommonDataConfig.class)
public class SettingDAOTest extends SpringTestBase {
    @Autowired
    private SettingDAO settingDAO;

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    public void findAll() {
        List<Setting> list = settingDAO.find(new Paging()).getList();
        assertThat(list.size(), greaterThanOrEqualTo(0));
    }

    @Test
    public void crud() {
        Setting test = new Setting();
        try {
            test.setCategory("TEST");
            test.setName("DataConfigTest");
            test.setValue("Foo");
            test.setType(Setting.Type.STRING);

            settingDAO.create(test);
            assertThat(test.getSettingId(), greaterThanOrEqualTo(1));

            Setting test2 = settingDAO.get(test.getSettingId());
            assertThat(test2.getSettingId(), is(test.getSettingId()));
            assertThat(test2.getCategory(), is("TEST"));
            assertThat(test2.getName(), is("DataConfigTest"));
            assertThat(test2.getValue(), is("Foo"));

            List<Setting> list = settingDAO.find(new Paging()).getList();
            assertThat(list.size(), greaterThanOrEqualTo(1));
        } finally {
            settingDAO.delete(test.getSettingId());
            try {
                settingDAO.get(test.getSettingId());
                Assert.fail("There should not be record id=" + test.getSettingId() + " after delete.");
            } catch (RuntimeException e) {
                // Expected.
            }
        }
    }

    @Test
    public void paging() {
        List<Setting> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Setting test = new Setting();
            test.setCategory("PAGING_TEST");
            test.setName("key" + i);
            test.setValue("Foo" + i);
            test.setType(Setting.Type.STRING);

            settingDAO.create(test);
            list.add(test);
        }

        try {
            Paging paging = new Paging(0, 25);
            PagingList<Setting> plist = settingDAO.find(paging);
            assertThat(plist.getList().size(), is(25));
            assertThat(plist.isMore(), is(true));
            assertThat(plist.getPaging().getOffset(), is(paging.getOffset()));
            assertThat(plist.getPaging().getSize(), is(paging.getSize()));

            int totalCount = jdbc.queryForObject("SELECT count(*) FROM settings", Integer.class);
            paging = new Paging(0, totalCount + 1);
            plist = settingDAO.find(paging);
            assertThat(plist.getList().size(), is(totalCount));
            assertThat(plist.isMore(), is(false));

            paging = new Paging(25, 25);
            PagingList<Setting> plist2 = settingDAO.find(paging);
            assertThat(plist2.getList().size(), is(25));
            assertThat(plist2.isMore(), is(true));
            assertThat(plist2.getList().get(0).getSettingId(), not(plist.getList().get(0).getSettingId()));

            paging = new Paging(totalCount - 25, 25);
            plist = settingDAO.find(paging);
            assertThat(plist.getList().size(), is(25));
            assertThat(plist.isMore(), is(false));

            // Test query by category group
            List<Setting> catList = settingDAO.findByCategory("PAGING_TEST");
            assertThat(catList.size(), is(100));
            assertThat(catList.stream().allMatch(s -> s.getCategory().equals("PAGING_TEST")), is(true));
        } finally {
            for (Setting setting : list) {
                settingDAO.delete(setting.getSettingId());
            }
        }
    }
}
