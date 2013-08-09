package org.motechproject.carereporting.xml;

import org.motechproject.carereporting.dao.AreaDao;
import org.motechproject.carereporting.dao.IndicatorCategoryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.LevelDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComplexDwQueryEntity;
import org.motechproject.carereporting.domain.ConditionEntity;
import org.motechproject.carereporting.domain.DwQueryEntity;
import org.motechproject.carereporting.domain.IndicatorCategoryEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.SimpleDwQueryEntity;
import org.motechproject.carereporting.xml.mapping.Category;
import org.motechproject.carereporting.xml.mapping.Denominator;
import org.motechproject.carereporting.xml.mapping.Dimension;
import org.motechproject.carereporting.xml.mapping.DwQuery;
import org.motechproject.carereporting.xml.mapping.Indicator;
import org.motechproject.carereporting.xml.mapping.Numerator;
import org.motechproject.carereporting.xml.mapping.WhereCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

@Component
@Transactional(readOnly = true)
public class XmlIndicatorParser {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private LevelDao levelDao;

    @Autowired
    private IndicatorCategoryDao indicatorCategoryDao;

    @Transactional
    public IndicatorEntity parse(InputStream is) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Indicator.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Indicator indicator = (Indicator) unmarshaller.unmarshal(is);
        return createIndicatorEntityFromXmlIndicator(indicator);
    }

    private IndicatorEntity createIndicatorEntityFromXmlIndicator(Indicator indicator) {
        IndicatorEntity indicatorEntity = new IndicatorEntity();
        indicatorEntity.setName(indicator.getName());
        indicatorEntity.setArea(findAreaByNameAndLevelName(indicator.getArea().getName(), indicator.getArea().getLevel().toString()));
        indicatorEntity.setCategories(prepareIndicatorCategories(indicator.getCategories()));
        indicatorEntity.setDefaultFrequency(indicator.getDefaultFrequency().getValue());
        indicatorEntity.setDenominator(prepareDenominator(indicator.getDenominator()));
        if (indicator.getNumerator() != null) {
            indicatorEntity.setNumerator(prepareNumerator(indicator.getNumerator()));
        }
        return indicatorEntity;
    }

    private AreaEntity findAreaByNameAndLevelName(String name, String levelName) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", name);
        LevelEntity level = levelDao.getByField("name", levelName);
        fields.put("level", level);
        return areaDao.getByFields(fields);
    }

    private Set<IndicatorCategoryEntity> prepareIndicatorCategories(List<Category> categories) {
        Set<IndicatorCategoryEntity> indicatorCategories = new HashSet<>();
        for (Category category: categories) {
            IndicatorCategoryEntity indicatorCategory = indicatorCategoryDao.getByField("name", category.getName());
            indicatorCategories.add(indicatorCategory);
        }
        return indicatorCategories;
    }

    private DwQueryEntity prepareDenominator(Denominator denominator) {
        if (denominator.getIndicatorId() != null) {
            return indicatorDao.getByIdWithFields(denominator.getIndicatorId(), "denominator").getDenominator();
        } else {
            return prepareDwQuery(denominator.getDwQuery());
        }
    }

    private SimpleDwQueryEntity prepareDimension(Dimension dimension) {
        SimpleDwQueryEntity simpleDwQueryEntity = new SimpleDwQueryEntity();
        simpleDwQueryEntity.setTableName(dimension.getName());
        simpleDwQueryEntity.setWhereConditions(prepareWhereConditions(dimension.getWhereConditions()));
        return simpleDwQueryEntity;
    }

    private Set<ConditionEntity> prepareWhereConditions(List<WhereCondition> whereConditions) {
        whereConditions.toString();
        return null;
    }

    private DwQueryEntity prepareNumerator(Numerator numerator) {
        if (numerator.getIndicatorId() != null) {
            return indicatorDao.getByIdWithFields(numerator.getIndicatorId(), "denominator").getDenominator();
        } else {
            return prepareDwQuery(numerator.getDwQuery());
        }
    }

    private DwQueryEntity prepareDwQuery(DwQuery dwQuery) {
        ComplexDwQueryEntity dwQueryEntity = new ComplexDwQueryEntity();
        dwQueryEntity.setDimension(prepareDimension(dwQuery.getDimension()));
        return dwQueryEntity;
    }
}
