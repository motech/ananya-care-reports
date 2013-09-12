package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetLanguagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserLanguageScenario;

public class DefineLanguageScenario extends AbstractScenario {

    {
        addRequests(
                new UserGetLoggedUserLanguageScenario(),
                new LanguageGetLanguagesScenario(),
                new LanguageGetMessagesScenario()
        );
    }

}
