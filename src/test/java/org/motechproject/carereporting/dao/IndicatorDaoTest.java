package org.motechproject.carereporting.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.motechproject.carereporting.exception.CareNullArgumentRuntimeException;
import org.motechproject.carereporting.exception.CareResourceNotFoundRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class IndicatorDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IndicatorDao indicatorDao;

    @Test(expected = CareNullArgumentRuntimeException.class)
    public void shouldThrowCareNullArgumentExceptionWhenEntityIdIsNull() {
        indicatorDao.findById(null);
    }

    @Test(expected = CareResourceNotFoundRuntimeException.class)
    public void shouldThrowCareResourceNotFoundExceptionWhenEntityDoesNotExistsInDatabase() {
        indicatorDao.findById(1);
    }

}
