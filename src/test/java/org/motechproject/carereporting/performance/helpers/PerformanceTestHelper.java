package org.motechproject.carereporting.performance.helpers;

import org.joda.time.DateTime;
import org.motechproject.carereporting.dao.DwQueryDao;
import org.motechproject.carereporting.dao.IndicatorDao;
import org.motechproject.carereporting.dao.LanguageDao;
import org.motechproject.carereporting.domain.AreaEntity;
import org.motechproject.carereporting.domain.ComputedFieldEntity;
import org.motechproject.carereporting.domain.FieldOperationEntity;
import org.motechproject.carereporting.domain.FormEntity;
import org.motechproject.carereporting.domain.IndicatorClassificationEntity;
import org.motechproject.carereporting.domain.IndicatorEntity;
import org.motechproject.carereporting.domain.IndicatorValueEntity;
import org.motechproject.carereporting.domain.LanguageEntity;
import org.motechproject.carereporting.domain.LevelEntity;
import org.motechproject.carereporting.domain.OperatorTypeEntity;
import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.domain.types.FieldType;
import org.motechproject.carereporting.exception.CareRuntimeException;
import org.motechproject.carereporting.service.AreaService;
import org.motechproject.carereporting.service.ComputedFieldService;
import org.motechproject.carereporting.service.FormsService;
import org.motechproject.carereporting.service.IndicatorService;
import org.motechproject.carereporting.service.UserService;
import org.motechproject.carereporting.xml.XmlIndicatorParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public class PerformanceTestHelper {

    private static final Logger LOG = Logger.getLogger("PerformanceTestHelper");

    private static final String INDICATORS_DIRECTORY = "indicators/";
    private static final int CALCULATE_VALUES_FOR_PERIOD_DAYS = 30;
    private int indicatorsCount;
    private int areasCount;
    private int computedFieldsCount;
    private int classificationsCount;
    private int languagesCount;
    private int usersCount;

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private ComputedFieldService computedFieldService;

    @Autowired
    private IndicatorDao indicatorDao;

    @Autowired
    private AreaService areaService;

    @Autowired
    private FormsService formsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LanguageDao languageDao;

    @Autowired
    private DwQueryDao dwQueryDao;

    @Autowired
    private XmlIndicatorTestParser xmlIndicatorParser;

    private Random random = new Random();

    public PerformanceTestHelper() {

    }

    public PerformanceTestHelper(int indicatorsCount, int areasCount, int computedFieldsCount, int classificationsCount, int languagesCount, int usersCount) {
        this.indicatorsCount = indicatorsCount;
        this.areasCount = areasCount;
        this.computedFieldsCount = computedFieldsCount;
        this.classificationsCount = classificationsCount;
        this.languagesCount = languagesCount;
        this.usersCount = usersCount;
    }

    public void populateDatabase() throws Exception {

        setupAuthentication();
        populateDatabaseWithRandomIndicators();
        populateDatabaseWithRandomClassifications();
        populateDatabaseWithRandomAreas();
        populateDatabaseWithRandomLanguages();
        populateDatabaseWithRandomComputedFields();
        populateDatabaseWithRandomUsers();
    }

    private void setupAuthentication() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_INDICATORS"));
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_CLASSIFICATIONS"));
        authorities.add(new SimpleGrantedAuthority("CAN_CREATE_COMPUTED_FIELDS"));
        authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_SYSTEM_USERS"));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("principal", "credentials", authorities));
    }

    private void populateDatabaseWithRandomIndicators() throws IOException, JAXBException {
        File[] indicators = getAllIndicatorFiles();
        while (--indicatorsCount >= 0) {
            File indicator = indicators[random.nextInt(indicators.length)];
            try {
                createIndicator(indicator, "test-indicator-" + indicatorsCount);
            } catch (RuntimeException e) {
                LOG.warning("Cannot parse indicator: " + indicator.getPath());
                LOG.info("Remained: " + indicatorsCount + " indicators");
                indicatorsCount++;
            }
        }
    }

    private void populateDatabaseWithRandomUsers() {
        List<AreaEntity> areaEntities = new ArrayList<>(areaService.getAllAreas());

        for (int i = 0; i < usersCount; i++) {
            UserEntity userEntity = new UserEntity(UUID.randomUUID().toString());
            userEntity.setArea(areaEntities.get(random.nextInt(areaEntities.size())));
            userService.register(userEntity);
        }
    }

    private void populateDatabaseWithRandomClassifications() {
        for (int i = 0; i < classificationsCount; i++) {
            String name = UUID.randomUUID().toString();
            IndicatorClassificationEntity indicatorClassificationEntity = new IndicatorClassificationEntity();
            indicatorClassificationEntity.setName(name);
            try {
                indicatorService.createNewIndicatorClassification(indicatorClassificationEntity);
            } catch (CareRuntimeException e) {
                LOG.warning("Indicator classification with the same name or code already exists");
                i--;
            }
        }
    }

    private void populateDatabaseWithRandomComputedFields() {
        List<ComputedFieldEntity> computedFieldEntities = new ArrayList<>(computedFieldService.getAllComputedFields(true));
        FieldType[] fieldTypes = FieldType.values();
        List<FormEntity> formEntities = new ArrayList<>(formsService.getAllForms());
        List<OperatorTypeEntity> operatorTypeEntities = new ArrayList<>(computedFieldService.getAllOperatorTypes());

        for (int i = 0; i < computedFieldsCount; i++) {
            ComputedFieldEntity computedFieldEntity = new ComputedFieldEntity();
            computedFieldEntity.setName(UUID.randomUUID().toString());
            computedFieldEntity.setOrigin(false);
            computedFieldEntity.setType(fieldTypes[random.nextInt(fieldTypes.length)]);
            computedFieldEntity.setForm(formEntities.get(random.nextInt(formEntities.size())));
            FieldOperationEntity fieldOperationEntity = new FieldOperationEntity();
            fieldOperationEntity.setField1(computedFieldEntities.get(random.nextInt(computedFieldEntities.size())));
            fieldOperationEntity.setField2(computedFieldEntities.get(random.nextInt(computedFieldEntities.size())));
            fieldOperationEntity.setOperatorType(operatorTypeEntities.get(random.nextInt(operatorTypeEntities.size())));
            Set<FieldOperationEntity> fieldOperationEntities = new HashSet<>();
            fieldOperationEntities.add(fieldOperationEntity);
            computedFieldEntity.setFieldOperations(fieldOperationEntities);
            computedFieldService.createNewComputedField(computedFieldEntity);
        }
    }

    private void populateDatabaseWithRandomLanguages() {
        for (int i = 0; i < languagesCount; i++) {
            String name = UUID.randomUUID().toString();
            LanguageEntity languageEntity = new LanguageEntity();
            languageEntity.setName(name);
            languageEntity.setCode(name.substring(0, 2));
            try {
                languageDao.save(languageEntity);
            } catch (CareRuntimeException e) {
                LOG.warning("Language with the same name or code already exists");
                i--;
            }
        }
    }

    private void populateDatabaseWithRandomAreas() {
        List<LevelEntity> levelEntities = new ArrayList<>(areaService.getAllLevels());

        for (int i = 0; i < levelEntities.size(); i++) {
            addArea(i, levelEntities);
        }
    }

    private void addArea(int level, List<LevelEntity> levelEntities) {
        List<AreaEntity> areaEntities = new ArrayList<>(areaService.getAreasByLevelId(level));

        for (int i = 0; i < areasCount * Math.pow(5, level) / 780; i++) {
            AreaEntity areaEntity = new AreaEntity(UUID.randomUUID().toString(), levelEntities.get(level));
            if (areaEntities.size() > 0) {
                areaEntity.setParentArea(areaEntities.get(random.nextInt(areaEntities.size())));
            }
            areaService.createNewArea(areaEntity);
        }
    }

    private File[] getAllIndicatorFiles() {
        try {
            return new ClassPathResource(INDICATORS_DIRECTORY).getFile().listFiles();
        } catch (IOException e) {
            throw new RuntimeException("Cannot open indicators directory", e);
        }
    }

    private void createIndicator(File indicator, String name) throws IOException, JAXBException {
        IndicatorEntity indicatorEntity = xmlIndicatorParser.parse(new FileInputStream(indicator));
        indicatorEntity.setName(name + indicatorEntity.getName());
        dwQueryDao.save(indicatorEntity.getNumerator());
        if(indicatorEntity.getDenominator() != null) {
            dwQueryDao.save(indicatorEntity.getDenominator());
        }
        indicatorDao.save(indicatorEntity);
        for (AreaEntity area: areaService.getAllAreas()) {
            DateTime dateTime = new DateTime();
            for (int i = 0; i < CALCULATE_VALUES_FOR_PERIOD_DAYS; i++) {
                addCalculatedValueForDate(indicatorEntity, area, dateTime.toDate());
                dateTime = dateTime.plusDays(1);
            }
        }
    }

    private void addCalculatedValueForDate(IndicatorEntity indicator, AreaEntity area, Date date) {
        IndicatorValueEntity value = new IndicatorValueEntity(indicator, area,
                BigDecimal.valueOf(random.nextDouble() * 1000),
                BigDecimal.valueOf(random.nextDouble() * 100),
                BigDecimal.valueOf(random.nextDouble() * 100),
                indicator.getDefaultFrequency(), date);

        indicatorService.createNewIndicatorValue(value);
    }

}
