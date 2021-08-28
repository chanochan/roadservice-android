package com.example.roadservice.models;

public class SampleData {
    public static final Issue ISSUE = new Issue(
            new GeoLocation(35.7531726, 51.3267043),
            "مشکل بزرگ",
            "این مشکل خیلی مشکل بزرگیه. واقعا باید فکرامونو بریزیم رو هم یه چاره‌ای بیندیشیم.",
            ""
    );

    public static final Issue[] ISSUES_LIST = {
            new Issue(
                    GeoLocation.ORIGIN,
                    "مشکل اوّل",
                    "",
                    ""
            ),
            new Issue(
                    GeoLocation.ORIGIN,
                    "مشکل دوم",
                    "",
                    ""
            ),
            new Issue(
                    GeoLocation.ORIGIN,
                    "مشکل سوم",
                    "",
                    ""
            ),
    };

    public static final String[] MACHINES = {
            "جرثقیل",
            "تراکتور",
            "بنز",
            "برف‌روبی"
    };

    public static final String[] SKILLS = {
            "برق‌کاری",
            "جوش‌کاری",
            "خدمات آسفالت",
            "آتش‌نشانی"
    };
}
