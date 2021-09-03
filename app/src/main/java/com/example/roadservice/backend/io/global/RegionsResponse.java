package com.example.roadservice.backend.io.global;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionsResponse {
    public static final int CODE = 0;
    @SerializedName("id")
    @Expose
    public int pk;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("sub_regions")
    @Expose
    public List<Province> provinces;

    public static class Province {
        @SerializedName("id")
        @Expose
        public int pk;

        @SerializedName("name")
        @Expose
        public String name;

        @SerializedName("country")
        @Expose
        public int countryId;

        @SerializedName("sub_regions")
        @Expose
        public List<County> counties;

        public static class County {
            @SerializedName("id")
            @Expose
            public int pk;

            @SerializedName("name")
            @Expose
            public String name;

            @SerializedName("province")
            @Expose
            public int provinceId;
        }
    }
}