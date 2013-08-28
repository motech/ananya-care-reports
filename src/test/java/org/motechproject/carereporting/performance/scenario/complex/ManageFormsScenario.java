package org.motechproject.carereporting.performance.scenario.complex;

import org.motechproject.carereporting.performance.scenario.AbstractScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetForeignKeyForTableScenario;
import org.motechproject.carereporting.performance.scenario.simple.FormGetFormsScenario;

public class ManageFormsScenario extends AbstractScenario {

    {
        addRequests(new FormGetFormsScenario());

        for (int i = 0; i < 5; i++) {
            addRequests(new FormGetForeignKeyForTableScenario());
        }
    }

}
