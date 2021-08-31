package com.example.roadservice.models;

import com.example.roadservice.ui.issues.specialist.structs.ItemCounter;

import java.util.Arrays;

public class SampleData {
    public static final Issue ISSUE = new Issue(
            1,
            new GeoLocation(35.7531726, 51.3267043),
            "مشکل بزرگ",
            "این مشکل خیلی مشکل بزرگیه. واقعا باید فکرامونو بریزیم رو هم یه چاره‌ای بیندیشیم.",
            "",
            1
    );

    public static final Issue[] ISSUES_LIST = {
            new Issue(
                    10,
                    GeoLocation.ORIGIN,
                    "مشکل اوّل",
                    "",
                    "",
                    2
            ),
            new Issue(
                    11,
                    GeoLocation.ORIGIN,
                    "مشکل دوم",
                    "",
                    "",
                    3
            ),
            new Issue(
                    12,
                    GeoLocation.ORIGIN,
                    "مشکل سوم",
                    "",
                    "",
                    4
            ),
    };

    public static final Machine[] MACHINES = {
            new Machine(1, "جرثقیل"),
            new Machine(2, "تراکتور"),
            new Machine(3, "بنز"),
            new Machine(4, "برف‌روبی"),
    };

    public static final Skill[] SKILLS = {
            new Skill(1, "برق‌کاری"),
            new Skill(2, "جوش‌کاری"),
            new Skill(3, "خدمات آسفالت"),
            new Skill(4, "آتش‌نشانی")
    };

    public static Mission getMission() {
        ItemCounter machines[] = {
                new ItemCounter(MACHINES[0], 2),
                new ItemCounter(MACHINES[1], 3)
        };
        ItemCounter skills[] = {
                new ItemCounter(SKILLS[0], 1),
                new ItemCounter(SKILLS[2], 5)
        };
        return new Mission(
                Arrays.asList(machines),
                Arrays.asList(skills),
                new MissionType(1, "مردم احساس آرامش"),
                1
        );
    }
}
