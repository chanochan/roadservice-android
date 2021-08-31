package com.example.roadservice.models;

public class SampleData {
    public static final Issue ISSUE = new Issue(
            new GeoLocation(35.7531726, 51.3267043),
            "مشکل بزرگ",
            "این مشکل خیلی مشکل بزرگیه. واقعا باید فکرامونو بریزیم رو هم یه چاره‌ای بیندیشیم.",
            "",
            1
    );

    public static final Issue[] ISSUES_LIST = {
            new Issue(
                    GeoLocation.ORIGIN,
                    "مشکل اوّل",
                    "",
                    "",
                    2
            ),
            new Issue(
                    GeoLocation.ORIGIN,
                    "مشکل دوم",
                    "",
                    "",
                    3
            ),
            new Issue(
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
}
