package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.LanguageGetPlainMessagesScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserLanguageScenario;
import org.motechproject.carereporting.performance.scenario.simple.UserGetLoggedUserPermissionsScenario;

public class LoadPageScenario extends AbstractScenario {

    {
        for (int i = 0; i < 3; i++) {
            addRequests(new LanguageGetPlainMessagesScenario());
        }
        addRequests(
                new UserGetLoggedUserPermissionsScenario(),
                new PerformanceSummaryScenario()
        );
    }

}
