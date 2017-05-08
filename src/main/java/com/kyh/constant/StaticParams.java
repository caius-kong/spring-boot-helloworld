package com.kyh.constant;

/**
 * Created by kongyunhui on 2017/5/4.
 */
public class StaticParams {
    public class PATHREGX{
        public static final String CSS = "/APIs/css/**";
        public static final String JS = "/APIs/js/**";
        public static final String IMG = "/APIs/images/**";

        public static final String NO_AUTH = "/APIs/";
        public static final String ADMIN_AUTH = "/APIs/admin/**";
        public static final String USER_AUTH = "/APIs/users/**";


    }

    public class USERROLE{
        public static final String ROLE_USER = "user";
        public static final String ROLE_ADMIN = "admin";
    }
}
