package org.motechproject.carereporting.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;


@RequestMapping("api/map-report")
@Controller
public class MapReportController {

    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, String> getMapReportData(@RequestParam Integer indicatorId,
                                                 @RequestParam(required = false) Integer areaId) {

        String[] areas = new String[] {"Purba champaran", "Sitimarhi", "Begusarai", "Samastipur", "Darbhanga", "Madhubani", "Sheohar",
                                        "Nawada", "Nalanda", "Shelkhpua", "Lakhisarai", "Khagaria", "Jehanabad", "Arwal", "Kaimur (Bhabua)",
                                        "Aurangabad", "Gaya", "Jamui", "Munger", "Madhepura", "Banka", "Bhagalpur", "Katihar", "Kishanganj",
                                        "Purnia", "Araria", "Saharsa", "Supaul", "Rohtas", "Buxar", "Patna", "Bhojpur", "Vaishali", "Muzaffarpur",
                                        "Saran", "Siwan", "Gopalganj", "Pashchim Champaran"};
        String[] trends = new String[] {"positive", "negative", "neutral"};

        Random random = new Random();
        Map<String, String> values = new LinkedHashMap<>();
        for (String area: areas) {
            values.put(area, trends[random.nextInt(trends.length)]);
        }
        return values;
    }
}
