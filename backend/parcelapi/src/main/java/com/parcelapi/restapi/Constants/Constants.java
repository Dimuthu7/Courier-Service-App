package com.parcelapi.restapi.Constants;

public class Constants {
    public static class REQUEST_MAPPING {
        public static final String USER = "/api/userdetails";
        public static final String CUSTOMER = "/api/customerdetails";
        public static final String DRIVER = "/api/driverdetails";
        public static final String ADMIN = "/api/admindetails";
        public static final String AUTH = "/api/auth";
    }

    public static class JWT {
        public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
        public static final String JWT_SECURITY_KEY = "THIS_IS_THE_SECURITY_KEY_FOR_JWT_AUTHENTICATION";
    }

    public static class AUTHFILTER_IGNORE {
        public static final String SWAGGER_UI = "/swagger-ui/**";
        public static final String SWAGGER_DOC = "/v3/api-docs/**";
        public static final String AUTH_URL = "/api/auth/**";
        public static final String REGISTER = "/api/userdetails/register";
    }
}
