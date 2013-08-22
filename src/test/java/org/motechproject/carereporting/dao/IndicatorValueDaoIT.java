package org.motechproject.carereporting.dao;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.FrequencyEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class IndicatorValueDaoIT extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IndicatorValueDao indicatorValueDao;

    @Test
    @Ignore
    public void testRemoveByIndicator() {
        Integer id = 3;
        FrequencyEntity frequency = new FrequencyEntity();
        frequency.setId(id);
        AreaEntity area = new AreaEntity();
        area.setId(id);
        IndicatorEntity indicator = new IndicatorEntity();
        indicator.setId(id);
        IndicatorValueEntity indicatorValueEntity = new IndicatorValueEntity();
        indicatorValueEntity.setValue(BigDecimal.TEN);
        indicatorValueEntity.setDenominator(BigDecimal.ONE);
        indicatorValueEntity.setNumerator(BigDecimal.TEN);
        indicatorValueEntity.setDate(new Date());
        indicatorValueEntity.setFrequency(frequency);
        indicatorValueEntity.setArea(area);
        indicatorValueEntity.setIndicator(indicator);
        indicatorValueDao.save(indicatorValueEntity);

        indicatorValueDao.removeByIndicator(indicator);

        List<IndicatorValueEntity> valueEntities1 = indicatorValueDao.getIndicatorValuesForArea(id, id, id, DateUtils.addDays(new Date(), -1), DateUtils.addDays(new Date(), -1));

        assertEquals(0, valueEntities1.size());
    }

}
