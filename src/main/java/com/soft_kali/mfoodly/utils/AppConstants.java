package com.rupesh_mandal.blog_app_backend.config;

import org.springframework.data.domain.Sort;

public class AppConstants {
    public static final int PAGE_NUMBBER=0;
    public static final int PAGE_SIZE=10;

    public static final int ROLE_ADMIN=501;
    public static final int ROLE_NORMAL=502;
    public static final String SORT_BY_FOR_POST ="title";
    public static final String SORT_BY_FOR_COMMENT ="commentId";
    public static final Sort.Direction sort=Sort.Direction.ASC;

}
